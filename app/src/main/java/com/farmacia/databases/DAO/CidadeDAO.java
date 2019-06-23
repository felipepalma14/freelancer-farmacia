package com.farmacia.databases.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.farmacia.databases.DAO.interfaces.ICidadeDAO;
import com.farmacia.databases.provider.DatabaseContentProvider;
import com.farmacia.databases.schema.ICidadeSchema;
import com.farmacia.models.Cidade;
import com.farmacia.models.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public class CidadeDAO extends DatabaseContentProvider implements ICidadeSchema, ICidadeDAO {

    private Cursor cursor;
    private ContentValues initialValues;


    public CidadeDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public Cidade getCidadePorId(int cidadeId) {
        final String selectionArgs[] = { String.valueOf(cidadeId) };
        final String selection = COLUNA_ID + " = ?";
        Cidade mCidade = new Cidade();
        cursor = super.query(CIDADE_TABLE, CIDADE_COLUNAS, selection,
                selectionArgs, COLUNA_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mCidade = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return mCidade;
    }

    @Override
    public ArrayList<Cidade> getTodasCidades() {
        ArrayList<Cidade> cidadeLista = new ArrayList<>();
        cursor = super.query(CIDADE_TABLE, CIDADE_COLUNAS, null,
                null, COLUNA_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Cidade item = cursorToEntity(cursor);
                cidadeLista.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return cidadeLista;
    }

    @Override
    protected Cidade cursorToEntity(Cursor cursor) {
        Cidade mCidade = new Cidade();

        int idIndex;
        int cidadeNomeIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUNA_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUNA_ID);
                mCidade.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUNA_DESCRICAO) != -1) {
                cidadeNomeIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_DESCRICAO);
                mCidade.setNome(cursor.getString(cidadeNomeIndex));
            }



        }
        return mCidade;
    }

    private void setContentValue(Cidade cidade) {
        initialValues = new ContentValues();
        if (cidade.getId() > 0) {
            initialValues.put(COLUNA_ID, cidade.getId());
        }
        initialValues.put(COLUNA_DESCRICAO, cidade.getNome());

    }

    private ContentValues getContentValue() {
        return initialValues;
    }
}
