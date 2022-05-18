package com.example.projectconsulting.models;

public class Dias {
    int id;
    String dia = "";
    String inici = "";
    String fi = "";
    String tasca = "";

    public Dias(int id, String dia, String inici, String fi, String tasca) {
        this.id = id;
        this.dia = dia;
        this.inici = inici;
        this.fi = fi;
        this.tasca = tasca;
    }

    public Dias() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDia() {
        return dia;
    }
    public void setDia(String dia) {
        this.dia = dia;
    }
    public String getInici() {
        return inici;
    }
    public void setInici(String inici) {
        this.inici = inici;
    }
    public String getFi() {
        return fi;
    }
    public void setFi(String fi) {
        this.fi = fi;
    }
    public String getTasca() {
        return tasca;
    }
    public void setTasca(String tasca) {
        this.tasca = tasca;
    }
}