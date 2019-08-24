package com.dario.carrizo.teoriainformacion.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dario.carrizo.teoriainformacion.R;
import com.google.android.material.textfield.TextInputEditText;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CanalesFragment extends Fragment {

    private TextInputEditText tiCanal;
    private TextView tvNombreCanal;
    private Button btnAceptar;
    private TextView tvA1, tvA2, tvA3, tvA4, tvInfo;

    public CanalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_canales, container, false);
        // Inflate the layout for this fragment
        linkVariables(rootView);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(tiCanal.getWindowToken(), 0);
                String valor = tiCanal.getText().toString();
                if (!valor.isEmpty()) {

                    switch (Integer.parseInt(valor)) {
                        case 2:
                            tvNombreCanal.setVisibility(VISIBLE);
                            tvNombreCanal.setText("Canal Binario");
                            ocultarTodo();
                            calcularCapacidad2();
                            tiCanal.setText("");
                            tiCanal.requestFocus();
                            break;
                        case 3:
                            tvNombreCanal.setVisibility(VISIBLE);
                            tvNombreCanal.setText("Canal Ternario");
                            ocultarTodo();
                            calcularCapacidad3();
                            tiCanal.setText("");
                            tiCanal.requestFocus();
                            break;
                        case 4:
                            tvNombreCanal.setVisibility(VISIBLE);
                            tvNombreCanal.setText("Canal Cuaternario");
                            ocultarTodo();
                            calcularCapacidad4();
                            tiCanal.setText("");
                            tiCanal.requestFocus();
                            break;
                        default:
                            tvNombreCanal.setVisibility(GONE);
                            Toast.makeText(getActivity(), "Ingreso un valor no permitido", Toast.LENGTH_SHORT).show();
                            tiCanal.setText("");
                            tiCanal.requestFocus();
                            ocultarTodo();
                            break;
                    }
                } else {
                    tiCanal.setError("Debe ingresar un valor");
                    tiCanal.requestFocus();
                    ocultarTodo();
                }
            }
        });
        return rootView;
    }

    private void linkVariables(View rootView) {
        tiCanal = rootView.findViewById(R.id.ti_canal);
        btnAceptar = rootView.findViewById(R.id.btnAceptar);
        tvNombreCanal = rootView.findViewById(R.id.tvNombreCanal);
        tvA1 = rootView.findViewById(R.id.tvA1);
        tvA1.setVisibility(GONE);
        tvA2 = rootView.findViewById(R.id.tvA2);
        tvA2.setVisibility(GONE);
        tvA3 = rootView.findViewById(R.id.tvA3);
        tvA3.setVisibility(GONE);
        tvA4 = rootView.findViewById(R.id.tvA4);
        tvA4.setVisibility(GONE);
        tvInfo = rootView.findViewById(R.id.tvInfo);
        tvInfo.setVisibility(GONE);
        tvNombreCanal.setVisibility(GONE);
    }

    private void ocultarTodo() {
        tvA1.setVisibility(GONE);
        tvA2.setVisibility(GONE);
        tvA3.setVisibility(GONE);
        tvA4.setVisibility(GONE);
        tvInfo.setVisibility(GONE);
    }
    /**
    * Calculamos la capacidad maxima de un canal cuaternario
    * */
    @SuppressLint("SetTextI18n")
    private void calcularCapacidad4() {
        //Probabilidades maximax
        double mpa1 = 0.0;
        double mpa2 = 0.0;
        double mpa3 = 0.0;
        double mpa4 = 0.0;
        //Informacion
        double Iab = 0.0;
        // Probabilidades
        double pa1 = 0.0;
        double pa2 = 0.0;
        double pa3 = 0.0;
        double pa4 = 0.0;
        //Auxiliares para realizar los calculos
        double aux1 = 0.0;
        double aux2 = 0.0;
        double aux3 = 0.0;
        double aux4 = 0.0;
        // Capacidad maxima del canal
        double max = 0.0;
        // Canales
        final double b1a1 = 0.25;
        final double b2a1 = 0.25;
        final double b3a1 = 0.25;
        final double b4a1 = 0.25;
        final double b1a2 = 0.2;
        final double b2a2 = 0.4;
        final double b3a2 = 0.2;
        final double b4a2 = 0.2;
        final double b1a3 = 0.16666666666666666;
        final double b2a3 = 0.3333333333333333;
        final double b3a3 = 0.16666666666666666;
        final double b4a3 = 0.3333333333333333;
        final double b1a4 = 0.125;
        final double b2a4 = 0.25;
        final double b3a4 = 0.375;
        final double b4a4 = 0.25;
        while (pa1 <= 1.0) {
            for (aux2 = 0.0, pa2 = 0.0; pa2 < 1.0 && pa1 + pa2 <= 1.0; pa2 = Math.round(aux2 * 100.0) / 100.0) {
                for (aux3 = 0.0, pa3 = 0.0; pa3 <= 1.0 && pa1 + pa2 + pa3 <= 1.0; pa3 = Math.round(aux3 * 100.0) / 100.0) {
                    for (aux4 = 0.0, pa4 = 0.0; pa4 <= 1.0 && pa1 + pa2 + pa3 + pa4 == 1.0; pa4 = Math.round(aux4 * 100.0) / 100.0) {
                        final double psb1 = pa1 * b1a1 + pa2 * b1a2 + pa3 * b1a3 + pa4 * b1a4;
                        final double psb2 = pa1 * b2a1 + pa2 * b2a2 + pa3 * b2a3 + pa4 * b2a4;
                        final double psb3 = pa1 * b3a1 + pa2 * b3a2 + pa3 * b3a3 + pa4 * b3a4;
                        final double psb4 = pa1 * b4a1 + pa2 * b4a2 + pa3 * b4a3 + pa4 * b4a4;
                        final double HB = psb1 * (Math.log(1.0 / psb1) / Math.log(2.0)) + psb2 * (Math.log(1.0 / psb2) / Math.log(2.0)) + psb3 * (Math.log(1.0 / psb3) / Math.log(2.0)) + psb4 * (Math.log(1.0 / psb4) / Math.log(2.0));
                        final double Hba = pa1 * (b1a1 * (Math.log(1.0 / b1a1) / Math.log(2.0)) + b2a1 * (Math.log(1.0 / b2a1) / Math.log(2.0)) + b3a1 * (Math.log(1.0 / b3a1) / Math.log(2.0))) + pa2 * (b1a2 * (Math.log(1.0 / b1a2) / Math.log(2.0)) + b2a2 * (Math.log(1.0 / b2a2) / Math.log(2.0)) + b3a2 * (Math.log(1.0 / b3a2) / Math.log(2.0))) + pa3 * (b1a3 * (Math.log(1.0 / b1a3) / Math.log(2.0)) + b2a3 * (Math.log(1.0 / b2a3) / Math.log(2.0)) + b3a3 * (Math.log(1.0 / b3a3) / Math.log(2.0))) + pa4 * (b1a4 * (Math.log(1.0 / b1a4) / Math.log(2.0)) + b2a4 * (Math.log(1.0 / b2a4) / Math.log(2.0)) + b3a4 * (Math.log(1.0 / b3a4) / Math.log(2.0)));
                        Iab = HB - Hba;
                        if (Iab < -0.05) {
                            System.out.println(" ");
                        }
                        if (Iab > max) {
                            max = Iab;
                            mpa1 = pa1;
                            mpa2 = pa2;
                            mpa3 = pa3;
                            mpa4 = pa4;
                        }
                        aux4 += 0.01;
                    }
                    aux3 += 0.01;
                }
                aux2 += 0.01;
            }
            aux1 += 0.01;
            pa1 = Math.round(aux1 * 100.0) / 100.0;
        }

        tvA1.setVisibility(VISIBLE);
        tvA2.setVisibility(VISIBLE);
        tvA3.setVisibility(VISIBLE);
        tvA4.setVisibility(VISIBLE);
        tvInfo.setVisibility(VISIBLE);

        tvA1.setText("Probabilidad para la fuente A1: \n" + mpa1);
        tvA2.setText("Probabilidad para la fuente A2: \n" + mpa2);
        tvA3.setText("Probabilidad para la fuente A3: \n" + mpa3);
        tvA4.setText("Probabilidad para la fuente A4: \n" + mpa4);
        tvInfo.setText("Información obtenida de las probabilidades: \n" + max);
    }

    @SuppressLint("SetTextI18n")
    private void calcularCapacidad3() {
        double mpa1 = 0.0;
        double mpa2 = 0.0;
        double mpa3 = 0.0;
        double Iab = 0.0;
        double pa1 = 0.0;
        double pa2 = 0.0;
        double pa3 = 0.0;
        double aux1 = 0.0;
        double aux2 = 0.0;
        double aux3 = 0.0;
        double max = 0.0;
        final double b1a1 = 0.25;
        final double b2a1 = 0.5;
        final double b3a1 = 0.25;
        final double b1a2 = 0.2;
        final double b2a2 = 0.6;
        final double b3a2 = 0.2;
        final double b1a3 = 0.3333333333333333;
        final double b2a3 = 0.3333333333333333;
        final double b3a3 = 0.3333333333333333;

        while (pa1 <= 1.0) {
            for (aux2 = 0.0, pa2 = 0.0; pa2 < 1.0 && pa1 + pa2 <= 1.0; pa2 = Math.round(aux2 * 100.0) / 100.0) {
                for (aux3 = 0.0, pa3 = 0.0; pa3 <= 1.0 && pa1 + pa2 + pa3 <= 1.0; pa3 = Math.round(aux3 * 100.0) / 100.0) {
                    if (pa1 + pa2 + pa3 == 1.0) {
                        final double psb1 = pa1 * b1a1 + pa2 * b1a2 + pa3 * b1a3;
                        final double psb2 = pa1 * b2a1 + pa2 * b2a2 + pa3 * b2a3;
                        final double psb3 = pa1 * b3a1 + pa2 * b3a2 + pa3 * b3a3;
                        final double HB = psb1 * (Math.log(1.0 / psb1) / Math.log(2.0)) + psb2 * (Math.log(1.0 / psb2) / Math.log(2.0)) + psb3 * (Math.log(1.0 / psb3) / Math.log(2.0));
                        final double Hba = pa1 * (b1a1 * (Math.log(1.0 / b1a1) / Math.log(2.0)) + b2a1 * (Math.log(1.0 / b2a1) / Math.log(2.0)) + b3a1 * (Math.log(1.0 / b3a1) / Math.log(2.0))) + pa2 * (b1a2 * (Math.log(1.0 / b1a2) / Math.log(2.0)) + b2a2 * (Math.log(1.0 / b2a2) / Math.log(2.0)) + b3a2 * (Math.log(1.0 / b3a2) / Math.log(2.0))) + pa3 * (b1a3 * (Math.log(1.0 / b1a3) / Math.log(2.0)) + b2a3 * (Math.log(1.0 / b2a3) / Math.log(2.0)) + b3a3 * (Math.log(1.0 / b3a3) / Math.log(2.0)));
                        Iab = HB - Hba;
                        if (Iab < -0.05) {
                            System.out.println(" ");
                        }
                        if (Iab > max) {
                            max = Iab;
                            mpa1 = pa1;
                            mpa2 = pa2;
                            mpa3 = pa3;
                        }
                    }
                    aux3 += 0.01;
                }
                aux2 += 0.01;
            }
            aux1 += 0.01;
            pa1 = Math.round(aux1 * 100.0) / 100.0;
        }

        tvA1.setVisibility(VISIBLE);
        tvA2.setVisibility(VISIBLE);
        tvA3.setVisibility(VISIBLE);
        tvInfo.setVisibility(VISIBLE);

        tvA1.setText("Probabilidad para la fuente A1: \n" + mpa1);
        tvA2.setText("Probabilidad para la fuente A2: \n" + mpa2);
        tvA3.setText("Probabilidad para la fuente A3: \n" + mpa3);
        tvInfo.setText("Información obtenida de las probabilidades: \n" + max);
    }

    @SuppressLint("SetTextI18n")
    private void calcularCapacidad2() {
        //Maximo de probabilidades p(a1), p(a2)
        double mpa1 = 0.0;
        double mpa2 = 0.0;
        //Entropia Maxima
        double maxHb = 0.0;
        double maxHba = 0.0;
        // Informacion
        double Iab = 0.0;
        // Probabilidades
        double pa1 = 0.0;
        double pa2 = 1.0;
        // Maximo del canal
        double max = 0.0;
        // Canales
        final double b1a1 = 0.8;
        final double b2a1 = 0.2;
        final double b1a2 = 0.4;
        final double b2a2 = 0.6;
        //Logarimos
        final double[] log2 = new double[4];
        final double[] log3 = new double[2];

        while (pa1 <= 1.0) {
            final double psb1 = pa1 * b1a1 + pa2 * b1a2;
            final double psb2 = pa1 * b2a1 + pa2 * b2a2;
            if (psb1 != 0.0) {
                log3[0] = Math.log(1.0 / psb1) / Math.log(2.0);
            } else {
                log3[0] = 0.0;
            }
            if (psb2 != 0.0) {
                log3[1] = Math.log(1.0 / psb2) / Math.log(2.0);
            } else {
                log3[1] = 0.0;
            }
            final double HB = psb1 * log3[0] + psb2 * log3[1];
            if (b1a1 != 0.0) {
                log2[0] = Math.log(1.0 / b1a1) / Math.log(2.0);
            } else {
                log2[0] = Math.log(2.0);
            }
            if (b2a1 != 0.0) {
                log2[1] = Math.log(1.0 / b2a1) / Math.log(2.0);
            } else {
                log2[1] = Math.log(2.0);
            }
            if (b2a1 != 0.0) {
                log2[2] = Math.log(1.0 / b1a2) / Math.log(2.0);
            } else {
                log2[2] = Math.log(2.0);
            }
            if (b2a2 != 0.0) {
                log2[3] = Math.log(1.0 / b2a2) / Math.log(2.0);
            } else {
                log2[3] = Math.log(2.0);
            }
            final double Hba = pa1 * (b1a1 * log2[0] + b2a1 * log2[1]) + pa2 * (b1a2 * log2[2] + b2a2 * log2[3]);
            Iab = HB - Hba;
            if (Iab > max) {
                max = Iab;
                mpa1 = pa1;
                mpa2 = pa2;
                maxHb = HB;
                maxHba = Hba;
            }
            pa1 += 1.0E-4;
            pa2 = 1.0 - pa1;
        }
        tvA1.setVisibility(VISIBLE);
        tvA2.setVisibility(VISIBLE);
        tvInfo.setVisibility(VISIBLE);
        tvA1.setText("Probabilidad para la fuente A1: \n" + mpa1);
        tvA2.setText("Probabilidad para la fuente A2: \n" + mpa2);
        tvInfo.setText("Información obtenida de las probabilidades: \n" + max);
    }

}
