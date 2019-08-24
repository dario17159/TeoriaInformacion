package com.dario.carrizo.teoriainformacion.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.dario.carrizo.teoriainformacion.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CuitCuilFragment extends Fragment {

    private TextInputEditText tiCuilCuit;
    private Button btnValidar;
    private LottieAnimationView success, fail;


    int cantidad = 0;
    int [] arreglo;
    int [] multiplicadores = {5,4,3,2,7,6,5,4,3,2};


    public CuitCuilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cuit_cuil, container, false);
        tiCuilCuit = rootView.findViewById(R.id.tiCuilCuit);
        btnValidar = rootView.findViewById(R.id.btnValidarCuitCuil);
        success = rootView.findViewById(R.id.success);
        fail = rootView.findViewById(R.id.fail);

        arreglo = new int[11];
        btnValidar.setEnabled(false);
        tiCuilCuit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                success.setVisibility(View.GONE);
                success.cancelAnimation();
                fail.setVisibility(View.GONE);
                fail.cancelAnimation();
                if(cantidad < charSequence.toString().trim().length()){
                    arreglo[cantidad] = Integer.parseInt(""+charSequence.toString().trim().charAt(cantidad));
                    cantidad++;
                }else if (cantidad > charSequence.toString().trim().length()){
                    cantidad--;
                }
                if(charSequence.toString().trim().length() == 11){
                    btnValidar.setEnabled(true);
                }else{
                    btnValidar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(tiCuilCuit.getWindowToken(), 0);
                if(validar(arreglo)){
                    success.setVisibility(View.VISIBLE);
                    success.playAnimation();
                    //Toast.makeText(getActivity(), "Valido", Toast.LENGTH_SHORT).show();
                }else {
                    fail.setVisibility(View.VISIBLE);
                    fail.playAnimation();
                    //Toast.makeText(getActivity(), "Invalido", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private boolean validar(int[] arreglo) {
        int acumulador = 0, resultado,resto;
        for(int i=0;i<multiplicadores.length;i++){
            acumulador+=arreglo[i]*multiplicadores[i];
        }
        resto = (acumulador%arreglo.length);
        if(resto == 0){
            resultado = 0;
        } else if(resto == 1){
            resultado = 9;
        } else {
            resultado = arreglo.length - resto;
        }
        if(resultado == arreglo[arreglo.length-1]){
            return true;
        }else return false;
    }

}
