package com.example.projectconsulting.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Valoracions implements Serializable {
    private Kpis kpis = null;
    private Usuaris usuaris = null;
    private int kpis_id;
    private int usuari_valorat_id;
    private int usuari_pp_id;
    private String data;
    private int nota;
    private String observacions = null;
    boolean esPuntuado;

    public Valoracions(Kpis kpis, Usuaris usuaris, int kpis_id, int usuari_valorat_id, int usuari_pp_id, String data, int nota, String observacions) {
        this.kpis = kpis;
        this.usuaris = usuaris;
        this.kpis_id = kpis_id;
        this.usuari_valorat_id = usuari_valorat_id;
        this.usuari_pp_id = usuari_pp_id;
        this.data = data;
        this.nota = nota;
        this.observacions = observacions;
    }
// Getter Methods


    public boolean isEsPuntuado() {
        return esPuntuado;
    }

    public void setEsPuntuado(boolean esPuntuado) {
        this.esPuntuado = esPuntuado;
    }

    public Kpis getKpis() {
        return kpis;
    }

    public Usuaris getUsuaris() {
        return usuaris;
    }

    public int getKpis_id() {
        return kpis_id;
    }

    public int getUsuari_valorat_id() {
        return usuari_valorat_id;
    }

    public int getUsuari_pp_id() {
        return usuari_pp_id;
    }

    public String getData() {
        return data;
    }

    public int getNota() {
        return nota;
    }

    public String getObservacions() {
        return observacions;
    }

    // Setter Methods

    public void setKpis(Kpis kpis) {
        this.kpis = kpis;
    }

    public void setUsuaris(Usuaris usuaris) {
        this.usuaris = usuaris;
    }

    public void setKpis_id(int kpis_id) {
        this.kpis_id = kpis_id;
    }

    public void setUsuari_valorat_id(int usuari_valorat_id) {
        this.usuari_valorat_id = usuari_valorat_id;
    }

    public void setUsuari_pp_id(int usuari_pp_id) {
        this.usuari_pp_id = usuari_pp_id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }
}