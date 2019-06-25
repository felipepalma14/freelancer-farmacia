package com.farmacia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmacia.databases.Database;
import com.farmacia.models.Cidade;
import com.farmacia.models.Farmacia;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CadastraFarmaciaActivity extends AppCompatActivity {

    private static final String TAG = CadastroUsuarioActivity.class.getSimpleName();
    private static Database mDatabase;
    private Bitmap mBitmap;

    MapFarmaciaFragment mapaFragment = new MapFarmaciaFragment();

    Farmacia mFarmacia = new Farmacia();

    EditText edtNome,edtCnpj , edtTelefone, edtCEP, edtEndereco;
    ImageView imgFarmacia;
    Spinner spnCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_farmacia);

        edtNome = findViewById(R.id.edt_nome_farmacia);
        edtCnpj = findViewById(R.id.edt_cnpj_farmacia);
        edtTelefone = findViewById(R.id.edt_telefone_farmacia);
        edtCEP = findViewById(R.id.edt_cep_farmacia);
        edtEndereco = findViewById(R.id.edt_endereco_farmacia);

        imgFarmacia = findViewById(R.id.img_farmacia);
        spnCidade = findViewById(R.id.spn_cidade);

        mDatabase = new Database(this);

        mDatabase.open();

        initializeUI ();


        mapaFragment.setmCadastraFarmaciaActivity(this);

        addFragment(mapaFragment,false, "Mapa");
    }

    public void botaoClicado(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btn_salva_farmacia:
                getValues();
                cadastrarFarmacia();
                break;
            case R.id.img_farmacia:
                selectImage();
                break;
        }
    }


    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.container_frame_back, fragment, tag);
        ft.commitAllowingStateLoss();
    }


    // function to get values from the Edittext and image
    private void getValues(){

        String nome = edtNome.getText().toString();
        String cnpj = edtCnpj.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String cep = edtCEP.getText().toString();
        String endereco = edtEndereco.getText().toString();

        Cidade cidade = (Cidade) spnCidade.getSelectedItem();

        //byte[] produtoImagem = profileImage(mBitmap);
        mFarmacia.setNome(nome);
        mFarmacia.setCpnj(cnpj);
        mFarmacia.setCep(cep);
        mFarmacia.setTelefone(telefone);
        mFarmacia.setEndereco(endereco);
        mFarmacia.setCidade(cidade);
        byte[] farmaciaImagem = profileImage(mBitmap);
        mFarmacia.setImagem(farmaciaImagem);
        Log.d("FARM", mFarmacia.toString());

    }


    public void setValues(){
        edtCEP.setText(mFarmacia.getCep());
        edtEndereco.setText(mFarmacia.getEndereco());
    }

    public void selectImage(){

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 2);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 2:
                if(resultCode == RESULT_OK){
                    Uri choosenImage = data.getData();

                    if(choosenImage !=null){

                        mBitmap = decodeUri(choosenImage, 400);
                        imgFarmacia.setImageBitmap(mBitmap);
                    }
                }
        }
    }


    //COnvert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    private byte[] profileImage(Bitmap b){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }


    private void initializeUI() {

        ArrayList<Cidade> cidades= mDatabase.mCidadeDao.getTodasCidades();

        ArrayAdapter<Cidade> adapter =
                new ArrayAdapter<Cidade>(getApplicationContext(),R.layout.spinner_item, cidades);

        spnCidade.setAdapter(adapter);

    }

    private void cadastrarFarmacia(){
        boolean resultado = mDatabase.mfarmaciaDAO.adicionarFarmacia(mFarmacia);

        if (resultado) {
            Intent intent = new Intent(CadastraFarmaciaActivity.this, ListaFarmaciaActivity.class);

            startActivity(intent);
            limparCampos();
            Toast.makeText(getBaseContext(), "Dados inseridos!!", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(getBaseContext(), "DEU ERROR!!", Toast.LENGTH_LONG).show();
        }
    }

    private  void limparCampos(){
        edtNome.setText("");
        edtEndereco.setText("");
        edtCnpj.setText("");
        edtTelefone.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }
}
