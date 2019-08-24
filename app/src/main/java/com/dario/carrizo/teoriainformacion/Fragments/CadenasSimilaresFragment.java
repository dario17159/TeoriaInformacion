package com.dario.carrizo.teoriainformacion.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dario.carrizo.teoriainformacion.Commons.LevenshteinDistance;
import com.dario.carrizo.teoriainformacion.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CadenasSimilaresFragment extends Fragment {

    private TextInputEditText tiCadena1, tiCadena2;
    private TextView tvPorcentage;
    private String cad = "";

    public CadenasSimilaresFragment() {
        // Required empty public constructor
    }

    /**
     *  funcion onCreate -> Parte del ciclo de vida de una aplicacion android, en ella se llevan a cabo
     *  las configuraciones correspondientes al momento de crear el Fragment que sera visible al usuario
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cadenas_similares, container, false);
        tiCadena1 = rootView.findViewById(R.id.tiCad1);
        tiCadena2 = rootView.findViewById(R.id.tiCad2);
        tvPorcentage = rootView.findViewById(R.id.tvPorcentage);
        tvPorcentage.setVisibility(View.GONE);
        tiCadena2.setEnabled(false);
        /* el metodo addTextChangeListener nos permite manipular los eventos
            que se ocacionan al momento de escribir sobre in EditText,
            brinandonos informacion antes, durante y despues de realizar una modificacion en el.
         */
        tiCadena1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    cad = charSequence.toString().trim();
                    tiCadena2.setEnabled(true);
                } else{
                    tiCadena2.setText("");
                    tvPorcentage.setVisibility(View.GONE);
                    tiCadena2.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tiCadena2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().isEmpty()) {
                    //al momento de recibir la segunda cadena calculamos la distancia de Levenshtein
                    int lengthCadena1 = cad.length();
                    int distancia = LevenshteinDistance.computeLevenshteinDistance(tiCadena1.getText().toString().trim(), charSequence.toString().trim());
                    double porcentaje = 0;
                    /*Realizamos el caluculo del porcentaje respecto a la distancia obtenida y la longitud de la primer cadena
                    la cual es a la que se le realiza la comparacion
                     */

                    porcentaje = ((lengthCadena1 - distancia) * 100) / lengthCadena1;
                    tvPorcentage.setVisibility(View.VISIBLE);
                    tvPorcentage.setText("Las cadenas se asemejan en un "+porcentaje+"%");
                } else {
                    tvPorcentage.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return rootView;
    }

}
