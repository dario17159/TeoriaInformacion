package com.dario.carrizo.teoriainformacion.Commons;

import androidx.annotation.NonNull;

public class PersonaFija {
    private char[] NombreyA;

    private char[] direccion;

    private char[] dni;

    private int datos;

    public PersonaFija(char[] NomyA, char[] Dire, char[] Doc, int Dat) {
        this.NombreyA = new char[30];
        for (int i = 0; (i < 30); i++) {
            if ((i < NomyA.length)) {
                this.NombreyA[i] = NomyA[i];
            }
            else {
                this.NombreyA[i] = ' ';
            }

        }

        this.direccion = new char[30];
        for (int i = 0; (i < 30); i++) {
            if ((i < Dire.length)) {
                this.direccion[i] = Dire[i];
            }
            else {
                this.direccion[i] = ' ';
            }

        }

        this.dni = new char[30];
        for (int i = 0; (i < 30); i++) {
            if ((i < Doc.length)) {
                this.dni[i] = Doc[i];
            }
            else {
                this.dni[i] = ' ';
            }

        }

        this.datos = Dat;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder RBuilder = new StringBuilder(new String(this.NombreyA));
        for (int i = 0; (i < 30); i++) {
            RBuilder.append(this.direccion[i]);
        }
        StringBuilder R = new StringBuilder(RBuilder.toString());

        for (int i = 0; (i < 30); i++) {
            R.append(this.dni[i]);
        }
        return (R
                + ((byte) this.datos + "/n"));
    }
}
