package com.example.projectconsulting.models;

import java.util.ArrayList;

public class Formacion
{
    ArrayList < Object > usuaris = new ArrayList < Object > ();
    private float id;
    private String nom;

    public Formacion(ArrayList<Object> usuaris, float id, String nom) {
        this.usuaris = usuaris;
        this.id = id;
        this.nom = nom;
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
}
