package com.example.projectconsulting.models;

public class Chatgrupos
{
    private int id_mensaje;
    private int  id_usuari;
    private String mensaje;
    private int fecha;
    private int id_grup;

    public Chatgrupos(int id_mensaje, int id_usuari, String mensaje, int fecha, int id_grup) {
        this.id_mensaje = id_mensaje;
        this.id_usuari = id_usuari;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.id_grup = id_grup;
    }

    public int getId_mensaje() {
        return id_mensaje;
    }

    public void setId_mensaje(int id_mensaje) {
        this.id_mensaje = id_mensaje;
    }

    public int getId_usuari() {
        return id_usuari;
    }

    public void setId_usuari(int id_usuari) {
        this.id_usuari = id_usuari;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public int getId_grup() {
        return id_grup;
    }

    public void setId_grup(int id_grup) {
        this.id_grup = id_grup;
    }
}
