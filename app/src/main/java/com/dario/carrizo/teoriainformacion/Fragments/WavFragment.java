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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WavFragment extends Fragment {

    private static final int REQUEST_CODE = 6384;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private Button btn;
    private TextView tvResultado;
    private TextView tvID;
    private TextView tvFormato;
    private TextView tvIdSubchunk;
    private TextView tvTamanio;
    private TextView tvTamanioSubchunk;
    private TextView tvPCM;
    private TextView tvNumCanales;
    private TextView tvSamplerate;
    private TextView tvBitrate;
    private TextView tvBlockAling;
    private TextView tvBitSample;
    private TextView tvIdSubChunk2;
    private TextView tvTamanioSubchunk2;


    public WavFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wav, container, false);
        linkVariables(rootView);

        ocultarTv();
        // Create a simple button to start the file chooser process
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {

                    showChooser();
                }

            }
        });
        return rootView;
    }

    /**
     * Lanza un Intent que permite seleccionar el archivo el cual retorna un codio de respuesta
     ***/
    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(target, "Seleccione un archivo WAV");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Log.w("FileChooserError","A error ocurred to select file");
        }
    }

    private void linkVariables(View rootView) {
        btn = rootView.findViewById(R.id.btnSelector);
        tvResultado = rootView.findViewById(R.id.tvResultado);
        tvID = rootView.findViewById(R.id.tvID);
        tvFormato = rootView.findViewById(R.id.tvFormato);
        tvIdSubchunk = rootView.findViewById(R.id.tvIdSubchunk);
        tvTamanio = rootView.findViewById(R.id.tvTamanioArchivo);
        tvTamanioSubchunk = rootView.findViewById(R.id.tvTamanioSubchunk);
        tvPCM = rootView.findViewById(R.id.tvPCM);
        tvNumCanales = rootView.findViewById(R.id.tvNumCanales);
        tvSamplerate = rootView.findViewById(R.id.tvSamplerate);
        tvBitrate = rootView.findViewById(R.id.tvByterate);
        tvBlockAling = rootView.findViewById(R.id.tvBlockalign);
        tvBitSample = rootView.findViewById(R.id.tvBitsSample);
        tvIdSubChunk2 = rootView.findViewById(R.id.tvIDSubchunk2);
        tvTamanioSubchunk2 = rootView.findViewById(R.id.tvTamsubchunk2);
    }


    /**
     * onActivityResult recibe el codigo de respuesta generado al momento de seleccionar el archivo
     * en el Intent lanzado a partir del metodo showChooser()
     * **/
    @SuppressLint("LongLogTag")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si la seleccion del archivo fue correcta
        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                if (data != null) {
                    //Se obtiene la URI del archivo seleccionado
                    final Uri uri = data.getData();
                    //Log.i("TAG", "Uri = " + uri.toString());
                    try {
                        //Obtenemos el path del archivo a partir de la uri
                        final String path = FileUtils.getPath(getActivity(), uri);
                        //Enviamos el path del archivo para la verificacion correspondiente
                        isWavFile(path);
                    } catch (Exception e) {
                        Log.e("Error Response", "File select error", e);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * isWavFile recibe el path y realiza la verificacion de que si el archivo seleccionado es un .WAV
     * * */
    private void isWavFile(String FILEPATH) {
        //Uso de try/catch debido a que puede ocurrir un error de I/O
        try {
           /**
            * bytelector enviamos el path y la posicion que deseamos que nos retorne del archivo,
            * la posicion y el tamaño corresponde a los datos brindados en el documento de practica
            * en el me base para posicionarme
            * */
            if ((new String(bytelector.readFromFile(FILEPATH, 8, 4))).equals("WAVE")) {
                mostrarTv();
                tvResultado.setText("El archivo seleccionado es un .WAV");
                tvID.setText("ChunkID: " + new String(bytelector.readFromFile(FILEPATH, 0, 4)));
                byte array[] = bytelector.readFromFile(FILEPATH, 4, 4);
                float chunksize = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvTamanio.setText("ChunkSize: " + chunksize);
                tvFormato.setText("Formato: " + new String(bytelector.readFromFile(FILEPATH, 8, 4)));
                tvIdSubchunk.setText("ID Subchunk1: " + new String(bytelector.readFromFile(FILEPATH, 12, 4)));
                array = bytelector.readFromFile(FILEPATH, 16, 4);
                int subchunksize = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvTamanioSubchunk.setText("Tamaño Subchunk1: " + subchunksize);
                array = bytelector.readFromFile(FILEPATH, 20, 2);
                short audiof = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getShort();
                tvPCM.setText("PCM: " + audiof);
                array = bytelector.readFromFile(FILEPATH, 22, 2);
                int numc = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getShort();
                tvNumCanales.setText("Numero de Canales: " + numc);
                array = bytelector.readFromFile(FILEPATH, 24, 4);
                int samplerate = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvSamplerate.setText("Samplerate: " + samplerate);
                array = bytelector.readFromFile(FILEPATH, 28, 4);
                int byterate = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvBitrate.setText("Byterate: " + byterate);
                array = bytelector.readFromFile(FILEPATH, 32, 2);
                int blocka = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getShort();
                tvBlockAling.setText("Blockalign: " + blocka);
                array = bytelector.readFromFile(FILEPATH, 34, 2);
                int bps = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getShort();
                tvBitSample.setText("Bits per Sample: " + bps);
                tvIdSubChunk2.setText("ID Subchunk2: " + new String(bytelector.readFromFile(FILEPATH, 36, 4)));
                array = bytelector.readFromFile(FILEPATH, 40, 4);
                int subchunksize2 = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN).getInt();
                tvTamanioSubchunk2.setText("Tamaño Subchunk2: " + subchunksize2);
            } else {
                ocultarTv();
                tvResultado.setText("El archivo seleccionado no es .WAV");
            }
        } catch (IOException e) {
            Toast.makeText(getActivity(), "El archivo seleccionado esta dañado, intente nuevamente con uno diferente", Toast.LENGTH_SHORT).show();
            tvResultado.setText("Error al abrir el archivo");
            ocultarTv();
        }
    }

    /**
     * Tanto ocultarTv como mostrarTv, sirven para hacer o no visibles los TextView en la interfaz
     * */

    private void ocultarTv() {
        tvID.setVisibility(GONE);
        tvFormato.setVisibility(GONE);
        tvIdSubchunk.setVisibility(GONE);
        tvTamanio.setVisibility(GONE);
        tvTamanioSubchunk.setVisibility(GONE);
        tvPCM.setVisibility(GONE);
        tvNumCanales.setVisibility(GONE);
        tvSamplerate.setVisibility(GONE);
        tvBitrate.setVisibility(GONE);
        tvBlockAling.setVisibility(GONE);
        tvBitSample.setVisibility(GONE);
        tvIdSubChunk2.setVisibility(GONE);
        tvTamanioSubchunk2.setVisibility(GONE);
    }

    private void mostrarTv() {
        tvID.setVisibility(VISIBLE);
        tvFormato.setVisibility(VISIBLE);
        tvIdSubchunk.setVisibility(VISIBLE);
        tvTamanio.setVisibility(VISIBLE);
        tvTamanioSubchunk.setVisibility(VISIBLE);
        tvPCM.setVisibility(VISIBLE);
        tvNumCanales.setVisibility(VISIBLE);
        tvSamplerate.setVisibility(VISIBLE);
        tvBitrate.setVisibility(VISIBLE);
        tvBlockAling.setVisibility(VISIBLE);
        tvBitSample.setVisibility(VISIBLE);
        tvIdSubChunk2.setVisibility(VISIBLE);
        tvTamanioSubchunk2.setVisibility(VISIBLE);
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
                    showDialog("Almacenamiento Externo", context,
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
        alertBuilder.setTitle("Permiso Necesario");
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
