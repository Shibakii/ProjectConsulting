package com.example.projectconsulting.models;

import java.io.Serializable;

public class Botchats implements Serializable

{
    private int id;
    private int request;
    private int id_usuari;
    private String mensaje;
    private boolean actiu;

    public Botchats( int request, int id_usuari, String mensaje, boolean actiu) {
//        this.id = id;
        this.request = request;
        this.id_usuari = id_usuari;
        this.mensaje = mensaje;
        this.actiu = actiu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
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

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }
}
