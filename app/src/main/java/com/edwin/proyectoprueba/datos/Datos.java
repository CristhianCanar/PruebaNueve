package com.edwin.proyectoprueba.datos;

public class Datos {
    private String nombre;
    private String descripcion;
    private byte[] foto;
    private String ubicacion;

    public Datos() {
    }

    public Datos(String nombre, String descripcion, byte[] foto, String ubicacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
