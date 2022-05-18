package com.example.projectconsulting.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cursos implements Serializable {
    List<Object> grups_has_alumnes;
    List<Object> grups_has_docents;
    List<Object> grups_has_llistes_skills;
    private int curs_inici;
    private int curs_fi;
    private boolean actiu;

    public Cursos(List<Object> grups_has_alumnes, List<Object> grups_has_docents, List<Object> grups_has_llistes_skills, int curs_inici, int curs_fi, boolean actiu) {
        this.grups_has_alumnes = grups_has_alumnes;
        this.grups_has_docents = grups_has_docents;
        this.grups_has_llistes_skills = grups_has_llistes_skills;
        this.curs_inici = curs_inici;
        this.curs_fi = curs_fi;
        this.actiu = actiu;
    }

    public List<Object> getGrups_has_alumnes() {
        return grups_has_alumnes;
    }

    public void setGrups_has_alumnes(ArrayList<Object> grups_has_alumnes) {
        this.grups_has_alumnes = grups_has_alumnes;
    }

    public List<Object> getGrups_has_docents() {
        return grups_has_docents;
    }

    public void setGrups_has_docents(ArrayList<Object> grups_has_docents) {
        this.grups_has_docents = grups_has_docents;
    }

    public List<Object> getGrups_has_llistes_skills() {
        return grups_has_llistes_skills;
    }

    public void setGrups_has_llistes_skills(ArrayList<Object> grups_has_llistes_skills) {
        this.grups_has_llistes_skills = grups_has_llistes_skills;
    }

    public int getCurs_inici() {
        return curs_inici;
    }

    public void setCurs_inici(int curs_inici) {
        this.curs_inici = curs_inici;
    }

    public int getCurs_fi() {
        return curs_fi;
    }

    public void setCurs_fi(int curs_fi) {
        this.curs_fi = curs_fi;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }
}

