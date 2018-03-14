package com.example.mgiorda.mypizzeria2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Martin on 21/02/2018.
 */

public class EnvoiCommande extends AsyncTask<String, String, String > {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader listen;
    private String host;
    private int port;
    private String s;
    private String receive;
    private Context c;
    private String TAG = "Commande";

    public EnvoiCommande(String host, int port, Context c){
        this.host = host;
        this.port = port;
        this.c = c;
    }


    @Override
    protected String doInBackground(String... params) {

            try {
                socket = new Socket(host, port);
            }
            catch (IOException e) {

            }

            //c = (new MainActivity()).getContext();

            try {
                writer = new PrintWriter(socket.getOutputStream(), true);
                listen = new BufferedReader(new InputStreamReader(socket.getInputStream()),10);
            }
            catch (IOException e) {

            }

            //Communication avec le serveur
            s = params[0];
            writer.println(s);
        try {
            String s;
            while ((s = listen.readLine()) != null) {
                publishProgress(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String v) {
        super.onPostExecute(v);

    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.d(TAG, "message : "+values[0]);
        if (values[0] != null && !values[0].equals("")) {
            Toast.makeText(c, values[0], Toast.LENGTH_LONG).show();
        }

    }
}
