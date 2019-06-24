package com.farmacia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmacia.databases.Database;
import com.farmacia.models.Categoria;
import com.farmacia.models.Produto;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CadastraProdutoActivity extends AppCompatActivity {


    private static final String TAG = CadastraProdutoActivity.class.getSimpleName();
    private static Database mDatabase;

    private Bitmap mBitmap;
    private ImageView imgView;

    private EditText edtProdutoNome, edtProdutoPeso;
    private Spinner spnCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrarproduto);

        imgView = findViewById(R.id.imgView);
        edtProdutoNome = findViewById(R.id.edt_nome_produto);
        edtProdutoPeso = findViewById(R.id.edt_peso_produto);

        spnCategoria = findViewById(R.id.spn_categoria);

        mDatabase = new Database(this);

        initializeUI();





    }

    public void adicionarProduto(){

        try {
            if(mDatabase.mProdutoDAO.adicionarProduto(getValues())){
                Toast.makeText(CadastraProdutoActivity.this,"Produto cadastrado com sucesso!! ",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(CadastraProdutoActivity.this,"Não foi possível cadastrar produto: ",Toast.LENGTH_LONG).show();

            }

        }catch(Exception exc){
            Toast.makeText(CadastraProdutoActivity.this,"Não foi possível cadastrar produto: "+exc,Toast.LENGTH_LONG).show();

        }finally {
            mDatabase.close();
        }

        Intent tela= new Intent(CadastraProdutoActivity.this, ListaProdutosActivity.class);
        startActivity(tela);

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
                        imgView.setImageBitmap(mBitmap);
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



    // function to get values from the Edittext and image
    private Produto getValues(){

        String produtoNome = edtProdutoNome.getText().toString();
        String produtoPeso = edtProdutoPeso.getText().toString();
        Categoria categoria = (Categoria)spnCategoria.getSelectedItem();

        byte[] produtoImagem = profileImage(mBitmap);

        return new Produto(categoria, produtoNome, produtoPeso,produtoImagem);
    }


    public void botaoClicado(View view) {
        int id = view.getId();

        switch (id){
            case R.id.imgView:
                selectImage();
                break;
            case R.id.btnSalvaProduto:
                adicionarProduto();
                break;

        }
    }

    private void initializeUI() {

        ArrayList<Categoria> categorias= mDatabase.mCategoriaDAO.getTodasCategorias();

        ArrayAdapter<Categoria> adapter =
                new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item, categorias);

        spnCategoria.setAdapter(adapter);

    }
}
