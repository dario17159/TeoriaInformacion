package com.dario.carrizo.teoriainformacion.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.dario.carrizo.teoriainformacion.Fragments.BMPFragment;
import com.dario.carrizo.teoriainformacion.Fragments.CadenasSimilaresFragment;
import com.dario.carrizo.teoriainformacion.Fragments.CanalesFragment;
import com.dario.carrizo.teoriainformacion.Fragments.ClientFragment;
import com.dario.carrizo.teoriainformacion.Fragments.CuitCuilFragment;
import com.dario.carrizo.teoriainformacion.Fragments.EntropiaRedundanciaFragment;
import com.dario.carrizo.teoriainformacion.Fragments.WavFragment;
import com.dario.carrizo.teoriainformacion.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    //Declaracion de Variables
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    /**
    *  funcion onCreate -> Parte del ciclo de vida de una aplicacion android, en ella se llevan a cabo
     *  las configuraciones correspondientes al momento de crear la Activity que sera visible al usuario
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configuracion del toolbar
        setToolbar();

        //Relacion de las variables definidas, respecto al diseño
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);
        // Establece el fragment por default que se mostrara al cargar la app
        setFragmentByDefault();
        // Metodo que permite la manipulacion de los click sobre un elemento del menu desplegable
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                boolean fragmentTransaction = false;
                Fragment fragment = null;

                //Uso de swich para administrar el fragmen que se lanzara a partir del id del item seleccionado
                switch (menuItem.getItemId()){
                    case R.id.menu_wav:
                        fragment = new WavFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_bmp:
                        fragment = new BMPFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_entropia_redundancia:
                        fragment = new EntropiaRedundanciaFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_transmission:
                        fragment = new CanalesFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_compare:
                        fragment = new CadenasSimilaresFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_cuil_cuit:
                        fragment = new CuitCuilFragment();
                        fragmentTransaction=true;
                        break;
                    case R.id.menu_server:
                        fragment = new ClientFragment();
                        fragmentTransaction = true;
                }

                if(fragmentTransaction){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame,fragment)
                            .commit();
                    menuItem.setChecked(true);
                    getSupportActionBar().setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();
                }

                return true;
            }
        });
    }

    private void setFragmentByDefault(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame,new WavFragment())
                .commit();
        MenuItem item = navigationView.getMenu().getItem(0);
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * onOptionItemSelected nos pemite capurar el click ocacionado en el icono hammer permitiendo
     * desplegar el menu lateral y hacer visible las opciones
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(drawerLayout.getWindowToken(), 0);
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
