package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.farmacia.databases.Database;
import com.farmacia.models.Produto;

import java.util.List;

public class ListaProdutos extends AppCompatActivity {

    private static final String TAG = ListaProdutos.class.getSimpleName();
    private static Database mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaprodutos);


        mDatabase = new Database(this);
        mDatabase.open();


        List<Produto> listaProdutos = mDatabase.mProdutoDAO.getTodosProdutos();

        ListView lv= (ListView)findViewById(R.id.listaProdutos);

        ArrayAdapter<Produto> adapter= new ArrayAdapter<Produto>(this,
                R.layout.support_simple_spinner_dropdown_item, listaProdutos);

        lv.setAdapter(adapter);

        Button btnMaps=(Button)findViewById(R.id.btnMaps);




         Button btnAddProduto=(Button) findViewById(R.id.btnAddProduto);


         btnAddProduto.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent tela = new Intent(ListaProdutos.this, CadastraProdutoActivity.class);
                 startActivity(tela);
             }
         });
         btnMaps.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent tela=new Intent(ListaProdutos.this,FarmaciaMapsActivity.class);

                 startActivity(tela);
             }
         });
    }
}
