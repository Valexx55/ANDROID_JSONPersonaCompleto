package com.example.cice.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cice on 14/7/16.
 */
public class ObtenerListaPersonas extends AsyncTask <Void, Void, List<Persona>> {

    private Context context;

    public ObtenerListaPersonas (Context context)
    {
        this.context = context;
    }

    @Override
    protected List<Persona> doInBackground(Void... params) {
        List<Persona> l_personas = null;
        HttpURLConnection httpURLConnection = null;



        try
        {

            URL url = new URL ("http://192.168.3.11:8080/CICERemote/SubirPersona");
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");


            int code_resp = httpURLConnection.getResponseCode();

            if (code_resp == HttpURLConnection.HTTP_OK)
            {
                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                String json_personas = br.readLine();

                Gson gson = new Gson();

                l_personas = gson.fromJson (json_personas, new TypeToken<ArrayList<Persona>>(){}.getType());


            }

            httpURLConnection.disconnect();



        } catch (Throwable t)
        {
            Log.e(getClass().getCanonicalName(), "HA ido mal la com con el server", t);

        }

        finally//haya habido o no excepción, te metes por aquí, para liberar recursos
        {


            httpURLConnection.disconnect();

        }



        return  l_personas;
    }

    @Override
    protected void onPostExecute(List<Persona> personas) {
        MainActivity mainActivity = (MainActivity)this.context;
        mainActivity.mostrarListaPersonas(personas);
    }
}
