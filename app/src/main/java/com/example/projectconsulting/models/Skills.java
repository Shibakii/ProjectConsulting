package com.example.projectconsulting.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Skills implements Serializable {

    private int notaMedia;
    ArrayList < Kpis > kpis = new ArrayList < Kpis > ();
    private String llistes_skills = null;
    private int id;
    private String nom;
    private int llistes_skills_id;
    private boolean actiu;

    public Skills(ArrayList<Kpis> kpis, String llistes_skills, int id, String nom, int llistes_skills_id, boolean actiu) {
        this.kpis = kpis;
        this.llistes_skills = llistes_skills;
        this.id = id;
        this.nom = nom;
        this.llistes_skills_id = llistes_skills_id;
        this.actiu = actiu;
    }

    public int getNotaMedia() {
        hacerMedia();
        return notaMedia;
    }

    public ArrayList<Kpis> getKpis() {
        return kpis;
    }

    public void setKpis(ArrayList<Kpis> kpis) {
        this.kpis = kpis;
    }

    public String getLlistes_skills() {
        return llistes_skills;
    }

    public void setLlistes_skills(String llistes_skills) {
        this.llistes_skills = llistes_skills;
    }

    public float getId() {
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

    public int getLlistes_skills_id() {
        return llistes_skills_id;
    }

    public void setLlistes_skills_id(int llistes_skills_id) {
        this.llistes_skills_id = llistes_skills_id;
    }

    public void hacerMedia(){
        int notaTotal = 0;
        List <Valoracions> valoracionMedia= new ArrayList<>();
        for(int i=0;i<kpis.size();i++){
            valoracionMedia = kpis.get(i).getValoracions();

        }
        for(int j=0;j<valoracionMedia.size();j++){
            notaTotal += valoracionMedia.get(j).getNota();
        }

        this.notaMedia  = notaTotal/valoracionMedia.size();


    }

    public void addKpiPorKpi(Kpis kpi){

        kpis.add(kpi);

    }



    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }



}
