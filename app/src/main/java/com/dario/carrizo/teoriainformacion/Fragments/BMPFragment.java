package com.dario.carrizo.teoriainformacion.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dario.carrizo.teoriainformacion.Commons.FileUtils;
import com.dario.carrizo.teoriainformacion.Fragments.wavDecode.bytelector;
import com.dario.carrizo.teoriainformacion.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BMPFragment extends Fragment {


    private static final int REQUEST_CODE = 6384;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private TextView tvResultadoBMP;
    private TextView tvIDBMP;
    private TextView tvTamanioArchivoBMP;
    private TextView tvReservado;
    private TextView tvPosicionComienzoImagen;
    private TextView tvTamanioSeccion;
    private TextView tvAnchoPixeles;
    private TextView tvAltoPixeles;
    private TextView tvNumeroPlanos;
    private TextView tvBitPixel;
    private TextView tvTipoCompresion;
    private TextView tvTamanioComprimido;
    private TextView tvResolucionHorizontal;
    private TextView tvResolucionVertical;
    private TextView tvNumeroColoresUsados;
    private TextView tvNumeroColoresImportantes;
    private TextView tvPropiedades;
    private Button btnBMPSelector;

    public BMPFragment() {
        // Required empty public constructor
    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, "Seleccione un Archivo BMP");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }
    /**
     *  funcion onCreate -> Parte del ciclo de vida de una aplicacion android, en ella se llevan a cabo
     *  las configuraciones correspondientes al momento de crear el Fragment que sera visible al usuario
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bm, container, false);

        linkVariables(rootView);

        btnBMPSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {

                    showChooser();
                }
            }
        });
        ocultarTv();
        return rootView;
    }

    private void linkVariables(View rootView) {
        btnBMPSelector = rootView.findViewById(R.id.btnExplorarBMP);
        // Inflate the layout for this fragment
        tvResultadoBMP = rootView.findViewById(R.id.tvResultadoBMP);
        tvIDBMP = rootView.findViewById(R.id.tvIDBMP);
        tvTamanioArchivoBMP = rootView.findViewById(R.id.tvTamanioArchivoBMP);
        tvReservado = rootView.findViewById(R.id.tvReservado);
        tvPosicionComienzoImagen = rootView.findViewById(R.id.tvPosicionDatos);
        tvTamanioSeccion = rootView.findViewById(R.id.tvTamanioSeccionBMP);
        tvAnchoPixeles = rootView.findViewById(R.id.tvAnchoPixeles);
        tvAltoPixeles = rootView.findViewById(R.id.tvAltoPixeles);
        tvNumeroPlanos = rootView.findViewById(R.id.tvNumeroPlanos);
        tvBitPixel = rootView.findViewById(R.id.tvBitPixel);
        tvTipoCompresion = rootView.findViewById(R.id.tvCompresion);
        tvTamanioComprimido = rootView.findViewById(R.id.tvTamanioComprimidoBMP);
        tvResolucionHorizontal = rootView.findViewById(R.id.tvResolucionHorizontal);
        tvResolucionVertical = rootView.findViewById(R.id.tvResolucionVertical);
        tvNumeroColoresUsados = rootView.findViewById(R.id.tvNumeroColoresUsados);
        tvNumeroColoresImportantes = rootView.findViewById(R.id.tvNumeroColoresImportantes);
        tvPropiedades = rootView.findViewById(R.id.tvPropiedades);
    }

    /**
     * onActivityResult recibe el codigo de respuesta generado al momento de seleccionar el archivo
     * en el Intent lanzado a partir del metodo showChooser()
     * **/
    @SuppressLint("LongLogTag")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        //Log.i("TAG", "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(getActivity(), uri);
                            //wave.decode(path);
                            isBMPFile(path);
                        } catch (Exception e) {
                            Log.e("Error Response", "File select error", e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * isBMPFile recibe el path y realiza la verificacion de que si el archivo seleccionado es un .BMP
     * * */
    @SuppressLint("SetTextI18n")
    private void isBMPFile(String FILEPATH) {
        try {
            /**
             * bytelector enviamos el path y la posicion que deseamos que nos retorne del archivo,
             * la posicion y el tamaño corresponde a los datos brindados en el documento de practica
             * en el me base para posicionarme
             * */
            if ((new String(bytelector.readFromFile(FILEPATH, 0, 2))).equals("BM")) {
                mostrarTv();
                tvResultadoBMP.setText("El archivo seleccionado es un .BMP");
                tvIDBMP.setText("ID: " + new String(bytelector.readFromFile(FILEPATH, 0, 2)));
                byte array[] = bytelector.readFromFile(FILEPATH, 2, 4);
                float chunksize = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvTamanioArchivoBMP.setText("Tamaño del Fichero: " + chunksize);
                array = bytelector.readFromFile(FILEPATH, 6, 4);
                float res = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvReservado.setText("Reservado: " + res);
                array = bytelector.readFromFile(FILEPATH, 10, 4);
                float posrel = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvPosicionComienzoImagen.setText("Posicion de Comienzo de Datos: " + posrel);
                array = bytelector.readFromFile(FILEPATH, 14, 4);
                int size = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvTamanioSeccion.setText("Tamaño de Sección: " + size);
                array = bytelector.readFromFile(FILEPATH, 18, 4);
                short width = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getShort();
                tvAnchoPixeles.setText("Ancho en Pixeles: " + width);
                array = bytelector.readFromFile(FILEPATH, 22, 4);
                int heigh = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvAltoPixeles.setText("Alto en Pixeles: " + heigh);
                array = bytelector.readFromFile(FILEPATH, 26, 2);
                int nump = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getShort();
                tvNumeroPlanos.setText("Número de Planos: " + nump);
                array = bytelector.readFromFile(FILEPATH, 28, 2);
                int bitcount = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getShort();
                tvBitPixel.setText("Bit por Pixel: " + bitcount);
                array = bytelector.readFromFile(FILEPATH, 30, 4);
                int compression = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvTipoCompresion.setText("Tipo de Compresion (0,1,2): " + compression);
                array = bytelector.readFromFile(FILEPATH, 34, 4);
                int bps = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvTamanioComprimido.setText("Tamaño Comprimido: " + bps);
                array = bytelector.readFromFile(FILEPATH, 38, 4);
                int xperm = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvResolucionHorizontal.setText("Resolución Horizontal: " + xperm);
                array = bytelector.readFromFile(FILEPATH, 42, 4);
                int yperm = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvResolucionVertical.setText("Resolución Vertical: " + yperm);
                array = bytelector.readFromFile(FILEPATH, 46, 4);
                int colorused = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvNumeroColoresUsados.setText("Número de Colores Usado: " + colorused);
                array = bytelector.readFromFile(FILEPATH, 50, 4);
                int colorImportant = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvNumeroColoresImportantes.setText("Número de Colores Importantes: " + colorImportant);
            } else {
                ocultarTv();
                tvResultadoBMP.setText("El archivo seleccionado no es .BMP");
            }
        } catch (Exception e) {
            tvResultadoBMP.setText("Error al abrir el archivo");
            ocultarTv();
        }
    }
    /**
     * Tanto ocultarTv como mostrarTv, sirven para hacer o no visibles los TextView en la interfaz
     * */

    private void ocultarTv() {
        tvIDBMP.setVisibility(GONE);
        tvTamanioArchivoBMP.setVisibility(GONE);
        tvReservado.setVisibility(GONE);
        tvPosicionComienzoImagen.setVisibility(GONE);
        tvTamanioSeccion.setVisibility(GONE);
        tvAnchoPixeles.setVisibility(GONE);
        tvAltoPixeles.setVisibility(GONE);
        tvNumeroPlanos.setVisibility(GONE);
        tvBitPixel.setVisibility(GONE);
        tvTipoCompresion.setVisibility(GONE);
        tvTamanioComprimido.setVisibility(GONE);
        tvResolucionHorizontal.setVisibility(GONE);
        tvResolucionVertical.setVisibility(GONE);
        tvNumeroColoresUsados.setVisibility(GONE);
        tvNumeroColoresImportantes.setVisibility(GONE);
        tvPropiedades.setVisibility(GONE);
    }

    private void mostrarTv() {
        tvIDBMP.setVisibility(VISIBLE);
        tvTamanioArchivoBMP.setVisibility(VISIBLE);
        tvReservado.setVisibility(VISIBLE);
        tvPosicionComienzoImagen.setVisibility(VISIBLE);
        tvTamanioSeccion.setVisibility(VISIBLE);
        tvAnchoPixeles.setVisibility(VISIBLE);
        tvAltoPixeles.setVisibility(VISIBLE);
        tvNumeroPlanos.setVisibility(VISIBLE);
        tvBitPixel.setVisibility(VISIBLE);
        tvTipoCompresion.setVisibility(VISIBLE);
        tvTamanioComprimido.setVisibility(VISIBLE);
        tvResolucionHorizontal.setVisibility(VISIBLE);
        tvResolucionVertical.setVisibility(VISIBLE);
        tvNumeroColoresUsados.setVisibility(VISIBLE);
        tvNumeroColoresImportantes.setVisibility(VISIBLE);
        tvPropiedades.setVisibility(VISIBLE);
    }

    /**
     * Verifica si la aplicacion tiene permiso de acceso al almacenamiento del dispositivo
     * de no ser asi lo solicita.
     * */
    private boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    /**
     * showDialog generamos el dialogo que se mostrara para solicitar el permiso correspondiente
     * */
    private void showDialog(final String msg, final Context context,
                            final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Necesitan Permisos");
        alertBuilder.setMessage(msg + " necesita permisos");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    /***
     * onRequestPermissionsResult se recibe el codigo de respuesta a la peticion de permiso de acceso
     * al almacenamiento del archivo
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(getActivity(), "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

}
