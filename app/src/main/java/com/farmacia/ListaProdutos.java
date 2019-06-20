package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class ListaProdutos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaprodutos);


        dbcontroller db= new dbcontroller(getBaseContext());
        List<produtos> p= db.retornaProduto();
        ListView lv= (ListView)findViewById(R.id.listaProdutos);
        ArrayAdapter<produtos> adapter= new ArrayAdapter<produtos>(this,R.layout.support_simple_spinner_dropdown_item,p);
        lv.setAdapter(adapter);
        Button btnMaps=(Button)findViewById(R.id.btnMaps);




         Button btnAddProduto=(Button) findViewById(R.id.btnAddProduto);


         btnAddProduto.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent tela=new Intent(ListaProdutos.this, CadastraProdutoActivity.class);
                 startActivity(tela);
             }
         });
         btnMaps.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent tela=new Intent(ListaProdutos.this,MapsActivity.class);
                 startActivity(tela);
             }
         });
    }
}
