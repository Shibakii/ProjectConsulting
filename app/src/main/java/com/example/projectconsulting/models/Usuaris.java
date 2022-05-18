package com.example.projectconsulting.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuaris implements Serializable {

    ArrayList < Object > botchat = new ArrayList < Object > ();
    ArrayList < Object > chatgrupos = new ArrayList < Object > ();
    private String formacion = null;
    List< Grups_has_alumnes > grups_has_alumnes;
    List < Grups_has_docents > grups_has_docents;
    ArrayList < Object > imatge_usuari = new ArrayList < Object > ();
    ArrayList < Object > missatges = new ArrayList < Object > ();
    private String rols = null;
    ArrayList < Object > valoracions = new ArrayList < Object > ();
    ArrayList< Object > valoracions1 = new ArrayList < Object > ();








    private int id;
    private String nom;
    private int rols_id;
    private boolean actiu;
    private String correu;
    private String contrasenia;
    private int any_naixement;
    private String telefono;
    private String dni;
    private String direccion;
    private String id_formacio;
    private Bitmap imagen;


    public Usuaris(int id, String nom,
                   int rols_id,
                   boolean actiu,
                   String correu,
                   String contrasenia, int any_naixement, String telefono, String dni, String direccion, String id_formacio) {
        this.id = id;
        this.nom = nom;
        this.rols_id = rols_id;
        this.actiu = actiu;
        this.correu = correu;
        this.contrasenia = contrasenia;
        this.any_naixement = any_naixement;
        this.telefono = telefono;
        this.dni = dni;
        this.direccion = direccion;
        this.id_formacio = id_formacio;
    }

    public Bitmap getImagen()
    {
        return imagen;
    }

    public void setImagen(Bitmap imagen)
    {
        this.imagen = imagen;
    }

    public ArrayList<Object> getBotchat() {
        return botchat;
    }

    public void setBotchat(ArrayList<Object> botchat) {
        this.botchat = botchat;
    }

    public ArrayList<Object> getChatgrupos() {
        return chatgrupos;
    }

    public void setChatgrupos(ArrayList<Object> chatgrupos) {
        this.chatgrupos = chatgrupos;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    public List<Grups_has_alumnes> getGrups_has_alumnes() {
        return grups_has_alumnes;
    }

    public void setGrups_has_alumnes(List<Grups_has_alumnes> grups_has_alumnes) {
        this.grups_has_alumnes = grups_has_alumnes;
    }

    public List<Grups_has_docents> getGrups_has_docents() {
        return grups_has_docents;
    }

    public void setGrups_has_docents(List<Grups_has_docents> grups_has_docents) {
        this.grups_has_docents = grups_has_docents;
    }

    public ArrayList<Object> getImatge_usuari() {
        return imatge_usuari;
    }

    public void setImatge_usuari(ArrayList<Object> imatge_usuari) {
        this.imatge_usuari = imatge_usuari;
    }

    public ArrayList<Object> getMissatges() {
        return missatges;
    }

    public void setMissatges(ArrayList<Object> missatges) {
        this.missatges = missatges;
    }

    public String getRols() {
        return rols;
    }

    public void setRols(String rols) {
        this.rols = rols;
    }

    public ArrayList<Object> getValoracions() {
        return valoracions;
    }

    public void setValoracions(ArrayList<Object> valoracions) {
        this.valoracions = valoracions;
    }

    public ArrayList<Object> getValoracions1() {
        return valoracions1;
    }

    public void setValoracions1(ArrayList<Object> valoracions1) {
        this.valoracions1 = valoracions1;
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

    public int getRols_id() {
        return rols_id;
    }

    public void setRols_id(int rols_id) {
        this.rols_id = rols_id;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getAny_naixement() {
        return any_naixement;
    }

    public void setAny_naixement(int any_naixement) {
        this.any_naixement = any_naixement;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getId_formacio() {
        return id_formacio;
    }

    public void setId_formacio(String id_formacio) {
        this.id_formacio = id_formacio;
    }
}
