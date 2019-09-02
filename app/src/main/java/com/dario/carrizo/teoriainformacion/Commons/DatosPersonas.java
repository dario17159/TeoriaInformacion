package com.dario.carrizo.teoriainformacion.Commons;

import android.os.Environment;

import com.dario.carrizo.teoriainformacion.Fragments.VariableFijoFragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DatosPersonas {

    private final String NOMBRE_ARCHIVO_FIJO = "fijos.dat";
    private final String NOMBRE_ARCHIVO_VARIABLE = "variable.dat";
    private final String NOMBRE_ARCHIVO_FIJO_GRANDE = "Bigfijos.dat";
    private final String NOMBRE_ARCHIVO_VARIABLE_GRANDE = "Bigvariable.dat";
    private final String NOMBRE_CARPETA = "ArchivosVariablesFijos";


    private String nombreYapellido;
    private String direccion;
    private String dni;
    private short[] bivaluado;

    public DatosPersonas() {
    }

    public DatosPersonas(String nombreYapellido, String direccion, String dni, short[] bivaluado) {
        this.nombreYapellido = nombreYapellido;
        this.direccion = direccion;
        this.dni = dni;
        this.bivaluado = bivaluado;
    }

    public String getNombreYapellido() {
        return nombreYapellido;
    }

    public void setNombreYapellido(String nombreYapellido) {
        this.nombreYapellido = nombreYapellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public short[] getBivaluado() {
        return bivaluado;
    }

    public void setBivaluado(short[] bivaluado) {
        this.bivaluado = bivaluado;
    }


    /*Methods*/
    public void CrearArchivoFijo() {

        try {
            File recordsDir;
            recordsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
            if (!recordsDir.exists()) {
                recordsDir.mkdirs();
            }

            File fixedfile = new File(recordsDir, NOMBRE_ARCHIVO_FIJO);

            //Si el archivo con ese nombre ya existe, se lo sobre -e escribe
            if (fixedfile.exists()) {
                System.out.println("Ya existe un archivo con ese nombre, se sobre-escribira el archivo");
                fixedfile.delete();
                fixedfile = new File(recordsDir, NOMBRE_ARCHIVO_FIJO);
            }

            //Creamos el archivo de long fija
            boolean success = fixedfile.createNewFile();
            if (success) {
                System.out.println("Archivo creado exitosamente, se procederá a la carga");
                BufferedWriter escritor = new BufferedWriter(new FileWriter(fixedfile.getAbsolutePath(), true));

                escritor.write(nombreYapellido + "\n");
                escritor.write(direccion + "\n");
                escritor.write(dni + "\n");
                // los valores individualesde arreglo se cargan como uno solo
                for (int i = 0; i < this.bivaluado.length; i++) {
                    escritor.write(String.valueOf((this.bivaluado[i])));
                }
                escritor.close();
                VariableFijoFragment.pathArchivoFijo = fixedfile.getAbsolutePath();
            } else {
                System.out.println("error");
            }
        } catch (Exception ex) {
            System.err.println("Ocurrio un error en CrearArchivoFijo " + ex.getMessage());
        }
    }

    public void crearArchivoFijo(DatosPersonas[] datos) {

        try {
            File recordsDir;
            recordsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
            if (!recordsDir.exists()) {
                recordsDir.mkdirs();
            }
            File fixedfile = new File(recordsDir, NOMBRE_ARCHIVO_FIJO_GRANDE);
            if (fixedfile.exists()) {
                System.out.println("Ya existe un archivo con ese nombre, se sobre-escribira el archivo");
                fixedfile.delete();
                fixedfile = new File(recordsDir, NOMBRE_ARCHIVO_FIJO_GRANDE);
            }

            //Creamos el archivo de long fija
            boolean success = fixedfile.createNewFile();
            if (success) {
                System.out.println("Archivo creado exitosamente, se procederá a la carga");
                BufferedWriter escritor = new BufferedWriter(new FileWriter(fixedfile.getAbsolutePath(), true));

                for (int j = 0; j < datos.length; j++) {
                    escritor.write(datos[j].getNombreYapellido() + "\n");
                    escritor.write(datos[j].getDireccion() + "\n");
                    escritor.write(datos[j].getDni() + "\n");
                    for (int i = 0; i < datos[j].getBivaluado().length; i++) {
                        escritor.write(String.valueOf((datos[j].getBivaluado()[i])));
                    }
                }
                escritor.close();
                VariableFijoFragment.pathArchivoFijoGrande = fixedfile.getAbsolutePath();
            } else {
                System.out.println("error");
            }
        } catch (Exception ex) {
            System.err.println("Ocurrio un error en CrearArchivoFijo " + ex.getMessage());
        }

    }

    public void CrearArchivoVariable() {

        try {
            File recordsDir;
            recordsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
            if (!recordsDir.exists()) {
                recordsDir.mkdirs();
            }

            File varFile = new File(recordsDir, NOMBRE_ARCHIVO_VARIABLE);

            //File varFile = new File("C:\\Users\\Daniel\\Documents\\PulpoHacker\\archivo de Tamaño Fijo y Variable\\Archivos de Salida\\variable.dat");
            if (varFile.exists()) {
                System.out.println("Ya existe un archivo con ese nombre, se sobre-escribirá el mismo");
                varFile.delete();
                varFile = new File(recordsDir, NOMBRE_ARCHIVO_VARIABLE);
                //varFile = new File("C:\\Users\\Daniel\\Documents\\PulpoHacker\\archivo de Tamaño Fijo y Variable\\Archivos de Salida\\variable.dat");

            }
            boolean success = varFile.createNewFile();

            //Creamos archivo de tamaño variable
            if (success) {
                System.out.println("Creacion exitosa de Archivo de longitud variable, se procede a la carga");
                BufferedWriter escritor = new BufferedWriter(new FileWriter(varFile.getAbsolutePath(), true));

                escritor.write(nombreYapellido + "\n");
                escritor.write(direccion + "\n");
                escritor.write(dni + "\n");
                //se llama al metodo reducir p grabar los valores bivaluados como un valor binario, que al grabarse se guguarda como un caracter ascii equivalente al valro binario
                escritor.write(reducir(this.bivaluado));
                escritor.close();
                VariableFijoFragment.pathArchivoVariable = varFile.getAbsolutePath();
            } else {
                System.out.println("error");
            }

        } catch (Exception ex) {
            System.err.println("Ocurrio un error en CrearArchivoVariable " + ex.getMessage());
        }
    }

    public void crearArchivoDeTamVariable(DatosPersonas[] datos) {
        try {
            File recordsDir;
            recordsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
            if (!recordsDir.exists()) {
                recordsDir.mkdirs();
            }

            File varFile = new File(recordsDir, NOMBRE_ARCHIVO_VARIABLE_GRANDE);
            if (varFile.exists()) {
                System.out.println("Ya existe un archivo con ese nombre, se sobre-escribirá el mismo");
                varFile.delete();
                varFile = new File(recordsDir, NOMBRE_ARCHIVO_VARIABLE_GRANDE);

            }
            boolean success = varFile.createNewFile();

            //Creamos archivo de tamaño variable
            if (success) {
                System.out.println("Creacion exitosa de Archivo de longitud variable, se procede a la carga");
                BufferedWriter escritor = new BufferedWriter(new FileWriter(varFile.getAbsolutePath(), true));

                for (int i = 0; i < datos.length; i++) {
                    escritor.write(datos[i].getNombreYapellido() + "\n");
                    escritor.write(datos[i].getDireccion() + "\n");
                    escritor.write(datos[i].getDni() + "\n");
                    escritor.write(reducir(datos[i].getBivaluado()));
                }
                escritor.close();
                VariableFijoFragment.pathArchivoVariableGrande = varFile.getAbsolutePath();
            } else {
                System.out.println("error");
            }

        } catch (Exception ex) {
            System.err.println("Ocurrio un error en CrearArchivoVariable " + ex.getMessage());
        }

    }

    private int reducir(short[] a) {
        int b = 0;
        double exponente = 7;
        double pos = Math.pow(2, exponente);
        for (int i = 0; i < 8; i++) {
            if (a[i] == 1) {
                b = b | (int) pos;
            }
            exponente = exponente - 1;
            pos = Math.pow(2, exponente);

        }
        //System.out.println("El valor de b es" + b);
        return b;
    }

}
