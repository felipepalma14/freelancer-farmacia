package com.farmacia.databases.schema;

/**
 * Created by Felipe Palma on 21/06/2019.
 */
public interface ICategoriaSchema {

    String CATEGORIA_TABLE = "categoria";
    String COLUNA_ID = "_id";
    String COLUNA_DESCRICAO = "descricao";

    String CATEGORIA_TABLE_CREATE =  "CREATE TABLE IF NOT EXISTS "
            + CATEGORIA_TABLE
            + " ("
            + COLUNA_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,, "
            + COLUNA_DESCRICAO
            + " TEXT NOT NULL, "
            + ")";
    String[] CATEGORIA_COLUNAS = new String[] { COLUNA_ID,COLUNA_DESCRICAO };

    String SQL_INSERT_CIDADES = "INSERT INTO categoria(descricao) VALUES ('Promoções'), ('Alergias e Infecções'),('Asma'),('Calmantes')";

}
