package com.farmacia.databases.schema;

/**
 * Created by Felipe Palma on 21/06/2019.
 */
public interface ICidadeSchema {
    String CIDADE_TABLE = "cidade";
    String COLUNA_ID = "_id";
    String COLUNA_DESCRICAO = "descricao";

    String CIDADE_TABLE_CREATE =  "CREATE TABLE IF NOT EXISTS "
            + CIDADE_TABLE
            + " ("
            + COLUNA_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUNA_DESCRICAO
            + " TEXT NOT NULL "
            + ")";

    String[] CIDADE_COLUNAS = new String[] { COLUNA_ID,COLUNA_DESCRICAO };



    String SQL_INSERT_CIDADES = "INSERT INTO cidade(descricao) VALUES ('Manaus'), ('São Paulo'),('Distrito Federal')";
}
