package com.farmacia.databases.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.farmacia.databases.DAO.interfaces.ICategoriaDAO;
import com.farmacia.databases.DAO.interfaces.ICidadeDAO;
import com.farmacia.databases.provider.DatabaseContentProvider;
import com.farmacia.databases.schema.ICategoriaSchema;
import com.farmacia.databases.schema.ICidadeSchema;
import com.farmacia.models.Categoria;
import com.farmacia.models.Cidade;

import java.util.ArrayList;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public class CategoriaDAO extends DatabaseContentProvider implements ICategoriaSchema, ICategoriaDAO {

    private Cursor cursor;
    private ContentValues initialValues;


    public CategoriaDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public Categoria getCategoriaPorId(int categoriaId) {
        final String selectionArgs[] = { String.valueOf(categoriaId) };
        final String selection = COLUNA_ID + " = ?";
        Categoria mCategoria = new Categoria();
        cursor = super.query(CATEGORIA_TABLE, CATEGORIA_COLUNAS, selection,
                selectionArgs, COLUNA_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mCategoria = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return mCategoria;
    }

    @Override
    public ArrayList<Categoria> getTodasCategorias() {
        ArrayList<Categoria> categoriaLista = new ArrayList<>();
        cursor = super.query(CATEGORIA_TABLE, CATEGORIA_COLUNAS, null,
                null, COLUNA_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Categoria item = cursorToEntity(cursor);
                categoriaLista.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return categoriaLista;
    }

    @Override
    protected Categoria cursorToEntity(Cursor cursor) {
        Categoria mCategoria = new Categoria();

        int idIndex;
        int categoriaNomeIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUNA_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUNA_ID);
                mCategoria.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUNA_DESCRICAO) != -1) {
                categoriaNomeIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_DESCRICAO);
                mCategoria.setDescricao(cursor.getString(categoriaNomeIndex));
            }



        }
        return mCategoria;
    }

    private void setContentValue(Categoria categoria) {
        initialValues = new ContentValues();
        if (categoria.getId() > 0) {
            initialValues.put(COLUNA_ID, categoria.getId());
        }
        initialValues.put(COLUNA_DESCRICAO, categoria.getDescricao());

    }

    private ContentValues getContentValue() {
        return initialValues;
    }
}
