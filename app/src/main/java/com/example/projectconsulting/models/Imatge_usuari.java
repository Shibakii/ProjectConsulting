package com.example.projectconsulting.models;

import android.graphics.Bitmap;
import android.media.Image;

public class Imatge_usuari
{
    private int id;
    private int id_usuari;
    private Bitmap imatge;

    public Imatge_usuari(int id, int id_usuari, Bitmap imatge) {
        this.id = id;
        this.id_usuari = id_usuari;
        this.imatge = imatge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuari() {
        return id_usuari;
    }

    public void setId_usuari(int id_usuari) {
        this.id_usuari = id_usuari;
    }

    public Bitmap getImatge() {
        return imatge;
    }

    public void setImatge(Bitmap imatge) {
        this.imatge = imatge;
    }
}
