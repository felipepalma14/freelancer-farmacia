package com.farmacia.databases.schema;

/**
 * Created by Felipe Palma on 21/06/2019.
 */
public interface IForumSchema {
    String FORUM_TABLE = "forum";
    String COLUNA_ID = "_id";
    String COLUNA_CPF= "cpf";
    String COLUNA_NOME = "nome";
    String COLUNA_EMAIL = "email";
    String COLUNA_SENHA = "senha";
    String COLUNA_IDADE = "idade";

    String COLUNA_ID_CIDADE = "_id_cidade";



    String USUARIO_TABLE_CREATE =  "CREATE TABLE IF NOT EXISTS "
            + FORUM_TABLE
            + " ("
            + COLUNA_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_CPF
            + " TEXT NOT NULL, "
            + COLUNA_NOME
            + " TEXT NOT NULL, "
            + COLUNA_EMAIL
            + " TEXT NOT NULL, "
            + COLUNA_SENHA
            + " TEXT NOT NULL, "
            + COLUNA_IDADE
            + " INTEGER NOT NULL, "
            + COLUNA_ID_CIDADE
            + " INTEGER NOT NULL, "
            //FK
            + "FOREIGN KEY( "
            + COLUNA_ID_CIDADE
            + " ) REFERENCES "
            + ICidadeSchema.CIDADE_TABLE
            + "(_id) "
            + ")";

    String[] USUARIO_COLUNAS = new String[] {
            COLUNA_ID,COLUNA_CPF,COLUNA_EMAIL,COLUNA_SENHA,
            COLUNA_NOME,COLUNA_IDADE,  COLUNA_ID_CIDADE
    };
}
