package com.dario.carrizo.teoriainformacion.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dario.carrizo.teoriainformacion.Commons.PersonaFija;
import com.dario.carrizo.teoriainformacion.Commons.PersonaVariable;
import com.dario.carrizo.teoriainformacion.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VariableFijoFragment extends Fragment {

    private List<PersonaVariable> p;

    private List<PersonaFija> pf;

    public VariableFijoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        p = new ArrayList<>();
        pf = new ArrayList<>();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_variable_fijo, container, false);
    }

}
