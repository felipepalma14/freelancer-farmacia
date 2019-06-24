package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.farmacia.adapters.ProdutoAdapter;
import com.farmacia.databases.Database;
import com.farmacia.models.Produto;

import java.util.ArrayList;

public class ListaProdutosActivity extends AppCompatActivity {

    private static final String TAG = ListaProdutosActivity.class.getSimpleName();
    private static Database mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaprodutos);


        mDatabase = new Database(this);
        mDatabase.open();


        ArrayList<Produto> listaProdutos = mDatabase.mProdutoDAO.getTodosProdutos();

        ListView lv= findViewById(R.id.listaProdutos);

        mostrarListaProdutos(listaProdutos, lv);

        Button btnMaps=(Button)findViewById(R.id.btnMaps);




         Button btnAddProduto=(Button) findViewById(R.id.btnAddProduto);


         btnAddProduto.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent tela = new Intent(ListaProdutosActivity.this, CadastraProdutoActivity.class);
                 startActivity(tela);
             }
         });
         btnMaps.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent tela=new Intent(ListaProdutosActivity.this,FarmaciaMapsActivity.class);

                 startActivity(tela);
             }
         });
    }

    private void mostrarListaProdutos(ArrayList<Produto> listaProdutos, ListView lv) {
        Log.d(TAG, listaProdutos.toString());
        ProdutoAdapter mProdutoAdapter = new ProdutoAdapter(this,listaProdutos);
        lv.setAdapter(mProdutoAdapter);
    }
}
