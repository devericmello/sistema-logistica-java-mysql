// src/service/RoteirizacaoServicePro.java
package service;

import java.util.*;
import java.util.stream.Collectors;

public class RoteirizacaoServicePro {

    public enum Objetivo { MENOR_TEMPO, MENOR_CUSTO }

    public static class Parada {
        public final int idPedido;
        public final double lat, lon;
        public final double pesoKg, volumeM3;  // opcionais (0 = não usa)
        public Parada(int idPedido, double lat, double lon) {
            this(idPedido, lat, lon, 0, 0);
        }
        public Parada(int idPedido, double lat, double lon, double pesoKg, double volumeM3) {
            this.idPedido = idPedido; this.lat = lat; this.lon = lon; this.pesoKg = pesoKg; this.volumeM3 = volumeM3;
        }
    }

    public static class ResultadoRota {
        public final List<Integer> ordemPedidos;
        public final double distanciaTotalKm;
        public final int tempoPrevistoMin;
        public final double custoPrevisto;
        public ResultadoRota(List<Integer> ordemPedidos, double km, int min, double custo) {
            this.ordemPedidos = ordemPedidos; this.distanciaTotalKm = km; this.tempoPrevistoMin = min; this.custoPrevisto = custo;
        }
    }

    // API principal: sugere rota (NN + 2-opt) e calcula métricas
    public ResultadoRota gerarRota(
            double latOrigem, double lonOrigem, List<Parada> paradas,
            Objetivo objetivo,
            double velocidadeKmh, double tempoParadaMin,
            double custoHora, double custoKm,
            double capPesoKg, double capVolM3) {

        if (paradas == null || paradas.isEmpty())
            return new ResultadoRota(List.of(), 0, 0, 0);

        // (1) validações simples de capacidade (didático: soma total)
        double totalPeso = paradas.stream().mapToDouble(p -> p.pesoKg).sum();
        double totalVol  = paradas.stream().mapToDouble(p -> p.volumeM3).sum();
        if ((capPesoKg > 0 && totalPeso > capPesoKg) || (capVolM3 > 0 && totalVol > capVolM3)) {
            // Não bloqueia: apenas continua com aviso implícito (poderia filtrar)
        }

        // (2) rota inicial: Nearest Neighbor a partir da origem
        List<Parada> restantes = new ArrayList<>(paradas);
        List<Parada> rota = new ArrayList<>(restantes.size());
        double aLat = latOrigem, aLon = lonOrigem;
        while (!restantes.isEmpty()) {
            int bestIdx = -1;
            double bestDist = Double.POSITIVE_INFINITY;
            for (int i = 0; i < restantes.size(); i++) {
                Parada p = restantes.get(i);
                double d = haversineKm(aLat, aLon, p.lat, p.lon);
                if (d < bestDist) { bestDist = d; bestIdx = i; }
            }
            Parada prox = restantes.remove(bestIdx);
            rota.add(prox);
            aLat = prox.lat; aLon = prox.lon;
        }

        // (3) refino: 2-opt
        rota = twoOpt(latOrigem, lonOrigem, rota);

        // (4) métricas
        return avaliarRota(latOrigem, lonOrigem, rota, velocidadeKmh, tempoParadaMin, custoHora, custoKm);
    }

