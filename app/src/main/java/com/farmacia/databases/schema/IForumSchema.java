package com.farmacia.databases.schema;

import com.farmacia.databases.DAO.interfaces.IUsuarioDAO;

/**
 * Created by Felipe Palma on 21/06/2019.
 */
public interface IForumSchema {
    String FORUM_TABLE = "forum";
    String COLUNA_ID = "_id";
    String COLUNA_USUARIO_ID= "usuario_id";
    String COLUNA_TOPICO = "topico";
    String COLUNA_FARMACIA_ID = "farmacia_id";
    //String COLUNA_COMENTARIO = "comentario";




    String FORUM_TABLE_CREATE =  "CREATE TABLE IF NOT EXISTS "
            + FORUM_TABLE
            + " ("
            + COLUNA_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUNA_USUARIO_ID
            + " INTEGER NOT NULL, "
            + COLUNA_FARMACIA_ID
            + " INTEGER NOT NULL, "
            + COLUNA_TOPICO
            + " TEXT NOT NULL, "
            //FK
            + "FOREIGN KEY( "
            + COLUNA_FARMACIA_ID
            + " ) REFERENCES "
            + IFarmaciaSchema.FARMACIA_TABLE
            + "(_id), "
            //FK
            + "FOREIGN KEY( "
            + COLUNA_USUARIO_ID
            + " ) REFERENCES "
            + IUsuarioSchema.USUARIO_TABLE
            + "(_id) "
            + ")";

    String[] FORUM_COLUNAS = new String[] {
            COLUNA_ID,COLUNA_USUARIO_ID,COLUNA_TOPICO,COLUNA_FARMACIA_ID
    };
}
