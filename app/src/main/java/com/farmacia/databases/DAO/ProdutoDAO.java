package com.farmacia.databases.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.farmacia.databases.DAO.interfaces.ICidadeDAO;
import com.farmacia.databases.DAO.interfaces.IProdutoDAO;
import com.farmacia.databases.provider.DatabaseContentProvider;
import com.farmacia.databases.schema.ICidadeSchema;
import com.farmacia.databases.schema.IProdutoSchema;
import com.farmacia.models.Categoria;
import com.farmacia.models.Cidade;
import com.farmacia.models.Produto;
import com.farmacia.models.Usuario;

import java.util.ArrayList;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public class ProdutoDAO extends DatabaseContentProvider implements IProdutoSchema, IProdutoDAO {

    private Cursor cursor;
    private ContentValues initialValues;

    private CategoriaDAO categoriaDAO;




    public ProdutoDAO(SQLiteDatabase db, CategoriaDAO categoriaDAO) {

        super(db);
        this.categoriaDAO = categoriaDAO;
    }

    @Override
    public Produto getProdutoPorId(int produtoId) {
        final String selectionArgs[] = { String.valueOf(produtoId) };
        final String selection = COLUNA_ID + " = ?";
        Produto mProduto = new Produto();
        cursor = super.query(PRODUTO_TABLE, PRODUTO_COLUNAS, selection,
                selectionArgs, COLUNA_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mProduto = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return mProduto;
    }

    @Override
    public boolean adicionarProduto(Produto produto) {
        setContentValue(produto);
        try {
            return super.insert(PRODUTO_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            /// column email is not unique (code 19)
            return false;
        }
    }


    @Override
    public ArrayList<Produto> getTodosProdutos() {
        ArrayList<Produto> produtoLista = new ArrayList<>();
        cursor = super.query(PRODUTO_TABLE, PRODUTO_COLUNAS, null,
                null, COLUNA_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Produto item = cursorToEntity(cursor);
                produtoLista.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return produtoLista;
    }

    @Override
    protected Produto cursorToEntity(Cursor cursor) {
        Produto mProduto = new Produto();

        int idIndex;
        int nomeIndex;
        int pesoIndex;
        int imagemIndex;
        int categoriaIdIndex;


        if (cursor != null) {
            if (cursor.getColumnIndex(COLUNA_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUNA_ID);
                mProduto.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUNA_NOME) != -1) {
                nomeIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_NOME);
                mProduto.setDescricao(cursor.getString(nomeIndex));
            }

            if (cursor.getColumnIndex(COLUNA_PESO) != -1) {
                pesoIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_PESO);
                mProduto.setPeso(cursor.getString(pesoIndex));
            }
            if (cursor.getColumnIndex(COLUNA_IMAGEM) != -1) {
                imagemIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_IMAGEM);
                mProduto.setImage(cursor.getBlob(imagemIndex));
            }

            if (cursor.getColumnIndex(COLUNA_ID_CATEGORIA) != -1) {
                categoriaIdIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_ID_CATEGORIA);
                mProduto.setCategoria(categoriaDAO.getCategoriaPorId(cursor.getInt(categoriaIdIndex)));
            }

        }
        return mProduto;
    }

    private void setContentValue(Produto produto) {
        initialValues = new ContentValues();
        if (produto.getId() > 0) {
            initialValues.put(COLUNA_ID, produto.getId());
        }
        initialValues.put(COLUNA_NOME, produto.getId());
        initialValues.put(COLUNA_PESO, produto.getPeso());
        initialValues.put(COLUNA_IMAGEM, produto.getImage());
        initialValues.put(COLUNA_ID_CATEGORIA, produto.getCategoria().getId());

    }

    private ContentValues getContentValue() {
        return initialValues;
    }
}
