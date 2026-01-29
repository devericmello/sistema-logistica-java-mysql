/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Pedido;
import Modelo.PedidoItem;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Pedidos e Ocorrências.
 * Requer tabela conforme script SQL V2.
 * Usa Conexao.getConnection() se existir na sua base.
 */
public class PedidoDAO {

    private final Connection conn;

    // Tente primeiro este construtor, passando sua Connection existente
    public PedidoDAO(Connection conn) {
        this.conn = conn;
    }

    // Alternativa: se você tem uma classe Conexao estática
    public static PedidoDAO fromConexao() {
        try {
            Class<?> clazz = Class.forName("br.com.seuprojeto.infra.Conexao");
            Connection c = (Connection) clazz.getMethod("getConnection").invoke(null);
            return new PedidoDAO(c);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao obter conexão via Conexao.getConnection()", e);
        }
    }

    // ==== CRUD PEDIDO ====

    public int criarPedido(Pedido p) throws SQLException {
        String sql = "INSERT INTO pedido (id_cliente,id_estoque_origem,data_prometida,canal,status,observacao) " +
                     "VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getIdCliente());
            ps.setInt(2, p.getIdEstoqueOrigem());
            if (p.getDataPrometida() != null) {
                ps.setTimestamp(3, Timestamp.valueOf(p.getDataPrometida()));
            } else {
                ps.setNull(3, Types.TIMESTAMP);
            }
            ps.setString(4, p.getCanal() == null ? "B2C" : p.getCanal());
            ps.setString(5, p.getStatus() == null ? "ABERTO" : p.getStatus());
            ps.setString(6, p.getObservacao());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("Não foi possível gerar id do pedido.");
    }

    public void adicionarItem(int idPedido, int idMaterial, int quantidade) throws SQLException {
        String sql = "INSERT INTO pedido_item (id_pedido,id_material,quantidade) VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ps.setInt(2, idMaterial);
            ps.setInt(3, quantidade);
            ps.executeUpdate();
        }
    }

    public List<Pedido> listarPedidos(String statusOuTodos, String termoCliente) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p.*, c.nome AS nomeCliente ")
          .append("FROM pedido p JOIN cliente c ON c.id_cliente=p.id_cliente ");
        List<Object> params = new ArrayList<>();
        boolean where = false;
        if (statusOuTodos != null && !"TODOS".equalsIgnoreCase(statusOuTodos)) {
            sb.append("WHERE p.status=? ");
            params.add(statusOuTodos);
            where = true;
        }
        if (termoCliente != null && !termoCliente.isBlank()) {
            sb.append(where ? "AND " : "WHERE ");
            sb.append("c.nome LIKE ? ");
            params.add("%" + termoCliente + "%");
        }
        sb.append("ORDER BY p.data_criacao DESC");
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                List<Pedido> out = new ArrayList<>();
                while (rs.next()) {
                    Pedido p = mapPedido(rs);
                    p.setNomeCliente(rs.getString("nomeCliente"));
                    out.add(p);
                }
                return out;
            }
        }
    }

    public List<PedidoItem> listarItens(int idPedido) throws SQLException {
        String sql = "SELECT pi.*, m.nome AS nomeMaterial " +
                     "FROM pedido_item pi JOIN material m ON m.id_material=pi.id_material " +
                     "WHERE pi.id_pedido=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                List<PedidoItem> out = new ArrayList<>();
                while (rs.next()) out.add(mapItem(rs));
                return out;
            }
        }
    }

    public void atualizarStatus(int idPedido, String novoStatus) throws SQLException {
        String sql = "UPDATE pedido SET status=? WHERE id_pedido=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, novoStatus);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        }
    }

    public void atualizarStatusItem(int idPedidoItem, String novoStatus) throws SQLException {
        String sql = "UPDATE pedido_item SET status=? WHERE id_pedido_item=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, novoStatus);
            ps.setInt(2, idPedidoItem);
            ps.executeUpdate();
        }
    }

    // Marca expedição registrando saída no movimento (estoque -> S)
    public void expedirItem(int idEstoque, int idMaterial, int quantidade, int idPedidoItem) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // 1) Movimento de saída
            String mov = "INSERT INTO movimentacao_material (id_estoque,id_material,tipo,quantidade,custo_unitario,motivo) " +
                         "VALUES (?,?, 'S', ?, 0.00, 'Expedição de pedido')";
            try (PreparedStatement ps = conn.prepareStatement(mov)) {
                ps.setInt(1, idEstoque);
                ps.setInt(2, idMaterial);
                ps.setInt(3, quantidade);
                ps.executeUpdate();
            }
            // 2) Atualiza status item
            atualizarStatusItem(idPedidoItem, "SEPARADO");
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void registrarOcorrencia(int idPedido, String tipo, String descricao, String gravidade) throws SQLException {
        String sql = "INSERT INTO ocorrencia_entrega (id_pedido,tipo,descricao,gravidade) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ps.setString(2, tipo);
            ps.setString(3, descricao);
            ps.setString(4, gravidade);
            ps.executeUpdate();
        }
    }

    public void confirmarEntrega(int idPedido, Integer tempoRealMin) throws SQLException {
        String updEnt = "UPDATE entrega SET status='ENTREGUE', tempo_real_min=?, confirmado_em=NOW() WHERE id_pedido=?";
        try (PreparedStatement ps = conn.prepareStatement(updEnt)) {
            if (tempoRealMin == null) ps.setNull(1, Types.INTEGER); else ps.setInt(1, tempoRealMin);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        }
        atualizarStatus(idPedido, "ENTREGUE");
    }

    // ==== KPIs simples (consulta às views) ====

    public double nivelServicoDoDia(java.time.LocalDate dia) throws SQLException {
        String sql = "SELECT nivel_servico_pct FROM vw_nivel_servico WHERE dia=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(dia));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
                return 0.0;
            }
        }
    }

    // ==== Mapeamentos ====
    private Pedido mapPedido(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setIdPedido(rs.getInt("id_pedido"));
        p.setIdCliente(rs.getInt("id_cliente"));
        p.setIdEstoqueOrigem(rs.getInt("id_estoque_origem"));
        Timestamp dc = rs.getTimestamp("data_criacao");
        if (dc != null) p.setDataCriacao(dc.toLocalDateTime());
        Timestamp dp = rs.getTimestamp("data_prometida");
        if (dp != null) p.setDataPrometida(dp.toLocalDateTime());
        p.setCanal(rs.getString("canal"));
        p.setStatus(rs.getString("status"));
        p.setObservacao(rs.getString("observacao"));
        return p;
    }

    private PedidoItem mapItem(ResultSet rs) throws SQLException {
        PedidoItem i = new PedidoItem();
        i.setIdPedidoItem(rs.getInt("id_pedido_item"));
        i.setIdPedido(rs.getInt("id_pedido"));
        i.setIdMaterial(rs.getInt("id_material"));
        i.setQuantidade(rs.getInt("quantidade"));
        i.setStatus(rs.getString("status"));
        i.setReservadoEstoque(rs.getInt("reservado_estoque"));
        i.setNomeMaterial(rs.getString("nomeMaterial"));
        return i;
    }
}