package service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço simples de roteirização didática.
 * Entrada: origem (lat,lon) e lista de paradas com (idPedido, lat, lon).
 * Saída: ordem otimizada (menor distância total) + métricas.
 */
public class RoteirizacaoService {

    public static class Parada {
        public final int idPedido;
        public final double lat;
        public final double lon;
        public Parada(int idPedido, double lat, double lon) {
            this.idPedido = idPedido; this.lat = lat; this.lon = lon;
        }
    }

    public static class ResultadoRota {
        public final List<Integer> ordemPedidos;
        public final double distanciaTotalKm;
        public final int tempoPrevistoMin;

        public ResultadoRota(List<Integer> ordemPedidos, double distanciaTotalKm, int tempoPrevistoMin) {
            this.ordemPedidos = ordemPedidos;
            this.distanciaTotalKm = distanciaTotalKm;
            this.tempoPrevistoMin = tempoPrevistoMin;
        }
    }

    /** Gera rota pelo método do vizinho mais próximo. */
   public ResultadoRota gerarRota(double latOrigem, double lonOrigem, List<Parada> paradas,
                               double velocidadeMediaKmh, double tempoParadaMin) {

    List<Parada> restantes = new ArrayList<>(paradas);
    List<Integer> ordem = new ArrayList<>();
    double totalKm = 0.0;
    double atualLat = latOrigem, atualLon = lonOrigem;

    while (!restantes.isEmpty()) {
        Parada proxima = null;
        double menorDist = Double.MAX_VALUE;

        // percorre manualmente em vez de lambda
        for (Parada p : restantes) {
            double d = haversineKm(atualLat, atualLon, p.lat, p.lon);
            if (d < menorDist) {
                menorDist = d;
                proxima = p;
            }
        }

        // atualiza rota
        totalKm += menorDist;
        ordem.add(proxima.idPedido);
        atualLat = proxima.lat;
        atualLon = proxima.lon;
        restantes.remove(proxima);
    }

    // Tempo = deslocamento + tempo de serviço em cada parada
    double tempoHoras = (velocidadeMediaKmh <= 0 ? 30 : totalKm / velocidadeMediaKmh);
    int tempoMin = (int) Math.round(tempoHoras * 60 + tempoParadaMin * paradas.size());

    return new ResultadoRota(ordem, round2(totalKm), tempoMin);
}

    /** Compara a rota do aluno com a rota otimizada. Retorna delta de km e de minutos. */
    public Map<String, Double> compararRotaAluno(double latOrigem, double lonOrigem,
                                                 List<Parada> paradasOrdenadasAluno,
                                                 List<Parada> todasParadas,
                                                 double velocidadeMediaKmh, double tempoParadaMin) {
        ResultadoRota otima = gerarRota(latOrigem, lonOrigem, todasParadas, velocidadeMediaKmh, tempoParadaMin);
        double kmAluno = distanciaDaSequencia(latOrigem, lonOrigem, paradasOrdenadasAluno);
        double tempoAlunoMin = (kmAluno / velocidadeMediaKmh) * 60 + tempoParadaMin * paradasOrdenadasAluno.size();

        Map<String, Double> out = new LinkedHashMap<>();
        out.put("km_otimizada", otima.distanciaTotalKm);
        out.put("km_aluno", round2(kmAluno));
        out.put("delta_km", round2(kmAluno - otima.distanciaTotalKm));
        out.put("min_otimizada", (double) otima.tempoPrevistoMin);
        out.put("min_aluno", round2(tempoAlunoMin));
        out.put("delta_min", round2(tempoAlunoMin - otima.tempoPrevistoMin));
        return out;
    }

    private double distanciaDaSequencia(double latOrigem, double lonOrigem, List<Parada> seq) {
        double total = 0;
        double aLat = latOrigem, aLon = lonOrigem;
        for (Parada p : seq) {
            total += haversineKm(aLat, aLon, p.lat, p.lon);
            aLat = p.lat; aLon = p.lon;
        }
        return round2(total);
    }

    // Haversine em km
    public static double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2)*Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon/2)*Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
