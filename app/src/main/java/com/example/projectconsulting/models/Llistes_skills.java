package com.example.projectconsulting.models;

import java.util.ArrayList;
import java.util.List;

public class Llistes_skills
{
    List< Grups_has_llistes_skills > grups_has_llistes_skills ;
    List< Skills > skills ;
    private float id;
    private String nom;
    private boolean actiu;

    public Llistes_skills(ArrayList<Grups_has_llistes_skills> grups_has_llistes_skills, ArrayList<Skills> skills, float id, String nom, boolean actiu) {
        this.grups_has_llistes_skills = grups_has_llistes_skills;
        this.skills = skills;
        this.id = id;
        this.nom = nom;
        this.actiu = actiu;
    }

    public List<Grups_has_llistes_skills> getGrups_has_llistes_skills() {
        return grups_has_llistes_skills;
    }

    public void setGrups_has_llistes_skills(ArrayList<Grups_has_llistes_skills> grups_has_llistes_skills) {
        this.grups_has_llistes_skills = grups_has_llistes_skills;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skills> skills) {
        this.skills = skills;
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

    @Override
    public String toString() {
        return nom;
    }
}
