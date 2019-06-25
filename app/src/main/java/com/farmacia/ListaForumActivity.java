package com.farmacia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.farmacia.adapters.ForumAdapter;
import com.farmacia.adapters.ProdutoAdapter;
import com.farmacia.databases.Database;
import com.farmacia.models.Forum;
import com.farmacia.models.Produto;

import java.util.ArrayList;

public class ListaForumActivity extends AppCompatActivity {

    private static final String TAG = ListaFarmaciaActivity.class.getSimpleName();
    private static Database mDatabase;
    private  ArrayList<Forum> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_forum);

        mDatabase = new Database(this);
        mDatabase.open();


       lista = mDatabase.mForumDAO.getTodosForuns();

        ListView lv= findViewById(R.id.listaForum);

        mostrarLista(lista, lv);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Forum forum = lista.get(position);
                String uri = "geo:0,0?q="+ forum.getFarmacia().getGeoLocalizacao().latitude + "," + forum.getFarmacia().getGeoLocalizacao().longitude  + " (" + forum.getFarmacia().getNome() + ")";
                startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });




    }

    private void mostrarLista(ArrayList<Forum> lista, ListView lv) {
        ForumAdapter mAdapter = new ForumAdapter(this,lista);
        lv.setAdapter(mAdapter);
    }


    public void adicionarForum(View view) {
        Intent tela = new Intent(ListaForumActivity.this, ForumActivity.class);
        startActivity(tela);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }
}
