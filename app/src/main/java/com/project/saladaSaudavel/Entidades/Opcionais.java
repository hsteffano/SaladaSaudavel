package com.project.saladaSaudavel.Entidades;

public class Opcionais {
    int id;
    String nome,valor;

    public Opcionais() {
    }

    public Opcionais(int id, String nome, String valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
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

    @Override
    public String toString() {
        return "Nome: " + nome +", Valor: R$" + valor;
    }
}
