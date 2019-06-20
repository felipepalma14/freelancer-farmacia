package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText Login,Senha;
    Button Logar,Cadastrar;
    DBFarmacia db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login=findViewById(R.id.etxLogin);
        Senha=findViewById(R.id.etxSenhaL);
        Logar=findViewById(R.id.btnLogar);
        Cadastrar=findViewById(R.id.btnCriarConta);
        db= new DBFarmacia(this);

        Logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginS=Login.getText().toString();
                String senhaS=Senha.getText().toString();
                Boolean loginCheck= db.logincheck(loginS,senhaS);
                if(loginCheck==true){
                    Intent i = new Intent(LoginActivity.this, ListaProdutos.class);
                    Toast.makeText(LoginActivity.this,"Seja bem vindo",Toast.LENGTH_LONG).show();
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this, "CPF ou Senha errados",Toast.LENGTH_LONG).show();
                }
            }
        });




        Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(n);
            }
        });




    }
}
