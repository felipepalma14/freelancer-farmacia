package com.farmacia;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBFarmacia extends SQLiteOpenHelper  {
    public static final String nomedb="bdfarmacia";
    public static final String tb_usuario="tb_usuario";
    public static final String tb_produtos="tb_produtos";
    public static final String nome="nome";
    public static final String email="email";
    public static final String CPF="cpf";
    public static final String idade="idade";
    public static final String cidade="cidade";
    public static final String Senha="senha";
    public static final String nomeProduto="nomePro";
    public static final String idProduto="idProduto";
    public static final String valorProduto="valorProduto";
    public static final int VERSAO=1;

    public DBFarmacia(Context context){
        super(context,nomedb,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1="CREATE TABLE "+tb_usuario+"("+CPF+" integer  primary key,"+nome+" text,"+email+" text,"+idade+" text,"+cidade+" text,"+Senha+" text)";
        String sql2="CREATE TABLE "+tb_produtos+"("+idProduto+" integer primary key autoincrement,"+nomeProduto+" text,"+valorProduto+" text)";

        db.execSQL(sql1);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ tb_usuario);
        db.execSQL("DROP TABLE IF EXISTS "+ tb_produtos);
        onCreate(db);
    }

    public Boolean logincheck(String CPF, String senha){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from tb_usuario where cpf=? and senha=?",new String[]{CPF,senha});
        if(cursor.getCount()>0)return true;
        else return false;
    }


}
