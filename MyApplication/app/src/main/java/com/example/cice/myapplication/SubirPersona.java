package com.example.cice.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cice on 14/7/16.
 */
public class SubirPersona extends AsyncTask<Persona, Void, String>


{
    private Context context;

    public SubirPersona (Context context)
    {
        this.context = context;
    }

    @Override
    protected String doInBackground(Persona ... array_personas) {
        String resultado = "KO";
        Persona p = null;
        HttpURLConnection httpURLConnection = null;
        OutputStreamWriter outputStreamWriter = null;


        try
        {
            p = array_personas [0];

            URL url = new URL ("http://192.168.3.11:8080/CICERemote/SubirPersona");
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            OutputStream os = httpURLConnection.getOutputStream();
            //ObjectOutputStream
            outputStreamWriter = new OutputStreamWriter(os);

            Gson gson = new Gson();
            String mensaje_json = gson.toJson(p);

            outputStreamWriter.write(mensaje_json);
            outputStreamWriter.close();

            int code_resp = httpURLConnection.getResponseCode();

            if (code_resp == HttpURLConnection.HTTP_OK)
            {
                resultado = "OK";

            }

            httpURLConnection.disconnect();



        } catch (Throwable t)
        {
            Log.e(getClass().getCanonicalName(), "HA ido mal la com con el server", t);
            resultado = "KO";
        }

        finally//haya habido o no excepción, te metes por aquí, para liberar recursos
        {
            if (null != outputStreamWriter)
            {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            httpURLConnection.disconnect();

        }





        return resultado;
    }

    @Override
    protected void onPostExecute(String s) {
        MainActivity ma = (MainActivity)this.context;
        ma.mostrarPersona();

    }
}
