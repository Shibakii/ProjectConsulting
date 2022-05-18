package com.example.projectconsulting.models;

public class Grups_has_horaris {

    private String dias = null;
    private String grups = null;
    private String horari = null;
    private float id_grups;
    private float id_horari;
    private float id_dias;

    public Grups_has_horaris(String dias, String grups, String horari, float id_grups, float id_horari, float id_dias) {
        this.dias = dias;
        this.grups = grups;
        this.horari = horari;
        this.id_grups = id_grups;
        this.id_horari = id_horari;
        this.id_dias = id_dias;
    }

    public String getDias() {
        return dias;
    }

    public String getGrups() {
        return grups;
    }

    public String getHorari() {
        return horari;
    }

    public float getId_grups() {
        return id_grups;
    }

    public float getId_horari() {
        return id_horari;
    }

    public float getId_dias() {
        return id_dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public void setGrups(String grups) {
        this.grups = grups;
    }

    public void setHorari(String horari) {
        this.horari = horari;
    }

    public void setId_grups(float id_grups) {
        this.id_grups = id_grups;
    }

    public void setId_horari(float id_horari) {
        this.id_horari = id_horari;
    }

    public void setId_dias(float id_dias) {
        this.id_dias = id_dias;
    }
}