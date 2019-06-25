package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.farmacia.adapters.FarmaciaAdapter;
import com.farmacia.adapters.ProdutoAdapter;
import com.farmacia.databases.Database;
import com.farmacia.models.Farmacia;
import com.farmacia.models.Produto;

import java.util.ArrayList;

public class ListaFarmaciaActivity extends AppCompatActivity {

    private static final String TAG = ListaProdutosActivity.class.getSimpleName();
    private static Database mDatabase;

    private ListView lvFarmacia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_farmacia);

        lvFarmacia = findViewById(R.id.lista_farmacias);

        mDatabase = new Database(this);
        mDatabase.open();


        final ArrayList<Farmacia> listaFarmacias = mDatabase.mfarmaciaDAO.getTodasFarmacias();

        mostrarLista(listaFarmacias, lvFarmacia);

        lvFarmacia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CadastraProdutoActivity.class);
                intent.putExtra("FARMACIA",listaFarmacias.get(position).getId());
                startActivity(intent);
            }
        });

    }

    private void mostrarLista(ArrayList<Farmacia> listaFarmacias, ListView lv) {
        FarmaciaAdapter adapter = new FarmaciaAdapter(this,listaFarmacias);
        lv.setAdapter(adapter);
    }
}
