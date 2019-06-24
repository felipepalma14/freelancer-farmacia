package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OpcoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);
    }



    public void listagemProdutos(View view) {
        startActivity(new Intent(this, ListaProdutosActivity.class));
    }

    public void listagemPorLocalizacao(View view) {
        startActivity(new Intent(this,FarmaciaMapsActivity.class));
    }
}
