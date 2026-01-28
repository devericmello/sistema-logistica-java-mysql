/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDateTime;

public class Pedido {
    private int idPedido;
    private int idCliente;
    private int idEstoqueOrigem;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataPrometida;
    private String canal;   // B2B, B2C, INTERNO
    private String status;  // ABERTO, SEPARACAO, EXPEDIDO, EM_TRANSITO, ENTREGUE, CANCELADO
    private String observacao;
    private String nomeCliente; // join de apoio

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdEstoqueOrigem() { return idEstoqueOrigem; }
    public void setIdEstoqueOrigem(int idEstoqueOrigem) { this.idEstoqueOrigem = idEstoqueOrigem; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataPrometida() { return dataPrometida; }
    public void setDataPrometida(LocalDateTime dataPrometida) { this.dataPrometida = dataPrometida; }

    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
}