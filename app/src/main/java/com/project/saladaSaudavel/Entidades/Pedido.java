package com.project.saladaSaudavel.Entidades;

import com.project.saladaSaudavel.DAOs.PratoDAO;

import java.util.Date;

public class Pedido {
    int idPedido,idPrato,idCliente;
    String descr,data;

    public Pedido() {
    }

    public Pedido(int idPedido, String descr, int idPrato, int idCliente, String data) {
        this.idPedido = idPedido;
        this.descr = descr;
        this.idPrato = idPrato;
        this.idCliente = idCliente;
        this.data = data;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdPrato() {
        return idPrato;
    }

    public void setIdPrato(int idPrato) {
        this.idPrato = idPrato;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return idPedido + PratoDAO.recuperaPorId(getIdPrato()).getNome();
    }
}
