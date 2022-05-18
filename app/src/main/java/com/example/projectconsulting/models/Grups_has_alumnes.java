package com.example.projectconsulting.models;

import java.io.Serializable;

public class Grups_has_alumnes implements Serializable
{
    private int grups_id;
    private int usuaris_id;
    private int curs_id;

    public Grups_has_alumnes(int grups_id, int usuaris_id, int curs_id) {
        this.grups_id = grups_id;
        this.usuaris_id = usuaris_id;
        this.curs_id = curs_id;
    }

    public int getGrups_id() {
        return grups_id;
    }

    public void setGrups_id(int grups_id) {
        this.grups_id = grups_id;
    }

    public int getUsuaris_id() {
        return usuaris_id;
    }

    public void setUsuaris_id(int usuaris_id) {
        this.usuaris_id = usuaris_id;
    }

    public int getCurs_id() {
        return curs_id;
    }

    public void setCurs_id(int curs_id) {
        this.curs_id = curs_id;
    }
}
