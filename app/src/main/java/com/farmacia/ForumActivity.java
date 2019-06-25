package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmacia.databases.Database;
import com.farmacia.models.Cidade;
import com.farmacia.models.Farmacia;
import com.farmacia.models.Forum;
import com.farmacia.utils.LoginSingleton;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {

    private EditText edtTopico;
    private Spinner spnFarmacia;
    private static final String TAG = CadastroUsuarioActivity.class.getSimpleName();
    private static Database mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        edtTopico = findViewById(R.id.edt_topico_forum);
        spnFarmacia = findViewById(R.id.spn_farmacia);

        mDatabase = new Database(this);
        mDatabase.open();

        initializeUI();

    }

    private void initializeUI() {

        ArrayList<Farmacia> farmacias= mDatabase.mfarmaciaDAO.getTodasFarmacias();

        ArrayAdapter<Farmacia> adapter =
                new ArrayAdapter<Farmacia>(getApplicationContext(),R.layout.spinner_item, farmacias);

        spnFarmacia.setAdapter(adapter);

    }

    private  void limparCampos(){
        edtTopico.setText("");

    }

    public void registrarForum(View view) {
        Forum  mForum = new Forum();

        mForum.setTopico(edtTopico.getText().toString());
        mForum.setUsuario(LoginSingleton.getInstance().getUsuarioAutenticado());
        Farmacia mFarmacia = (Farmacia) spnFarmacia.getSelectedItem();

        mForum.setFarmacia(mFarmacia);

        boolean resultado = mDatabase.mForumDAO.adicionarForum(mForum);
        Intent intent;
        if (resultado) {

                intent   = new Intent(ForumActivity.this, ListaForumActivity.class);

            startActivity(intent);
            limparCampos();
            Toast.makeText(getBaseContext(), "Dados inseridos!!", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(getBaseContext(), "DEU ERROR!!", Toast.LENGTH_LONG).show();
        }
    }
}
