package com.farmacia.models;

public class Produto {
    private int id;
    private Categoria categoria;
    private String descricao;
    private String peso;
    private byte[] image;

    public Produto() {
    }

    public Produto(Categoria categoria, String descricao, String peso, byte[] image) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.peso = peso;
        this.image = image;
    }

    public Produto(int id, Categoria categoria, String descricao, String peso, byte[] image) {
        this.id = id;
        this.categoria = categoria;
        this.descricao = descricao;
        this.peso = peso;
        this.image = image;
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

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
