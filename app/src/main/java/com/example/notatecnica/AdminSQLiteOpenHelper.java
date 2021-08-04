package com.example.notatecnica;

import android.content.Context;
import android. database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{
    /**
     * Esta función es el constructor, y nos permite crear un objeto que funcionarán como administrador.
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    /**
     * Permite crear la tabla que contendrá todos los registros de los estudiantes.
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table estudiantes(id_estudiante int primary key, "+"nombres text, apellidos text, edad int, id_carrera text)");
    }
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
    }
}
