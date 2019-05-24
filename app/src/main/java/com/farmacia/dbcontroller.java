package com.farmacia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.farmacia.DBFarmacia.valorProduto;

public class dbcontroller {
    private SQLiteDatabase db;
    static private DBFarmacia banco;
    private String erro="dados inseridos com sucesso";


    public dbcontroller(Context context){
       banco=new DBFarmacia(context);
    }

    public String insertUser(String _CPF,String _nome, String _email, String _idade, String _cidade, String _senha ){
        ContentValues valores;
        long resultado;

        db=banco.getWritableDatabase();
        valores= new ContentValues();
        valores.put(DBFarmacia.CPF,_CPF);
        valores.put(DBFarmacia.nome,_nome);
        valores.put(DBFarmacia.email,_email);
        valores.put(DBFarmacia.idade,_idade);
        valores.put(DBFarmacia.cidade,_cidade);
        valores.put(DBFarmacia.Senha,_senha);

        resultado=db.insert(DBFarmacia.tb_usuario,null,valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return erro;

    }public String insertProduto(String _nomeproduto, String _valorproduto){
        ContentValues valoresproduto;
        long resultado2;

        db=banco.getWritableDatabase();
        valoresproduto=new ContentValues();
        valoresproduto.put(DBFarmacia.nomeProduto,_nomeproduto);
        valoresproduto.put(valorProduto,_valorproduto);
        resultado2=db.insert(DBFarmacia.tb_produtos,null,valoresproduto);

        if(resultado2==-1){
            return "Erro ao inserir";

        }else{
            return  "inserido com sucesso";
        }

    }public Cursor carregarDados(){
        Cursor cursor;
        String[] campos={banco.nomeProduto,banco.valorProduto};
        db=banco.getReadableDatabase();
        cursor=db.query(banco.tb_produtos,campos,null,null,null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }public List<produtos> retornaProduto(){
        List lsProdutos = new ArrayList<produtos>();
        SQLiteDatabase db= banco.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select nomePro, valorProduto from tb_produtos", null);
        if(cursor.moveToFirst()){
            do{
                produtos p= new produtos();
                p.setNome(cursor.getString(0));
                p.setValor(cursor.getString(1));
                lsProdutos.add(p);

            }while(cursor.moveToNext());
        }return lsProdutos;
    }
}

