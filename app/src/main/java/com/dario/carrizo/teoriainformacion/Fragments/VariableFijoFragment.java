package com.dario.carrizo.teoriainformacion.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dario.carrizo.teoriainformacion.Commons.DatosPersonas;
import com.dario.carrizo.teoriainformacion.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class VariableFijoFragment extends Fragment {

    public static String pathArchivoFijo = "";
    public static String pathArchivoVariable = "";
    public static String pathArchivoFijoGrande = "";
    public static String pathArchivoVariableGrande = "";

    short respuestas[] = new short[8];

    private final short SI = 1;
    private final short NO = 0;

    private DatosPersonas datosPersonas;

    private TextInputEditText etNombreApellido;
    private TextInputEditText etDireccion;
    private TextInputEditText etDNI;

    private RadioButton rbtnPrimariosSi;
    private RadioButton rbtnSecundariosSi;
    private RadioButton rbtnUiversitariosSi;
    private RadioButton rbtnViviendaSi;
    private RadioButton rbtnObraSocialSi;
    private RadioButton rbtnTrabajaSi;
    private RadioButton rbtnVoluntarioSi;
    private RadioButton rbtnHijosSi;

    private Button btnGuardar;
    private Button btnGenerar20;
    private Button btnVerFijo;
    private Button btnVerVariable;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    private DatosPersonas[] multitud = {
            new DatosPersonas("Daniel Tel-Aviv ", "Campana", "45478123", (new short[]{1, 0, 0, 0, 0, 0, 0, 0})),
            new DatosPersonas("Dario Francescoli  ", "La Plata (Capital)", "43199123", (new short[]{0, 1, 0, 0, 0, 0, 0, 0})),
            new DatosPersonas("Ryan Rabun  ", "San Vicente", "43991523", (new short[]{0, 0, 1, 0, 0, 0, 0, 0})),
            new DatosPersonas("Agatha Alsop  ", "San Nicolás", "43989123", (new short[]{0, 0, 0, 1, 0, 0, 0, 0})),
            new DatosPersonas("Yael Yardley  ", "Luján", "43989123", (new short[]{0, 0, 0, 0, 1, 0, 0, 0})),
            new DatosPersonas("Milo Minier  ", "Presidente Perón", "43197823", (new short[]{0, 0, 0, 0, 0, 1, 0, 0})),
            new DatosPersonas("Marcela Mccain  ", "Cañuelas", "43197823", (new short[]{0, 0, 0, 0, 0, 0, 1, 0})),
            new DatosPersonas("Gordon Gooden", "Cordoba", "45475323", (new short[]{1, 0, 0, 0, 1, 1, 1, 1})),
            new DatosPersonas("Lydia Litherland  ", "San Pedro", "43197823", (new short[]{0, 0, 0, 0, 0, 0, 0, 1})),
            new DatosPersonas("Brigid Barris  ", "Pergamino", "43197823", (new short[]{0, 0, 0, 0, 0, 0, 0, 0})),
            new DatosPersonas("Leticia Lever  ", "Berisso", "43197823", (new short[]{1, 1, 1, 1, 1, 1, 1, 1})),
            new DatosPersonas("Pamela Probert  ", "Junín", "43197823", (new short[]{1, 1, 1, 1, 1, 1, 1, 0})),
            new DatosPersonas("Madaline Mckellar  ", "Tres Arroyos", "43197823", (new short[]{1, 1, 1, 0, 1, 1, 1, 0})),
            new DatosPersonas("Isaura Israel  ", "La Costa", "43197823", (new short[]{1, 1, 1, 1, 0, 0, 1, 0})),
            new DatosPersonas("Granville Gallman  ", "Bahía Blanca", "43197823", (new short[]{1, 1, 1, 1, 1, 1, 0, 0})),
            new DatosPersonas("Winnie Wortham  ", "Ensenada", "43177823", (new short[]{1, 1, 1, 1, 1, 0, 0, 0})),
            new DatosPersonas("Cherryl Cates  ", "Olavarría", "43177823", (new short[]{1, 1, 1, 1, 0, 0, 0, 0})),
            new DatosPersonas("Weldon Wendorf  ", "Marcos Paz", "43177823", (new short[]{1, 1, 1, 0, 0, 0, 0, 0})),
            new DatosPersonas("Kathleen Keele  ", "Chivilcoy", "43177823", (new short[]{1, 1, 0, 0, 0, 0, 0, 0})),
            new DatosPersonas("Roy Reardon  ", "General Rodríguez", "434789123", (new short[]{1, 0, 0, 0, 0, 0, 0, 1})),

    };

    public VariableFijoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_variable_fijo, container, false);
        linkVariables(rootView);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStoragePermissionGranted()) {
                    String nombreApellido = etNombreApellido.getText().toString().trim();
                    String direccion = etDireccion.getText().toString().trim();
                    String dni = etDNI.getText().toString().trim();
                    if (nombreApellido.isEmpty()) {
                        etNombreApellido.setError("Este campo es requerido");
                        etNombreApellido.requestFocus();
                    } else if (direccion.isEmpty()) {
                        etDireccion.setError("Este campo es requerido");
                        etDireccion.requestFocus();
                    } else if (dni.isEmpty()) {
                        etDNI.setError("Este campo es requerido");
                        etDNI.requestFocus();
                    } else {
                        datosPersonas.setNombreYapellido(nombreApellido);
                        datosPersonas.setDireccion(direccion);
                        datosPersonas.setDni(dni);
                        cargarBivaluados();
                        datosPersonas.CrearArchivoFijo();
                        datosPersonas.CrearArchivoVariable();
                        Toast.makeText(getActivity(), "Archivos Generados con Exito, almacenados en la carpeta Alarmas", Toast.LENGTH_LONG).show();
                        etNombreApellido.setText("");
                        etDNI.setText("");
                        etDireccion.setText("");
                    }
                }
            }
        });
        btnGenerar20.setOnClickListener(view -> {
            datosPersonas.crearArchivoFijo(multitud);
            datosPersonas.crearArchivoDeTamVariable(multitud);
            showBigAletDialog(R.layout.dialog_show_big_files);
        });

        btnVerFijo.setOnClickListener(view -> {
            if (!pathArchivoFijo.isEmpty()) {
                showAlertDialog(R.layout.dialog_show_files_saved, true);
            } else {
                Toast.makeText(getActivity(), "Primero debe generar los archivos", Toast.LENGTH_SHORT).show();
            }
        });
        btnVerVariable.setOnClickListener(view -> {
            if (!pathArchivoVariable.isEmpty()) {
                showAlertDialog(R.layout.dialog_show_files_saved, false);
            } else {
                Toast.makeText(getActivity(), "Primero debe generar los archivos", Toast.LENGTH_SHORT).show();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private void showBigAletDialog(int layout) {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = li.inflate(layout, null);
        TextView tvPesoFijo = layoutView.findViewById(R.id.tvPesoFijo);
        TextView tvPesoVariable = layoutView.findViewById(R.id.tvPesoVariable);
        TextView tvDiferencia = layoutView.findViewById(R.id.tvDiferencia);
        Button btnAceptar = layoutView.findViewById(R.id.btnAceptar20);
        difTamaños(tvPesoFijo,
                tvPesoVariable,
                tvDiferencia
        );
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        btnAceptar.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("PERMISSION", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    private void cargarBivaluados() {
        if (rbtnPrimariosSi.isChecked()) {
            respuestas[0] = SI;
        } else {
            respuestas[0] = NO;
        }
        if (rbtnSecundariosSi.isChecked()) {
            respuestas[1] = SI;
        } else {
            respuestas[1] = NO;
        }
        if (rbtnUiversitariosSi.isChecked()) {
            respuestas[2] = SI;
        } else {
            respuestas[2] = NO;
        }
        if (rbtnViviendaSi.isChecked()) {
            respuestas[3] = SI;
        } else {
            respuestas[3] = NO;
        }
        if (rbtnObraSocialSi.isChecked()) {
            respuestas[4] = SI;
        } else {
            respuestas[4] = NO;
        }
        if (rbtnTrabajaSi.isChecked()) {
            respuestas[5] = SI;
        } else {
            respuestas[5] = NO;
        }
        if (rbtnVoluntarioSi.isChecked()) {
            respuestas[6] = SI;
        } else {
            respuestas[6] = NO;
        }
        if (rbtnHijosSi.isChecked()) {
            respuestas[7] = SI;
        } else {
            respuestas[7] = NO;
        }

        datosPersonas.setBivaluado(respuestas);
    }

    private void linkVariables(View rootView) {

        datosPersonas = new DatosPersonas();

        etNombreApellido = rootView.findViewById(R.id.etNombreApellido);
        etDireccion = rootView.findViewById(R.id.etDireccion);
        etDNI = rootView.findViewById(R.id.etDNI);

        rbtnPrimariosSi = rootView.findViewById(R.id.rbtnPrimariosSi);
        rbtnSecundariosSi = rootView.findViewById(R.id.rbtnSecundariosSi);
        rbtnUiversitariosSi = rootView.findViewById(R.id.rbtnUniversitariosSi);
        rbtnViviendaSi = rootView.findViewById(R.id.rbtnViviendaSi);
        rbtnObraSocialSi = rootView.findViewById(R.id.rbtnObraSocialSi);
        rbtnTrabajaSi = rootView.findViewById(R.id.rbtnTrabajaSi);
        rbtnVoluntarioSi = rootView.findViewById(R.id.rbtnVoluntariadoSi);
        rbtnHijosSi = rootView.findViewById(R.id.rbtnHijosSi);

        btnGuardar = rootView.findViewById(R.id.btnGuardar);
        btnGenerar20 = rootView.findViewById(R.id.btnGenerar20);
        btnVerFijo = rootView.findViewById(R.id.btnVerFijos);
        btnVerVariable = rootView.findViewById(R.id.btnVerVariables);
    }

    private void showAlertDialog(int layout, boolean fijo) {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = li.inflate(layout, null);
        TextView tvVerTitulo = layoutView.findViewById(R.id.tvVerTitulo);
        TextView tvVerNombreApellido = layoutView.findViewById(R.id.tvVerNombreApellido);
        TextView tvVerDireccion = layoutView.findViewById(R.id.tvVerDireccion);
        TextView tvVerDni = layoutView.findViewById(R.id.tvVerDNI);
        TextView tvVerPrimario = layoutView.findViewById(R.id.tvVerPrimarios);
        TextView tvVerSecundario = layoutView.findViewById(R.id.tvVerSecundarios);
        TextView tvVerUniversitario = layoutView.findViewById(R.id.tvVerUniversitarios);
        TextView tvVerVivienda = layoutView.findViewById(R.id.tvVerVivienda);
        TextView tvVerObraSocial = layoutView.findViewById(R.id.tvVerObraSocial);
        TextView tvVerTrabajo = layoutView.findViewById(R.id.tvVerTrabajo);
        TextView tvVerVoluntario = layoutView.findViewById(R.id.tvVerVoluntario);
        TextView tvVerHijos = layoutView.findViewById(R.id.tvVerHijos);
        Button btnAceptar = layoutView.findViewById(R.id.btnVerAceptar);
        if (fijo) {
            mostrarContenidoArchivoFijo(tvVerTitulo,
                    tvVerNombreApellido,
                    tvVerDireccion,
                    tvVerDni,
                    tvVerPrimario,
                    tvVerSecundario,
                    tvVerUniversitario,
                    tvVerVivienda,
                    tvVerObraSocial,
                    tvVerTrabajo,
                    tvVerVoluntario,
                    tvVerHijos);
        } else mostrarContenidoArchivoVariable(tvVerTitulo,
                tvVerNombreApellido,
                tvVerDireccion,
                tvVerDni,
                tvVerPrimario,
                tvVerSecundario,
                tvVerUniversitario,
                tvVerVivienda,
                tvVerObraSocial,
                tvVerTrabajo,
                tvVerVoluntario,
                tvVerHijos);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        btnAceptar.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }


    private List<String> abrirArchivo(String nombreArchivo) {
        List<String> transcripcion = new ArrayList<>();
        try {
            BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo));
            String line;

            while ((line = lector.readLine()) != null) {
                transcripcion.add(line);
            }
            lector.close();
            return transcripcion;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // System.out.println("mal1");
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("mal2");
            return null;
        }

    }

    @SuppressLint("SetTextI18n")
    private void mostrarContenidoArchivoFijo(TextView tvVerTitulo,
                                             TextView tvVerNombreApellido,
                                             TextView tvVerDireccion,
                                             TextView tvVerDni,
                                             TextView tvVerPrimario,
                                             TextView tvVerSecundario,
                                             TextView tvVerUniversitario,
                                             TextView tvVerVivienda,
                                             TextView tvVerObraSocial,
                                             TextView tvVerTrabajo,
                                             TextView tvVerVoluntario,
                                             TextView tvVerHijos) {
        ArrayList<String> archivoFijo = new ArrayList<>();
        archivoFijo = (ArrayList<String>) abrirArchivo(pathArchivoFijo);

        if (archivoFijo != null) {
            tvVerTitulo.setText("Archivo Fijo:");
            tvVerNombreApellido.setText(archivoFijo.get(0));
            tvVerDireccion.setText(archivoFijo.get(1));
            tvVerDni.setText(archivoFijo.get(2));
            String j = archivoFijo.get(3);
            if (j.charAt(0) == '1') {
                tvVerPrimario.setText("SI");
            } else tvVerPrimario.setText("NO");
            if (j.charAt(1) == '1') {
                tvVerSecundario.setText("SI");
            } else tvVerSecundario.setText("NO");
            if (j.charAt(2) == '1') {
                tvVerUniversitario.setText("SI");
            } else tvVerUniversitario.setText("NO");
            if (j.charAt(3) == '1') {
                tvVerVivienda.setText("SI");
            } else tvVerVivienda.setText("NO");
            if (j.charAt(4) == '1') {
                tvVerObraSocial.setText("SI");
            } else tvVerObraSocial.setText("NO");
            if (j.charAt(5) == '1') {
                tvVerTrabajo.setText("SI");
            } else tvVerTrabajo.setText("NO");
            if (j.charAt(6) == '1') {
                tvVerVoluntario.setText("SI");
            } else tvVerVoluntario.setText("NO");
            if (j.charAt(7) == '1') {
                tvVerHijos.setText("SI");
            } else tvVerHijos.setText("NO");
        } else {
            Toast.makeText(getActivity(), "Primero debe generar los archivos", Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("SetTextI18n")
    private void mostrarContenidoArchivoVariable(TextView tvVerTitulo,
                                                 TextView tvVerNombreApellido,
                                                 TextView tvVerDireccion,
                                                 TextView tvVerDni,
                                                 TextView tvVerPrimario,
                                                 TextView tvVerSecundario,
                                                 TextView tvVerUniversitario,
                                                 TextView tvVerVivienda,
                                                 TextView tvVerObraSocial,
                                                 TextView tvVerTrabajo,
                                                 TextView tvVerVoluntario,
                                                 TextView tvVerHijos) {
        ArrayList<String> archivoVariable = new ArrayList<>();
        archivoVariable = (ArrayList<String>) abrirArchivo(pathArchivoVariable);

        if (archivoVariable != null) {
            tvVerTitulo.setText("Archivo Variable:");
            tvVerNombreApellido.setText(archivoVariable.get(0));
            tvVerDireccion.setText(archivoVariable.get(1));
            tvVerDni.setText(archivoVariable.get(2));

            char s = archivoVariable.get(3).charAt(0); //obtiene de la ultima componente los valores bivaluados como un char
            int ascii = (int) s; //ahora que lo tengo como un char, puedo sacar su valor entero como ascii
            // System.out.println(ascii); // esto lo puedes borrar

            /*maquina que descifra. Aqui comparo la cadena binaria con 128,64,32,16,8,4,2,1
             * Entonces si por ejemplo quiero saber si tiene estudios primarios, hago un and. Si da 128, es que el bit de estudios primarios esta encendido, es decir, posee.
             * 10000000 & 128 = 128 -> ok.... si da distinto, es que no posee */
            double exponente = 7;
            double pos = Math.pow(2, exponente);
            ArrayList<String> auxiliar = new ArrayList<>();

            for (int i = 0; i < 8; i++) {
                //System.out.println( ascii & 0b10000000);
                if ((ascii & (int) pos) == pos) {
                    auxiliar.add("1");
                    //System.out.println(bimap.get(i + 1)); // busco en el bitmap la cadena correspondiente al resultado. Si no está encendido el bit, no se muestra nada
                } else {
                    auxiliar.add("0");
                }
                exponente = exponente - 1;
                pos = Math.pow(2, exponente);
            }

            if (auxiliar.get(0).equals("1")) {
                tvVerPrimario.setText("SI");
            } else tvVerPrimario.setText("NO");
            if (auxiliar.get(1).equals("1")) {
                tvVerSecundario.setText("SI");
            } else tvVerSecundario.setText("NO");
            if (auxiliar.get(2).equals("1")) {
                tvVerUniversitario.setText("SI");
            } else tvVerUniversitario.setText("NO");
            if (auxiliar.get(3).equals("1")) {
                tvVerVivienda.setText("SI");
            } else tvVerVivienda.setText("NO");
            if (auxiliar.get(4).equals("1")) {
                tvVerObraSocial.setText("SI");
            } else tvVerObraSocial.setText("NO");
            if (auxiliar.get(5).equals("1")) {
                tvVerTrabajo.setText("SI");
            } else tvVerTrabajo.setText("NO");
            if (auxiliar.get(6).equals("1")) {
                tvVerVoluntario.setText("SI");
            } else tvVerVoluntario.setText("NO");
            if (auxiliar.get(7).equals("1")) {
                tvVerHijos.setText("SI");
            } else tvVerHijos.setText("NO");
        } else {
            Toast.makeText(getActivity(), "Primero debe generar los archivos", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    public static void difTamaños(TextView tvPesoFijo,
                                  TextView tvPesoVariable,
                                  TextView tvDiferencia) {
        File archivoFijoGrande = new File(pathArchivoFijoGrande);
        File archivoVariableGrande = new File(pathArchivoVariableGrande);

        tvPesoFijo.setText(archivoFijoGrande.length() + " bytes");
        tvPesoVariable.setText(archivoVariableGrande.length() + " bytes");
        tvDiferencia.setText((archivoFijoGrande.length() - archivoVariableGrande.length()) + " bytes");
    }


}
