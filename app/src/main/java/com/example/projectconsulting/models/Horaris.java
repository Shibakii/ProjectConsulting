package com.example.projectconsulting.models;

import java.util.ArrayList;

public class Horaris {

    ArrayList< Object > grups_has_horaris = new ArrayList < Object > ();
    private float id;
    private String nom;

    public float getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}