package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnCadastro;
    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtCPF;
    private EditText txtIdade;
    private EditText txtCidade;
    private EditText etxSenha;
    private Button JaCadastrei;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNome=findViewById(R.id.txtNome);
        txtEmail=findViewById(R.id.email);
        txtCPF=findViewById(R.id.CPF);
        txtIdade=findViewById(R.id.Idade);
        txtCidade=findViewById(R.id.Cidade);
        btnCadastro=findViewById(R.id.Cadastro);
        etxSenha=findViewById(R.id.etxSenhaL);
        JaCadastrei=findViewById(R.id.btnJaCadastrei);

        JaCadastrei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbcontroller controle= new dbcontroller(getBaseContext());
                String nomeS= txtNome.getText().toString();
                String emailS=txtEmail.getText().toString();
                String cpfS=txtCPF.getText().toString();
                String idadeS=txtIdade.getText().toString();
                String cidades=txtCidade.getText().toString();
                String senhaS=etxSenha.getText().toString();
                String resultado;

                resultado=controle.insertUser(cpfS,nomeS,emailS,idadeS,cidades,senhaS);
                Intent intent= new Intent(MainActivity.this, telaprodutos.class);
                startActivity(intent);


                Toast.makeText(getBaseContext(),resultado,Toast.LENGTH_LONG).show();
            }
        });



    }
}
