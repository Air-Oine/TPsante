package com.example.admin.tpsante.database.datasource;

import android.content.ContentValues;

import com.example.admin.tpsante.database.datasource.e.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class Modele<T extends Modele> {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*
     * Méthodes techniques
     */

    public String getDataBase() {
        return getClass().getAnnotation(DataBase.class).value();
    }

    public String getTable() {
        return getClass().getAnnotation(Table.class).value();
    }

    public ContentValues getContentValues() throws Exception {

        ContentValues contentValues = new ContentValues();

        for (Method method : getClass().getMethods()) {
            if (method.isAnnotationPresent(Columne.class))
                contentValues.put(method.getAnnotation(Columne.class).value(), (String) method.invoke(this));
        }

        return contentValues;

    }

    public String[] getColumns() {

        List<String> liste = new ArrayList<>();

        liste.add("ID");

        for (Method method : getClass().getMethods()) {
            if (method.isAnnotationPresent(Columne.class)) {
                liste.add(method.getAnnotation(Columne.class).value());
            }
        }

        return liste.toArray(new String[liste.size()]);

    }

     /*
     * Méthodes à implémeter
     */

    public abstract String getCreateDataBase();

    public abstract T build(int id, List<String> valeurs);

    /*
     * Annotation
     */

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface DataBase {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Table {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Columne {
        String value();
        Type type();
    }


}
