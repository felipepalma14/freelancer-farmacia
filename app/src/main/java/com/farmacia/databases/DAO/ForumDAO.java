package com.farmacia.databases.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.farmacia.databases.DAO.interfaces.IForumDAO;
import com.farmacia.databases.DAO.interfaces.IUsuarioDAO;
import com.farmacia.databases.provider.DatabaseContentProvider;
import com.farmacia.databases.schema.IForumSchema;
import com.farmacia.databases.schema.IUsuarioSchema;
import com.farmacia.models.Forum;
import com.farmacia.models.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public class ForumDAO extends DatabaseContentProvider implements IForumSchema, IForumDAO {

    private Cursor cursor;
    private ContentValues initialValues;

    private UsuarioDAO usuarioDAO;
    private FarmaciaDAO farmaciaDAO;


    public ForumDAO(SQLiteDatabase db, UsuarioDAO usuarioDAO, FarmaciaDAO farmaciaDAO){
        super(db);
        this.usuarioDAO = usuarioDAO;
        this.farmaciaDAO = farmaciaDAO;
    }


    @Override
    public Forum getForumPorId(int id) {

        final String selectionArgs[] = { String.valueOf(id) };
        final String selection = COLUNA_ID + " = ?";
        Forum mForum = new Forum();
        cursor = super.query(FORUM_TABLE, FORUM_COLUNAS, selection,
                selectionArgs, COLUNA_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mForum = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return mForum;
    }

    @Override
    public ArrayList<Forum> getTodosForuns() {

        ArrayList<Forum> lista = new ArrayList<>();
        cursor = super.query(FORUM_TABLE, FORUM_COLUNAS, null,
                null, COLUNA_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Forum item = cursorToEntity(cursor);
                lista.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return lista;
    }

    @Override
    public boolean adicionarForum(Forum forum) {
        setContentValue(forum);
        try {
            return super.insert(FORUM_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            /// column email is not unique (code 19)
            return false;
        }
    }




    @Override
    protected Forum cursorToEntity(Cursor cursor) throws CursorIndexOutOfBoundsException {

        Forum mForum = new Forum();

        int idIndex;
        int topicoIndex;
        int usuarioIndex;
        int farmaciaIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUNA_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUNA_ID);
                mForum.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUNA_TOPICO) != -1) {
                topicoIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_TOPICO);
                mForum.setTopico(cursor.getString(topicoIndex));
            }
            if (cursor.getColumnIndex(COLUNA_USUARIO_ID) != -1) {
                usuarioIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_USUARIO_ID);
                mForum.setUsuario(usuarioDAO.getUsuarioPorId(cursor.getInt(usuarioIndex)));
            }
            if (cursor.getColumnIndex(COLUNA_FARMACIA_ID) != -1) {
                farmaciaIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_FARMACIA_ID);
                mForum.setFarmacia(farmaciaDAO.getFarmaciaPorId(cursor.getInt(farmaciaIndex)));
            }

        }
        return mForum;
    }


    private void setContentValue(Forum forum) {
        initialValues = new ContentValues();
        if(forum.getId() > 0) {
            initialValues.put(COLUNA_ID, forum.getId());
        }
        initialValues.put(COLUNA_TOPICO, forum.getTopico());
        initialValues.put(COLUNA_USUARIO_ID, forum.getUsuario().getId());
        initialValues.put(COLUNA_FARMACIA_ID, forum.getFarmacia().getId());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

}
