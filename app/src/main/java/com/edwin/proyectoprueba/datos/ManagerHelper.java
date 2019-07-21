package com.edwin.proyectoprueba.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ManagerHelper {

    SQLiteDatabase db;
    GestorHelper gh;
    Datos datos;

    public ManagerHelper(Context context){
        gh=new GestorHelper(context);
    }

    public void openDBWrite(){
        db=gh.getWritableDatabase();
    }

    public void openDBRead(){
        db=gh.getReadableDatabase();
    }


    public long insertarInformacion(Datos datos){
        openDBWrite();
        long valor;
        ContentValues contenedor=new ContentValues();
        contenedor.put(Constantes.CAMPO1,datos.getNombre());
        contenedor.put(Constantes.CAMPO2,datos.getDescripcion());
        contenedor.put(Constantes.CAMPO3,datos.getFoto());
        contenedor.put(Constantes.CAMPO4,datos.getUbicacion());


        valor=db.insert(Constantes.NAME_TABLE,null,contenedor);

        return valor;
    }

    public ArrayList<Datos> listar(){
        openDBWrite();

        ArrayList arrayList = new ArrayList<Datos>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constantes.NAME_TABLE,null);
        if (cursor.moveToFirst()) {
            do {

                Datos datos = new Datos();
                datos.setNombre(cursor.getString(0));
                datos.setDescripcion(cursor.getString(1));
                datos.setFoto(cursor.getBlob(2));
                datos.setUbicacion(cursor.getString(3));


                arrayList.add(datos);

            } while(cursor.moveToNext());
        }
        return arrayList;
    }

}
