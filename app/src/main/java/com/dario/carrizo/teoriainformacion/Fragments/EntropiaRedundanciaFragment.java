package com.dario.carrizo.teoriainformacion.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dario.carrizo.teoriainformacion.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntropiaRedundanciaFragment extends Fragment {

    //TODO: Implementar AsyncTask y Progresbar

    private InputStream txt,docx,odt,rtf,zip;
    private TextView tvEntropiaTxt,tvRedundanciaTxt;
    private TextView tvEntropiaDocx,tvRedundanciaDocx;
    private TextView tvEntropiaOdt,tvRedundanciaOdt;
    private TextView tvEntropiaRtf,tvRedundanciaRtf;
    private TextView tvEntropiaZip,tvRedundanciaZip;



    public EntropiaRedundanciaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_entropia_redundancia, container, false);
        tvEntropiaTxt = rootView.findViewById(R.id.tvEntropiaTxt );
        tvRedundanciaTxt = rootView.findViewById(R.id.tvRedundanciaTxt );
        tvEntropiaDocx = rootView.findViewById(R.id.tvEntropiaDocx );
        tvRedundanciaDocx = rootView.findViewById(R.id.tvRedundanciaDocx );
        tvEntropiaOdt = rootView.findViewById(R.id.tvEntropiaOdt );
        tvRedundanciaOdt = rootView.findViewById(R.id.tvRedundanciaOdt );
        tvEntropiaRtf = rootView.findViewById(R.id.tvEntropiaRtf );
        tvRedundanciaRtf = rootView.findViewById(R.id.tvRedundanciaRtf );
        tvEntropiaZip = rootView.findViewById(R.id.tvEntropiaZip );
        tvRedundanciaZip = rootView.findViewById(R.id.tvRedundanciaZip );
        try {
            txt= getResources().getAssets().open("hackers.txt");
            entropia(txt,tvEntropiaTxt,tvRedundanciaTxt,"hackers.txt");
            docx = getResources().getAssets().open("hackers.docx");
            entropia(docx,tvEntropiaDocx,tvRedundanciaDocx,"hackers.docx");
            odt = getResources().getAssets().open("hackers.odt");
            entropia(odt,tvEntropiaOdt,tvRedundanciaOdt,"hackers.odt");
            rtf = getResources().getAssets().open("hackers.rtf");
            entropia(rtf,tvEntropiaRtf,tvRedundanciaRtf,"hackers.rtf");
            zip = getResources().getAssets().open("hackers.zip");
            entropia(zip,tvEntropiaZip,tvRedundanciaZip,"hackers.zip");

        } catch (IOException e) {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }
        // Inflate the layout for this fragment
        return rootView;
    }

    private static void entropia(InputStream file, TextView tvEntropia, TextView tvRedundancia,String nombreArchivo) throws IOException {
        double entropia = 0.0;
        double probabilidad = 0.0;
        double informacion = 0.0;
        final int[] aux2 = new int[256];
        int i = 0;
        int longitud = 0;
        final double hmax = Math.log10(256.0) / Math.log10(2.0);
        //System.out.println(hmax);
        for (int j = 0; j < aux2.length; ++j) {
            aux2[j] = 0;
        }
        final BufferedInputStream lectorFichero = new BufferedInputStream(file);
        byte bytes;
        while ((bytes = (byte)lectorFichero.read()) != -1) {
            final char aux3 = (char)bytes;
            if (aux3 > '\0' && aux3 < '\u00ff') {
                final int[] array = aux2;
                final char c = aux3;
                ++array[c];
            }
            ++longitud;
        }
        while (i < 256) {
            if (aux2[i] != 0) {
                probabilidad = aux2[i] / (double)longitud;
                informacion = Math.log(Math.pow(probabilidad, -1.0));
                entropia += probabilidad * informacion;
            }
            ++i;
        }
        tvEntropia.setText("Entropía: "+entropia);
        tvRedundancia.setText("Redundancia: " + (1.0 - entropia / hmax));
        lectorFichero.close();
    }
}
