package com.farmacia.databases.schema;

/**
 * Created by Felipe Palma on 21/06/2019.
 */
public interface IFarmaciaSchema {
    String FARMACIA_TABLE = "farmacia";
    String COLUNA_ID = "_id";
    String COLUNA_ENDERECO= "endereco";
    String COLUNA_NOME = "nome";
    String COLUNA_TELEFONE = "telefone";
    String COLUNA_CEP = "cep";
    String COLUNA_CNPJ = "cnpj";
    String COLUNA_LATITUDE = "latitude";
    String COLUNA_LONGITUDE = "longitude";

    //String COLUNA_HORARIO = "horario";

    String COLUNA_ID_CIDADE = "_id_cidade";



    String FARMACIA_TABLE_CREATE =  "CREATE TABLE IF NOT EXISTS "
            + FARMACIA_TABLE
            + " ("
            + COLUNA_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUNA_ENDERECO
            + " TEXT NOT NULL, "
            + COLUNA_NOME
            + " TEXT NOT NULL, "
            + COLUNA_TELEFONE
            + " TEXT NOT NULL, "
            + COLUNA_CEP
            + " TEXT NOT NULL, "
            + COLUNA_CNPJ
            + " TEXT NOT NULL, "
            + COLUNA_LATITUDE
            + " TEXT NOT NULL, "
            + COLUNA_LONGITUDE
            + " TEXT NOT NULL, "
            + COLUNA_ID_CIDADE
            + " INTEGER NOT NULL, "
            //FK
            + "FOREIGN KEY( "
            + COLUNA_ID_CIDADE
            + " ) REFERENCES "
            + ICidadeSchema.CIDADE_TABLE
            + "(_id) "
            + ")";

    String[] FARMACIA_COLUNAS = new String[] {
            COLUNA_ID,COLUNA_CNPJ,COLUNA_ENDERECO,COLUNA_TELEFONE,
            COLUNA_NOME,COLUNA_CEP,COLUNA_ID_CIDADE, COLUNA_LATITUDE , COLUNA_LONGITUDE
    };
}
