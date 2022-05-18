package com.example.projectconsulting.models;

import java.util.ArrayList;
import java.util.List;

public class Kpis
{
    private String skills = null;
    List< Valoracions > valoracions ;
    private int id;
    private String nom;
    private int skills_id;
    private boolean actiu;

    public Kpis(String skills, List<Valoracions> valoracions, int id, String nom, int skills_id, boolean actiu) {
        this.skills = skills;
        this.valoracions = valoracions;
        this.id = id;
        this.nom = nom;
        this.skills_id = skills_id;
        this.actiu = actiu;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public List<Valoracions> getValoracions() {
        return valoracions;
    }

    public void setValoracions(List<Valoracions> valoracions) {
        this.valoracions = valoracions;
    }

    public void setValoracionPorValoracion(Valoracions valoracion){
        valoracions.add(valoracion);
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

    public float getSkills_id() {
        return skills_id;
    }

    public void setSkills_id(int skills_id) {
        this.skills_id = skills_id;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }
}
