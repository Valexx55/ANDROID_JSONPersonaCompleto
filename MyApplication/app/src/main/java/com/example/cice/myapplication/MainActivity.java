package com.example.cice.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int npersonas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        npersonas = 0;

        Persona p = new Persona("Fausto", 42);
        Persona p2 = new Persona("Carlos", 15);

        SubirPersona sp = new SubirPersona(this);
        SubirPersona sp2 = new SubirPersona(this);

        sp.execute(p);
        sp2.execute(p2);

        /**
         * todo lo que haga después de la lamada estará siendo en paralelo
         * con la ejecución del SubirPersona
         */


    }

    public void mostrarPersona ()
    {
        Log.d(getClass().getCanonicalName(), "Ha terminado de subir la persona");
        npersonas++;

        if (npersonas==2)
        {
            new ObtenerListaPersonas(this).execute();
        }
    }

    public void mostrarListaPersonas (List<Persona> lpersonas)
    {
        Log.d(getClass().getCanonicalName(), "He recibido la lista de personas");

        for (Persona p : lpersonas)
        {
           Log.d(getClass().getCanonicalName(), "Nombre = " + p.getNombre());
        }


    }


}
