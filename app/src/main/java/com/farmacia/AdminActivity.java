package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void botaoClicado(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_cadastra_farmacia:
                chamaCadastro();
                break;

            case R.id.btn_lista_farmacia:
                chamaListagem();

        }
    }

    private void chamaCadastro(){
        startActivity(new Intent(this, CadastraFarmaciaActivity.class));
    }


    private void chamaListagem(){
        startActivity(new Intent(this, ListaFarmaciaActivity.class));
    }
}
