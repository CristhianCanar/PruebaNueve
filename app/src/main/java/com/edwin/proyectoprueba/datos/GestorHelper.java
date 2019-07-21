package com.edwin.proyectoprueba.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GestorHelper extends SQLiteOpenHelper {
    public GestorHelper(Context context) {
        super(context, Constantes.DATA_BASE, null, Constantes.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + Constantes.NAME_TABLE);
        onCreate(db);
    }
}
