/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class PedidoItem {
    private int idPedidoItem;
    private int idPedido;
    private int idMaterial;
    private int quantidade;
    private String status; // PENDENTE, SEPARADO, FALTANTE, AVARIADO
    private int reservadoEstoque;
    private String nomeMaterial; // join

    public int getIdPedidoItem() { return idPedidoItem; }
    public void setIdPedidoItem(int idPedidoItem) { this.idPedidoItem = idPedidoItem; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdMaterial() { return idMaterial; }
    public void setIdMaterial(int idMaterial) { this.idMaterial = idMaterial; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getReservadoEstoque() { return reservadoEstoque; }
    public void setReservadoEstoque(int reservadoEstoque) { this.reservadoEstoque = reservadoEstoque; }

    public String getNomeMaterial() { return nomeMaterial; }
    public void setNomeMaterial(String nomeMaterial) { this.nomeMaterial = nomeMaterial; }
}