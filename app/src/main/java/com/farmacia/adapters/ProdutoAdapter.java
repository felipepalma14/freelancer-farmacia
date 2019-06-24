package com.farmacia.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmacia.R;
import com.farmacia.models.Produto;

import java.util.ArrayList;

/**
 * Created by Felipe Palma on 24/06/2019.
 */
public class ProdutoAdapter extends ArrayAdapter<Produto> {

    Context context;
    ArrayList<Produto> mListaProdutos;


    public ProdutoAdapter(Context context, ArrayList<Produto> produtos){
        super(context, R.layout.lista_item_produto_adapter, produtos);
        this.context=context;
        this.mListaProdutos = produtos;
    }

    public  class  Holder{
        TextView nomeProduto,categoriaProduto;
        ImageView pic;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        Produto data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {


            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lista_item_produto_adapter, parent, false);

            viewHolder.nomeProduto = (TextView) convertView.findViewById(R.id.txtViewer);
            viewHolder.categoriaProduto = (TextView) convertView.findViewById(R.id.txtCategoria);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.imgView);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }


        viewHolder.nomeProduto.setText("Produto: " + data.getDescricao());
        viewHolder.categoriaProduto.setText("Categoria: " + data.getDescricao());

        viewHolder.pic.setImageBitmap(convertToBitmap(data.getImage()));


        // Return the completed view to render on screen
        return convertView;
    }
    //get bitmap image from byte array

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

}
