package com.farmacia.models;

import android.location.Location;

public class Farmacia {

    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String cep;
    private String cpnj;
    private Cidade cidade;
    private Location geoLocalizacao;
    //horario


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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCpnj() {
        return cpnj;
    }

    public void setCpnj(String cpnj) {
        this.cpnj = cpnj;
    }

    public Location getGeoLocalizacao() {
        return geoLocalizacao;
    }

    public void setGeoLocalizacao(Location geoLocalizacao) {
        this.geoLocalizacao = geoLocalizacao;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
