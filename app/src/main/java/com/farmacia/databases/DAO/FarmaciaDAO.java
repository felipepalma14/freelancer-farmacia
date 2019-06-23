package com.farmacia.databases.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.farmacia.databases.DAO.CidadeDAO;
import com.farmacia.databases.DAO.interfaces.IFarmaciaDAO;
import com.farmacia.databases.provider.DatabaseContentProvider;
import com.farmacia.databases.schema.IFarmaciaSchema;
import com.farmacia.databases.schema.IUsuarioSchema;
import com.farmacia.models.Farmacia;
import com.farmacia.models.Produto;
import com.farmacia.models.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public class FarmaciaDAO extends DatabaseContentProvider implements IFarmaciaSchema, IFarmaciaDAO {

    private Cursor cursor;
    private ContentValues initialValues;

    private CidadeDAO cidadeDAO;



    public FarmaciaDAO(SQLiteDatabase db, CidadeDAO cidadeDAO){
        super(db);
        this.cidadeDAO = cidadeDAO;
    }


    @Override
    public Farmacia getFarmaciaPorId(int id) {

        final String selectionArgs[] = { String.valueOf(id) };
        final String selection = COLUNA_ID + " = ?";
        Farmacia mFarmacia = new Farmacia();
        cursor = super.query(FARMACIA_TABLE, FARMACIA_COLUNAS, selection,
                selectionArgs, COLUNA_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mFarmacia = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return mFarmacia;
    }

    @Override
    public List<Farmacia> getTodasFarmacias() {
        ArrayList<Farmacia> farmaciaLista = new ArrayList<>();
        cursor = super.query(FARMACIA_TABLE, FARMACIA_COLUNAS, null,
                null, COLUNA_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Farmacia item = cursorToEntity(cursor);
                farmaciaLista.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return farmaciaLista;
    }

    @Override
    public boolean adicionarFarmacia(Farmacia farmacia) {
        setContentValue(farmacia);
        try {
            return super.insert(FARMACIA_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            /// column email is not unique (code 19)
            return false;
        }
    }



    @Override
    protected Farmacia cursorToEntity(Cursor cursor) throws CursorIndexOutOfBoundsException {

        Farmacia mFarmacia = new Farmacia();

        int idIndex;
        int enderecoIndex;
        int nomeIndex;
        int cepIndex;
        int telefoneIndex;
        int cnpjIndex;
        int longitudeIndex;
        int latitudeIndex;
        int cidadeIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUNA_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUNA_ID);
                mFarmacia.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUNA_ENDERECO) != -1) {
                enderecoIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_ENDERECO);
                mFarmacia.setEndereco(cursor.getString(enderecoIndex));
            }
            if (cursor.getColumnIndex(COLUNA_NOME) != -1) {
                nomeIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_NOME);
                mFarmacia.setNome(cursor.getString(nomeIndex));
            }
            if (cursor.getColumnIndex(COLUNA_CEP) != -1) {
                cepIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_CEP);
                mFarmacia.setCep(cursor.getString(cepIndex));
            }
            if (cursor.getColumnIndex(COLUNA_TELEFONE) != -1) {
                telefoneIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_TELEFONE);
                mFarmacia.setTelefone(cursor.getString(telefoneIndex));
            }
            if (cursor.getColumnIndex(COLUNA_CNPJ) != -1) {
                cnpjIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_CNPJ);
                mFarmacia.setCpnj(cursor.getString(cnpjIndex));
            }
            if (cursor.getColumnIndex(COLUNA_TELEFONE) != -1) {
                telefoneIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_TELEFONE);
                mFarmacia.setTelefone(cursor.getString(telefoneIndex));
            }
            /*
            if (cursor.getColumnIndex(COLUNA_LATITUDE) != -1) {
                latitudeIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_LATITUDE);
                mFarmacia.setGeoLocalizacao(cursor.getString(telefoneIndex));
            }
            if (cursor.getColumnIndex(COLUNA_LONGITUDE) != -1) {
                longitudeIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_LONGITUDE);
                mFarmacia.setGeoLocalizacao(cursor.getString(telefoneIndex));
            }
             */

            if (cursor.getColumnIndex(COLUNA_ID_CIDADE) != -1) {
                cidadeIndex = cursor.getColumnIndexOrThrow(
                        COLUNA_ID_CIDADE);
                mFarmacia.setCidade(cidadeDAO.getCidadePorId(cursor.getInt(cidadeIndex)));
            }

        }
        return mFarmacia;
    }


    private void setContentValue(Farmacia farmacia) {
        initialValues = new ContentValues();
        if(farmacia.getId() > 0) {
            initialValues.put(COLUNA_ID, farmacia.getId());
        }
        initialValues.put(COLUNA_NOME, farmacia.getNome());
        initialValues.put(COLUNA_ENDERECO, farmacia.getEndereco());
        initialValues.put(COLUNA_CNPJ, farmacia.getCpnj());
        initialValues.put(COLUNA_CEP, farmacia.getCep());
        initialValues.put(COLUNA_TELEFONE, farmacia.getTelefone());
        initialValues.put(COLUNA_ID_CIDADE, farmacia.getCidade().getId());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

}
