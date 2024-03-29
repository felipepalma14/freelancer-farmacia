package com.farmacia.models;

import java.io.Serializable;

public class Cidade implements Serializable {
    private int id;
    private String nome;

    public Cidade() {
    }

    public Cidade(String nome) {
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

    @Override
    public String toString() {
        return this.getNome();
    }
}
