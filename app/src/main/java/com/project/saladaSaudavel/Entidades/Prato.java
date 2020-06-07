package com.project.saladaSaudavel.Entidades;

public class Prato {

    int id;
    String nome,valor,ingredientes;

    public Prato() {
    }

    public Prato(int id, String ingredientes, String valor, String nome) {
        this.id = id;
        this.ingredientes = ingredientes;
        this.valor = valor;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +", Valor: R$" + valor +", Ingredientes: " + ingredientes;
    }
}
