package com.example.testing.Model;

public class User {

    String uidPerfil,correoPerfil,nombresPerfil;

    public User() {
    }

    public User(String uidPerfil, String correoPerfil, String nombresPerfil) {
        this.uidPerfil = uidPerfil;
        this.correoPerfil = correoPerfil;
        this.nombresPerfil = nombresPerfil;
    }

    public String getUidPerfil() {
        return uidPerfil;
    }

    public void setUidPerfil(String uidPerfil) {
        this.uidPerfil = uidPerfil;
    }

    public String getCorreoPerfil() {
        return correoPerfil;
    }

    public void setCorreoPerfil(String correoPerfil) {
        this.correoPerfil = correoPerfil;
    }

    public String getNombresPerfil() {
        return nombresPerfil;
    }

    public void setNombresPerfil(String nombresPerfil) {
        this.nombresPerfil = nombresPerfil;
    }
}
