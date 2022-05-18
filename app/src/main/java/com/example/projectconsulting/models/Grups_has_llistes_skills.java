package com.example.projectconsulting.models;

import java.io.Serializable;

public class Grups_has_llistes_skills implements Serializable
{

    private int grups_id;
    private int llistes_skills_id;
    private int curs_id;

    public Grups_has_llistes_skills(int grups_id, int llistes_skills_id, int curs_id) {
        this.grups_id = grups_id;
        this.llistes_skills_id = llistes_skills_id;
        this.curs_id = curs_id;
    }

    public int getGrups_id() {
        return grups_id;
    }

    public void setGrups_id(int grups_id) {
        this.grups_id = grups_id;
    }

    public int getLlistes_skills_id() {
        return llistes_skills_id;
    }

    public void setLlistes_skills_id(int llistes_skills_id) {
        this.llistes_skills_id = llistes_skills_id;
    }

    public int getCurs_id() {
        return curs_id;
    }

    public void setCurs_id(int curs_id) {
        this.curs_id = curs_id;
    }


}
