package com.farmacia.databases;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.farmacia.databases.DAO.CategoriaDAO;
import com.farmacia.databases.DAO.CidadeDAO;
import com.farmacia.databases.DAO.FarmaciaDAO;
import com.farmacia.databases.DAO.ForumDAO;
import com.farmacia.databases.DAO.ProdutoDAO;
import com.farmacia.databases.DAO.UsuarioDAO;
import com.farmacia.databases.DAO.interfaces.ICategoriaDAO;
import com.farmacia.databases.DAO.interfaces.IProdutoDAO;
import com.farmacia.databases.schema.ICategoriaSchema;
import com.farmacia.databases.schema.ICidadeSchema;
import com.farmacia.databases.schema.IFarmaciaSchema;
import com.farmacia.databases.schema.IForumSchema;
import com.farmacia.databases.schema.IProdutoSchema;
import com.farmacia.databases.schema.IUsuarioSchema;
import com.farmacia.models.Produto;

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
    public static ProdutoDAO mProdutoDAO;
    public static CategoriaDAO mCategoriaDAO;
    public static FarmaciaDAO mfarmaciaDAO;
    public static ForumDAO mForumDAO;




    public Database open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();


        mCidadeDao = new CidadeDAO(mSqLiteDatabase);
        mCategoriaDAO = new CategoriaDAO(mSqLiteDatabase);

        mUsuarioDao = new UsuarioDAO(mSqLiteDatabase, mCidadeDao);
        mProdutoDAO = new ProdutoDAO(mSqLiteDatabase,mCategoriaDAO,mfarmaciaDAO);
        mfarmaciaDAO = new FarmaciaDAO(mSqLiteDatabase,mCidadeDao);
        mForumDAO = new ForumDAO(mSqLiteDatabase, mUsuarioDao, mfarmaciaDAO);


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
            db.execSQL(ICategoriaSchema.CATEGORIA_TABLE_CREATE);
            db.execSQL(ICidadeSchema.CIDADE_TABLE_CREATE);
            db.execSQL(IProdutoSchema.PRODUTO_TABLE_CREATE);
            db.execSQL(IFarmaciaSchema.FARMACIA_TABLE_CREATE);
            db.execSQL(IForumSchema.FORUM_TABLE_CREATE);


            db.execSQL(ICidadeSchema.SQL_INSERT_CIDADES);
            db.execSQL(ICategoriaSchema.SQL_INSERT_CIDADES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version "
                    + oldVersion + " to "
                    + newVersion + " which destroys all old data");

            db.execSQL("DROP TABLE IF EXISTS "
                    + IUsuarioSchema.USUARIO_TABLE);

            db.execSQL("DROP TABLE IF EXISTS "
                    + IProdutoSchema.PRODUTO_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "
                    + IFarmaciaSchema.FARMACIA_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "
                    + IForumSchema.FORUM_TABLE);

            onCreate(db);

        }
    }
}