    // Compara rota do aluno (sequência escolhida) vs a melhor (mesmo conjunto de paradas)
    public Map<String, Double> compararRotaAluno(
            double latOrigem, double lonOrigem,
            List<Parada> rotaAluno, List<Parada> todasDisponiveis,
            Objetivo objetivo,
            double velocidadeKmh, double tempoParadaMin,
            double custoHora, double custoKm) {

        // usamos a lista do aluno como conjunto base para a ótima (justo para comparação)
        Set<Integer> idsAluno = rotaAluno.stream().map(p -> p.idPedido).collect(Collectors.toCollection(LinkedHashSet::new));
        List<Parada> base = todasDisponiveis.stream().filter(p -> idsAluno.contains(p.idPedido)).collect(Collectors.toList());

        ResultadoRota melhor = gerarRota(latOrigem, lonOrigem, base, objetivo, velocidadeKmh, tempoParadaMin, custoHora, custoKm, 0, 0);
        ResultadoRota doAluno = avaliarRota(latOrigem, lonOrigem, rotaAluno, velocidadeKmh, tempoParadaMin, custoHora, custoKm);

        Map<String, Double> out = new LinkedHashMap<>();
        out.put("km_aluno", doAluno.distanciaTotalKm);
        out.put("min_aluno", (double) doAluno.tempoPrevistoMin);
        out.put("custo_aluno", doAluno.custoPrevisto);
        out.put("km_otimizada", melhor.distanciaTotalKm);
        out.put("min_otimizada", (double) melhor.tempoPrevistoMin);
        out.put("custo_otimizado", melhor.custoPrevisto);
        out.put("delta_km", doAluno.distanciaTotalKm - melhor.distanciaTotalKm);
        out.put("delta_min", (double) (doAluno.tempoPrevistoMin - melhor.tempoPrevistoMin));
        out.put("delta_custo", doAluno.custoPrevisto - melhor.custoPrevisto);
        return out;
    }

    // Avalia a sequência recebida e calcula km/min/custo
    public ResultadoRota avaliarRota(
            double latOrigem, double lonOrigem,
            List<Parada> seq,
            double velocidadeKmh, double tempoParadaMin,
            double custoHora, double custoKm) {

        double km = 0.0;
        double aLat = latOrigem, aLon = lonOrigem;
        for (Parada p : seq) {
            km += haversineKm(aLat, aLon, p.lat, p.lon);
            aLat = p.lat; aLon = p.lon;
        }
        // (didático) sem retorno ao depósito; some paradas no tempo
        double horas = (velocidadeKmh > 0 ? (km / velocidadeKmh) : 0);
        double min = horas * 60.0 + (tempoParadaMin * Math.max(0, seq.size()));
        double custo = custoHora * horas + custoKm * km;

        List<Integer> ordem = seq.stream().map(p -> p.idPedido).collect(Collectors.toList());
        return new ResultadoRota(ordem, round2(km), (int) Math.round(min), round2(custo));
    }

    // --- heurística 2-opt (sem retorno ao depósito) ---
    private List<Parada> twoOpt(double lat0, double lon0, List<Parada> rota) {
        if (rota.size() < 3) return rota;
        List<Parada> best = new ArrayList<>(rota);
        double bestDist = pathLen(lat0, lon0, best);

        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 0; i < best.size() - 1; i++) {
                for (int k = i + 1; k < best.size(); k++) {
                    List<Parada> nova = twoOptSwap(best, i, k);
                    double d = pathLen(lat0, lon0, nova);
                    if (d + 1e-9 < bestDist) {
                        bestDist = d;
                        best = nova;
                        improved = true;
                    }
                }
            }
        }
        return best;
    }

    private List<Parada> twoOptSwap(List<Parada> rota, int i, int k) {
        List<Parada> out = new ArrayList<>(rota.size());
        for (int c = 0; c < i; c++) out.add(rota.get(c));
        for (int c = k; c >= i; c--) out.add(rota.get(c));
        for (int c = k + 1; c < rota.size(); c++) out.add(rota.get(c));
        return out;
    }

    private double pathLen(double lat0, double lon0, List<Parada> seq) {
        double km = 0.0, aLat = lat0, aLon = lon0;
        for (Parada p : seq) {
            km += haversineKm(aLat, aLon, p.lat, p.lon);
            aLat = p.lat; aLon = p.lon;
        }
        return km;
    }

    // Haversine (km)
    public static double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0088;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2)*Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2)*Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    private static double round2(double v){ return Math.round(v * 100.0) / 100.0; }
}
