package com.farmacia.models;

public class Produto {
    private int id;
    private Categoria categoria;
    private String descricao;
    private double peso;

    public Produto(Categoria categoria, String descricao, double peso) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.peso = peso;
    }

    public Produto(int id, Categoria categoria, String descricao, double peso) {
        this.id = id;
        this.categoria = categoria;
        this.descricao = descricao;
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", categoria=" + categoria +
                ", descricao='" + descricao + '\'' +
                ", peso=" + peso +
                '}';
    }
}
