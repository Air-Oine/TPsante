package com.example.admin.tpsante.database.datasource;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataSource<T extends Modele> {

    private final DBOpenHelper helper;
    private SQLiteDatabase db;
    private T modele;

    public DataSource(Context context, Class<T> clazz) throws Exception {
        this.modele = clazz.newInstance();
        helper = new DBOpenHelper(context, modele);
    }

    public void open() throws SQLException {
       if(db == null) {
           db = helper.getWritableDatabase();
       }
    }

    public void close() {
        helper.close();
    }

    public int insert(T modele) throws Exception {
        int id = (int) db.insert(modele.getTable(), null, modele.getContentValues());
        modele.setId(id);
        return id;
    }

    public int update(T modele) throws Exception {
        return db.update(modele.getTable(), modele.getContentValues(), "ID = " + modele.getId(), null);
    }

    public long delete(T modele) {
        return db.delete(modele.getTable(), "ID = " + modele.getId(), null);
    }

    public List<T> readAll() {

        // columns
        String[] allColumns = modele.getColumns();

        // select query
        Cursor cursor = db.query(modele.getTable(), allColumns, null, null, null, null, null);

        // iterate on cursor and retreive result
        List<T> modeles = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            List<String> valeurs = new ArrayList<>();
            for (int i = 1; i < allColumns.length; i++) {
                valeurs.add(cursor.getString(i));
            }
            modeles.add(
                    (T) modele.build(cursor.getInt(0), valeurs));
            cursor.moveToNext();
        }

        cursor.close();

        return modeles;
    }
}