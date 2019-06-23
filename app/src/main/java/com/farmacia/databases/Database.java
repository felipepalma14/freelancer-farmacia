package com.farmacia.databases;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.farmacia.databases.DAO.CidadeDAO;
import com.farmacia.databases.DAO.UsuarioDAO;
import com.farmacia.databases.schema.ICidadeSchema;
import com.farmacia.databases.schema.IUsuarioSchema;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public class Database {

    private static final String TAG = "FARMACIA_DB";
    private static final String DATABASE_NAME = "farmacia.db";
    private DatabaseHelper mDatabaseHelper;
    // Increment DB Version on any schema change
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;
    public static UsuarioDAO mUsuarioDao;
    public static CidadeDAO mCidadeDao;



    public Database open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();

        mUsuarioDao = new UsuarioDAO(mSqLiteDatabase);
        mCidadeDao = new CidadeDAO(mSqLiteDatabase);
        return this;
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public Database(Context context) {
        this.mContext = context;
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(IUsuarioSchema.USUARIO_TABLE_CREATE);

            db.execSQL(ICidadeSchema.CIDADE_TABLE_CREATE);
            db.execSQL(ICidadeSchema.SQL_INSERT_CIDADES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.w(TAG, "Upgrading database from version "
                    + oldVersion + " to "
                    + newVersion + " which destroys all old data");

            db.execSQL("DROP TABLE IF EXISTS "
                    + IUsuarioSchema.USUARIO_TABLE);
            onCreate(db);

        }
    }
}
