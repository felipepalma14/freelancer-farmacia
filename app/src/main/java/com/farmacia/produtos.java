package com.farmacia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class produtos extends AppCompatActivity {

   private String nome;
   private String valor;
    dbcontroller db= new dbcontroller(getBaseContext());

    public List<produtos> valores(){


        return db.retornaProduto();
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
        return "" +
               nome +
                ", valor='" + valor
                ;
    }
}
