package com.farmacia.databases.schema;

/**
 * Created by Felipe Palma on 21/06/2019.
 */
public interface IProdutoSchema {
    String PRODUTO_TABLE = "produto";
    String COLUNA_ID = "_id";
    String COLUNA_NOME = "nome";
    String COLUNA_PESO = "peso";

    String COLUNA_ID_CATEGORIA = "_id_categoria";



    String PRODUTO_TABLE_CREATE =  "CREATE TABLE IF NOT EXISTS "
            + PRODUTO_TABLE
            + " ("
            + COLUNA_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_NOME
            + " TEXT NOT NULL, "
            + COLUNA_PESO
            + " INTEGER NOT NULL, "
            + COLUNA_ID_CATEGORIA
            + " INTEGER NOT NULL, "
            //FK
            + "FOREIGN KEY( "
            + COLUNA_ID_CATEGORIA
            + " ) REFERENCES "
            + ICategoriaSchema.CATEGORIA_TABLE
            + "(_id) "
            + ")";

    String[] PRODUTO_COLUNAS = new String[] {
            COLUNA_ID,COLUNA_NOME,COLUNA_PESO, COLUNA_ID_CATEGORIA
    };
}
