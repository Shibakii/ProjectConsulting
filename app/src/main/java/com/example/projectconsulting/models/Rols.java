package com.example.projectconsulting.models;

import java.util.ArrayList;

public class Rols {

    ArrayList< Object > usuaris = new ArrayList < Object > ();
    private float id;
    private String nom;
    private boolean actiu;

    public Rols(ArrayList<Object> usuaris, float id, String nom, boolean actiu) {
        this.usuaris = usuaris;
        this.id = id;
        this.nom = nom;
        this.actiu = actiu;
    }

    public ArrayList<Object> getUsuaris() {
        return usuaris;
    }

    public void setUsuaris(ArrayList<Object> usuaris) {
        this.usuaris = usuaris;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }
}
