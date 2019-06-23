package com.farmacia.databases.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.farmacia.databases.DAO.interfaces.IUsuarioDAO;
import com.farmacia.databases.provider.DatabaseContentProvider;
import com.farmacia.databases.schema.IUsuarioSchema;
import com.farmacia.models.Cidade;
import com.farmacia.models.Usuario;

import java.util.List;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public class UsuarioDAO extends DatabaseContentProvider implements IUsuarioSchema, IUsuarioDAO {

    private Cursor cursor;
    private ContentValues initialValues;

    private CidadeDAO cidadeDAO;


    public UsuarioDAO(SQLiteDatabase db, CidadeDAO cidadeDAO){
        super(db);
        this.cidadeDAO = cidadeDAO;
    }


    @Override
    public Usuario getUsuarioPorId(int usuarioId) {

        final String selectionArgs[] = { String.valueOf(usuarioId) };
        final String selection = COLUNA_ID + " = ?";
        Usuario mUsuario = new Usuario();
        cursor = super.query(USUARIO_TABLE, USUARIO_COLUNAS, selection,
                selectionArgs, COLUNA_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mUsuario = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return mUsuario;
    }

    @Override
    public List<Usuario> getTodosUsuario() {
        return null;
    }

    @Override
    public boolean adicionarUsuario(Usuario usuario) {
        setContentValue(usuario);
        try {
            return super.insert(USUARIO_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            /// column email is not unique (code 19)
            return false;
        }
    }



    @Override
    public Usuario fazerLogin(String login, String senha) {

        final String selectionArgs[] = { login,senha };

        final String selection = COLUNA_CPF + " = ? AND " + COLUNA_SENHA + " = ?";

        cursor = super.query(USUARIO_TABLE, USUARIO_COLUNAS, selection,
                selectionArgs, COLUNA_ID);

        try{
            if (cursor != null) {
                cursor.moveToFirst();
                Log.d("ERROR", cursor.toString());
                return cursorToEntity(cursor);
            }
            return null;
        }catch (CursorIndexOutOfBoundsException ex){
            Log.d("ERROR", ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteAllUsers() {
        return false;
    }

    @Override
    protected Usuario cursorToEntity(Cursor cursor) throws CursorIndexOutOfBoundsException {

        Usuario mUsuario = new Usuario();

        int idIndex;
        int userNameIndex;
        int idadeIndex;
        int cpfIndex;
        int emailIndex;
        int cidadeIndex;
        int dateIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUNA_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUNA_ID);
                mUsuario.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUNA_NOME) != -1) {
                userNameIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_NOME);
                mUsuario.setNome(cursor.getString(userNameIndex));
            }
            if (cursor.getColumnIndex(COLUNA_EMAIL) != -1) {
                emailIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_EMAIL);
                mUsuario.setEmail(cursor.getString(emailIndex));
            }
            if (cursor.getColumnIndex(COLUNA_CPF) != -1) {
                cpfIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_CPF);
                mUsuario.setCpf(cursor.getString(cpfIndex));
            }
            if (cursor.getColumnIndex(COLUNA_IDADE) != -1) {
                idadeIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_IDADE);
                mUsuario.setEmail(cursor.getString(idadeIndex));
            }
            if (cursor.getColumnIndex(COLUNA_ID_CIDADE) != -1) {
                cidadeIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_ID_CIDADE);
                mUsuario.setCidade(cidadeDAO.getCidadePorId(cursor.getInt(cidadeIndex)));
            }

        }
        return mUsuario;
    }


    private void setContentValue(Usuario usuario) {
        initialValues = new ContentValues();
        if(usuario.getId() > 0) {
            initialValues.put(COLUNA_ID, usuario.getId());
        }
        initialValues.put(COLUNA_NOME, usuario.getNome());
        initialValues.put(COLUNA_EMAIL, usuario.getEmail());
        initialValues.put(COLUNA_CPF, usuario.getCpf());
        initialValues.put(COLUNA_IDADE, usuario.getIdade());
        initialValues.put(COLUNA_SENHA, usuario.getSenha());
        initialValues.put(COLUNA_ID_CIDADE, usuario.getCidade().getId());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

}
