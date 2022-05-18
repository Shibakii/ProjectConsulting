package com.example.projectconsulting.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grup implements Serializable
{
    ArrayList < Chatgrupos > chatgrupos = new ArrayList < Chatgrupos > ();
    List<Grups_has_alumnes> grups_has_alumnes;
    List <Grups_has_docents> grups_has_docents;
    ArrayList <Grups_has_llistes_skills> grups_has_llistes_skills = new ArrayList <Grups_has_llistes_skills> ();
    ArrayList < Missatges > missatges = new ArrayList < Missatges > ();
    private int id;
    private String nom;

    public Grup(ArrayList<Chatgrupos> chatgrupos, List<Grups_has_alumnes> grups_has_alumnes,
                List<Grups_has_docents> grups_has_docents,
                ArrayList<Grups_has_llistes_skills> grups_has_llistes_skills,
                ArrayList<Missatges> missatges, int id, String nom) {
        this.chatgrupos = chatgrupos;
        this.grups_has_alumnes = grups_has_alumnes;
        this.grups_has_docents = grups_has_docents;
        this.grups_has_llistes_skills = grups_has_llistes_skills;
        this.missatges = missatges;
        this.id = id;
        this.nom = nom;
    }

    public ArrayList<Chatgrupos> getChatgrupos() {
        return chatgrupos;
    }

    public void setChatgrupos(ArrayList<Chatgrupos> chatgrupos) {
        this.chatgrupos = chatgrupos;
    }

    public List<Grups_has_alumnes> getGrups_has_alumnes() {
        return grups_has_alumnes;
    }

    public void setGrups_has_alumnes(ArrayList<Grups_has_alumnes> grups_has_alumnes) {
        this.grups_has_alumnes = grups_has_alumnes;
    }

    public List<Grups_has_docents> getGrups_has_docents() {
        return grups_has_docents;
    }

    public void setGrups_has_docents(ArrayList<Grups_has_docents> grups_has_docents) {
        this.grups_has_docents = grups_has_docents;
    }

    public ArrayList<Grups_has_llistes_skills> getGrups_has_llistes_skills() {
        return grups_has_llistes_skills;
    }

    public void setGrups_has_llistes_skills(ArrayList<Grups_has_llistes_skills> grups_has_llistes_skills) {
        this.grups_has_llistes_skills = grups_has_llistes_skills;
    }

    public ArrayList<Missatges> getMissatges() {
        return missatges;
    }

    public void setMissatges(ArrayList<Missatges> missatges) {
        this.missatges = missatges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
