package com.edwin.proyectoprueba.datos;

public class Constantes {

    public static final String DATA_BASE="TURISMO";
    public static final String NAME_TABLE="PRUEBA";
    public static final String CAMPO1="NOMBRE";
    public static final String CAMPO2="DESCRIPCION";
    public static final String CAMPO3="FOTO";
    public static final String CAMPO4="UBICACION";
    public static final int VERSION=1;

    public static final String CREATE_TABLE= " CREATE TABLE " + NAME_TABLE +
            "(" + CAMPO1 + " TEXT, " + CAMPO2 + " TEXT, " + CAMPO3 + " BLOB, " + CAMPO4 + " TEXT)";
}
