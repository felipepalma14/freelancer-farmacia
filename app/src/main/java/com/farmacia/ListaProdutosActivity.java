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
import com.farmacia.models.Farmacia;
import com.farmacia.models.Produto;
import com.farmacia.utils.LoginSingleton;

import java.util.ArrayList;

public class ListaProdutosActivity extends AppCompatActivity {

    private static final String TAG = ListaProdutosActivity.class.getSimpleName();
    private Database mDatabase;
    private Farmacia mFarmacia;
    private ArrayList<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telaprodutos);

        Bundle extras = getIntent().getExtras();


        mDatabase = new Database(this);
        mDatabase.open();

        if (extras != null) {
            int idFarmacia = extras.getInt("FARMACIA");
            mFarmacia = mDatabase.mfarmaciaDAO.getFarmaciaPorId(idFarmacia);
            listaProdutos = mDatabase.mProdutoDAO.getTodosProdutosPorFarmacia(mFarmacia.getId());
        }else{
            mDatabase = new Database(this);
            mDatabase.open();
            listaProdutos = mDatabase.mProdutoDAO.getTodosProdutos();
        }


        Log.d(TAG, listaProdutos.toString());


        ListView lv= findViewById(R.id.listaProdutos);

        mostrarListaProdutos(listaProdutos, lv);

        Button btnMaps=(Button)findViewById(R.id.btnMaps);

        Button btnAddProduto=(Button) findViewById(R.id.btnAddProduto);

        if(!LoginSingleton.getInstance().getUsuarioAutenticado().getCpf().equals("000")) {
            btnAddProduto.setVisibility(View.INVISIBLE);
        }

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
        ProdutoAdapter mProdutoAdapter = new ProdutoAdapter(this,listaProdutos);
        lv.setAdapter(mProdutoAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }
}
