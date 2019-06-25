package com.farmacia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmacia.databases.Database;
import com.farmacia.models.Cidade;
import com.farmacia.models.Usuario;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CadastroUsuarioActivity extends AppCompatActivity {


    private EditText edtNome,edtEmail, edtCPF,edtIdade, edtSenha, edtConfirmaSenha;
    private Spinner spnCidade;
    private Button btnTenhoCadastro,  btnCadastrar;

    private static final String TAG = CadastroUsuarioActivity.class.getSimpleName();
    private static Database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNome = findViewById(R.id.txtNome);
        edtEmail = findViewById(R.id.email);
        edtCPF = findViewById(R.id.CPF);
        edtIdade = findViewById(R.id.Idade);
        spnCidade = findViewById(R.id.spnCidade);
        edtSenha=findViewById(R.id.edtSenha);
        edtConfirmaSenha=findViewById(R.id.edtSenha);

        btnCadastrar=findViewById(R.id.Cadastro);
        btnTenhoCadastro=findViewById(R.id.btnJaCadastrei);


        mDatabase = new Database(this);
        mDatabase.open();


        initializeUI();

        getIPAddress();

        btnTenhoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(CadastroUsuarioActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeS = edtNome.getText().toString();
                String emailS =edtEmail.getText().toString();
                String cpfS =edtCPF.getText().toString();
                String idadeS =edtIdade.getText().toString();
                String senhaS =edtSenha.getText().toString();

                Cidade cidade = (Cidade) spnCidade.getSelectedItem();
                String senhaConfirmaS =edtConfirmaSenha.getText().toString();

                if ((senhaConfirmaS.equals(senhaS))) {

                    boolean resultado;

                    Usuario mUsuario = new Usuario(cidade, cpfS, nomeS, emailS, senhaS, Integer.valueOf(idadeS));

                    resultado = mDatabase.mUsuarioDao.adicionarUsuario(mUsuario);
                    Intent intent;
                    if (resultado) {
                        if(mUsuario.getCpf().equals("000")){

                          intent   = new Intent(CadastroUsuarioActivity.this, AdminActivity.class);
                        }else{

                            intent   = new Intent(CadastroUsuarioActivity.this, ListaProdutosActivity.class);
                        }

                        startActivity(intent);
                        limparCampos();
                        Toast.makeText(getBaseContext(), "Dados inseridos!!", Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(getBaseContext(), "DEU ERROR!!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(), "Por favor, senhas diferentes", Toast.LENGTH_LONG).show();
                }
            }
        });

//        spnCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("ITEM", mDatabase.mCidadeDao.getTodasCidades().get(position).getNome());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


    }

    private void initializeUI() {

        ArrayList<Cidade> cidades= mDatabase.mCidadeDao.getTodasCidades();

        ArrayAdapter<Cidade> adapter =
                new ArrayAdapter<Cidade>(getApplicationContext(),R.layout.spinner_item, cidades);

        spnCidade.setAdapter(adapter);

    }

    private  void limparCampos(){
        edtNome.setText("");
        edtEmail.setText("");
        edtCPF.setText("");
        edtIdade.setText("");
        //edtCidade.setText("");
        edtSenha.setText("");
        edtConfirmaSenha.setText("");
    }

    public static String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {

                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());

                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        intf.getName();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;
                      if (intf.getName().equals("eth0") && isIPv4) {

                          Log.d("IP",sAddr );
                          return sAddr;
                      }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }
}
