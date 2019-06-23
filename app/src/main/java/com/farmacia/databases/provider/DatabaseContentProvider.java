package com.farmacia.databases.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Felipe Palma on 22/06/2019.
 */
public abstract class DatabaseContentProvider {

    public SQLiteDatabase mSqLiteDatabase;

    protected abstract <T> T cursorToEntity(Cursor cursor);


    public DatabaseContentProvider(SQLiteDatabase db) {
        this.mSqLiteDatabase = db;
    }

    public int delete(String tableName, String selection,
                      String[] selectionArgs) {
        return mSqLiteDatabase.delete(tableName, selection, selectionArgs);
    }

    public long insert(String tableName, ContentValues values) {
        return mSqLiteDatabase.insert(tableName, null, values);
    }


    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs,
                        String sortOrder) {

        final Cursor cursor = mSqLiteDatabase.query(tableName, columns,
                selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    public Cursor query(String tableName, String[] columns,String selection, String[] selectionArgs,
                        String sortOrder,String limit) {

        return mSqLiteDatabase.query(tableName, columns, selection,
                selectionArgs, null, null, sortOrder, limit);
    }

    public Cursor query(String tableName, String[] columns,String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy, String limit) {

        return mSqLiteDatabase.query(tableName, columns, selection,
                selectionArgs, groupBy, having, orderBy, limit);
    }

    public int update(String tableName, ContentValues values,String selection, String[] selectionArgs) {
        return mSqLiteDatabase.update(tableName, values, selection,
                selectionArgs);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return mSqLiteDatabase.rawQuery(sql, selectionArgs);
    }
}
