package com.farmacia.models;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class Farmacia {

    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String cep;
    private String cpnj;
    private Cidade cidade;
    private LatLng geoLocalizacao;
    private byte[] imagem;
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

    public LatLng getGeoLocalizacao() {
        return geoLocalizacao;
    }

    public void setGeoLocalizacao(LatLng geoLocalizacao) {
        this.geoLocalizacao = geoLocalizacao;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Farmacia{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cep='" + cep + '\'' +
                ", cpnj='" + cpnj + '\'' +
                ", cidade=" + cidade +
                ", geoLocalizacao=" + geoLocalizacao.toString() +
                '}';
    }
}
