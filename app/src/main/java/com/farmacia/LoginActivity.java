package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farmacia.databases.Database;
import com.farmacia.models.Usuario;
import com.farmacia.utils.LoginSingleton;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLogin, edtSenha;

    private Button btnLogin, btnCadastrar;


    private static final String TAG = LoginActivity.class.getSimpleName();
    private static Database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = findViewById(R.id.edtLogin);
        edtSenha = findViewById(R.id.edtSenha);

        btnLogin = findViewById(R.id.btnLogar);
        btnCadastrar = findViewById(R.id.btnCriarConta);

        mDatabase = new Database(this);
        mDatabase.open();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginS= edtLogin.getText().toString();
                String senhaS=edtSenha.getText().toString();

                Usuario mUsuario =  mDatabase.mUsuarioDao.fazerLogin(loginS,senhaS);

                if(mUsuario != null){
//                    Intent i = new Intent(LoginActivity.this, ListaProdutosActivity.class);
                    Intent i = new Intent(LoginActivity.this, OpcoesActivity.class);
                    Toast.makeText(LoginActivity.this,"Seja bem vindo: " + mUsuario.getNome(),Toast.LENGTH_LONG).show();

                    mDatabase.close();
                    LoginSingleton.getInstance().setUsuarioAutenticado(mUsuario);

                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "CPF ou Senha errados",Toast.LENGTH_LONG).show();
                }
            }
        });




        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(n);
            }
        });




    }
}
