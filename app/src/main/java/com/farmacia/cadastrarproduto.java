package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class cadastrarproduto extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrarproduto);
        EditText etxNomeProduto=(EditText)findViewById(R.id.etxNomeProduto);
        EditText etxValorProduto=(EditText)findViewById(R.id.etxValor);
        Button btnSalvarProduto=(Button)findViewById(R.id.btnSalvaProduto);

        final String nomeproduto=etxNomeProduto.getText().toString();
        final String valorproduto=etxValorProduto.getText().toString();
        final dbcontroller db= new dbcontroller(this);

        btnSalvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.insertProduto(nomeproduto, valorproduto);
                }catch(Exception exc){
                    Toast.makeText(cadastrarproduto.this,"Não foi possível cadastrar produto: "+exc,Toast.LENGTH_LONG).show();
                }
                Intent tela= new Intent(cadastrarproduto.this,telaprodutos.class);
                startActivity(tela);

            }
        });




    }
}
