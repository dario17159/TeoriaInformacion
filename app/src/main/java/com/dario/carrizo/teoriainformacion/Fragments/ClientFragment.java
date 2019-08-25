package com.dario.carrizo.teoriainformacion.Fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Telephony;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dario.carrizo.teoriainformacion.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment {

    /**
     * Puerto
     */
    private static final int SERVERPORT = 5000;
    /**
     * HOST
     */
    private static String ADDRESS = "";

    /**
     * Variables
     * */
    private TextInputEditText etIp,etCadena;
    private Button btnConectar;
    private TextView tvResponseServer;

    public ClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_client, container, false);
        // Inflate the layout for this fragment
        liknVariables(rootView);
        
        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ADDRESS = etIp.getText().toString().trim();
                if(!ADDRESS.isEmpty()){
                    if(!etCadena.getText().toString().isEmpty()) {
                        MyATaskCliente myATaskYW = new MyATaskCliente();
                        myATaskYW.execute(etCadena.getText().toString().trim().toUpperCase());
                    } else{
                        etCadena.setError("Debe ingresar la cadena a codificar");
                        etCadena.requestFocus();
                    }

                }else{
                    etIp.setError("Primero debe ingresar la IP del servidor");
                    etIp.requestFocus();
                }
            }
        });

        return rootView;
    }

    private void liknVariables(View rootView) {
        btnConectar = rootView.findViewById(R.id.btnConectar);
        etCadena = rootView.findViewById(R.id.etCadena);
        etIp = rootView.findViewById(R.id.etIp);
        tvResponseServer = rootView.findViewById(R.id.tvResponseServer);
        tvResponseServer.setVisibility(View.GONE);
    }

    /**
     * Clase para interactuar con el servidor
     */
    class MyATaskCliente extends AsyncTask<String, Void, String> {

        /**
         * Ventana que bloqueara la pantalla del movil hasta recibir respuesta del servidor
         */
        ProgressDialog progressDialog;

        /**
         * muestra una ventana emergente
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("Connecting to server");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        /**
         * Se conecta al servidor y trata resultado
         */
        @Override
        protected String doInBackground(String... values) {

            try {
                //Se conecta al servidor
                InetAddress serverAddr = InetAddress.getByName(ADDRESS);
                Log.i("I/TCP Client", "Connecting...");
                Socket socket = new Socket(serverAddr, SERVERPORT);
                Log.i("I/TCP Client", "Connected to server");

                //envia peticion de cliente
                Log.i("I/TCP Client", "Send data to server");
                PrintStream output = new PrintStream(socket.getOutputStream());
                String request = values[0]; //almacena en request la cadena que obtiene del etCadena
                output.println(request);

                //recibe respuesta del servidor y formatea a String
                Log.i("I/TCP Client", "Received data to server");
                InputStream stream = socket.getInputStream();
                byte[] lenBytes = new byte[256];
                stream.read(lenBytes, 0, 256);
                String received = new String(lenBytes, "UTF-8").trim();
                Log.i("I/TCP Client", "Received " + received);
                Log.i("I/TCP Client", "");
                //cierra conexion
                socket.close();
                return received;
            } catch (UnknownHostException ex) {
                Log.e("E/TCP Client", "" + ex.getMessage());
  //              Toast.makeText(getActivity(), "No se puede conectar al host "+ADDRESS, Toast.LENGTH_SHORT).show();
                return ex.getMessage();
            } catch (IOException ex) {
                Log.e("E/TCP Client", "" + ex.getMessage());
//                Toast.makeText(getActivity(), "Ocurrio un error inesperado, Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                return ex.getMessage();
            }
        }

        /**
         * Oculta ventana emergente y muestra resultado en pantalla
         */
        @Override
        protected void onPostExecute(String value) {
            progressDialog.dismiss();
            tvResponseServer.setVisibility(View.VISIBLE);
            tvResponseServer.setText(value);
            //editText2.setText(value);
        }
    }

}
