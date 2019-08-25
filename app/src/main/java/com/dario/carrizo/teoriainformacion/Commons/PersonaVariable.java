package com.dario.carrizo.teoriainformacion.Commons;

import androidx.annotation.NonNull;

public class PersonaVariable {

    private String NombreyA;

    private String direccion;

    private String dni;

    private int datos;

    public PersonaVariable(String NomyA, String Dire, String Doc, int Dat) {
        this.NombreyA = NomyA;
        this.direccion = Dire;
        this.dni = Doc;
        this.datos = Dat;
    }

    @NonNull
    @Override
    public String toString() {

        return (this.NombreyA + (" "
                + (this.direccion + (" "
                + (this.dni + (" "
                + ( (byte) this.datos + "/n")))))));
    }
}
