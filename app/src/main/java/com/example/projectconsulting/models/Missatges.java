package com.example.projectconsulting.models;

import java.util.Date;

public class Missatges
{
    private String grups = null;
    private String usuaris = null;
    private float id;
    private String asumpte;
    private String cos;
    private float id_usuari;
    private float id_grup;
    private String data;

    public Missatges(String grups, String usuaris, float id, String asumpte, String cos, float id_usuari, float id_grup, String data) {
        this.grups = grups;
        this.usuaris = usuaris;
        this.id = id;
        this.asumpte = asumpte;
        this.cos = cos;
        this.id_usuari = id_usuari;
        this.id_grup = id_grup;
        this.data = data;
    }

    public String getGrups() {
        return grups;
    }

    public void setGrups(String grups) {
        this.grups = grups;
    }

    public String getUsuaris() {
        return usuaris;
    }

    public void setUsuaris(String usuaris) {
        this.usuaris = usuaris;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getAsumpte() {
        return asumpte;
    }

    public void setAsumpte(String asumpte) {
        this.asumpte = asumpte;
    }

    public String getCos() {
        return cos;
    }

    public void setCos(String cos) {
        this.cos = cos;
    }

    public float getId_usuari() {
        return id_usuari;
    }

    public void setId_usuari(float id_usuari) {
        this.id_usuari = id_usuari;
    }

    public float getId_grup() {
        return id_grup;
    }

    public void setId_grup(float id_grup) {
        this.id_grup = id_grup;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
