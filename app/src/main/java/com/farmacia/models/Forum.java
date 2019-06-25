package com.farmacia.models;

public class Forum {
    private int id;
    private Usuario usuario;
    private String topico;
    private String comentario;
    private String createAt;
    private Farmacia farmacia;

    public Forum() {
    }

    public Forum(Usuario usuario, String topico, String comentario, String createAt, Farmacia farmacia) {
        this.usuario = usuario;
        this.topico = topico;
        this.comentario = comentario;
        this.createAt = createAt;
        this.farmacia = farmacia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTopico() {
        return topico;
    }

    public void setTopico(String topico) {
        this.topico = topico;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", topico='" + topico + '\'' +
                ", comentario='" + comentario + '\'' +
                ", createAt='" + createAt + '\'' +
                '}';
    }
}
