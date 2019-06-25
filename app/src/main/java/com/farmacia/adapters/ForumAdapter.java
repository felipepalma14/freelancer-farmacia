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
import com.farmacia.models.Farmacia;
import com.farmacia.models.Forum;

import java.util.ArrayList;

/**
 * Created by Felipe Palma on 24/06/2019.
 */
public class ForumAdapter extends ArrayAdapter<Forum> {

    Context context;
    ArrayList<Forum> mLista;


    public ForumAdapter(Context context, ArrayList<Forum> forum){
        super(context, R.layout.lista_item_produto_adapter, forum);
        this.context=context;
        this.mLista = forum;
    }

    public  class  Holder{
        TextView nome,topico,localizacao;
        ImageView pic;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        Forum data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {


            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lista_item_forum_adapter, parent, false);

            viewHolder.nome = (TextView) convertView.findViewById(R.id.txt_forum_usuario_nome);
            viewHolder.topico = (TextView) convertView.findViewById(R.id.txt_forum_topico);
            viewHolder.localizacao = (TextView) convertView.findViewById(R.id.txt_forum_endereco);
           // viewHolder.pic = (ImageView) convertView.findViewById(R.id.img_farmacia);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }


        viewHolder.nome.setText(data.getUsuario().getNome());
        viewHolder.topico.setText(data.getTopico());
        viewHolder.localizacao.setText("Obter Localização da Farmácia");
        //viewHolder.pic.setImageBitmap(convertToBitmap(data.getImagem()));


        // Return the completed view to render on screen
        return convertView;
    }
    //get bitmap image from byte array

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

}
