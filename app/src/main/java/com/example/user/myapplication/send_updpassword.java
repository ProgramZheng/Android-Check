package com.example.user.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class send_updpassword extends AsyncTask<String, Void, String> {
    private Context context;

    //flag 0 means get and 1 means post.(By default it is get.)
    public send_updpassword(Context context) {
        this.context = context;
    }
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
            try{
                String id = (String)arg0[0];
                String get_old_password = (String)arg0[1];
                String get_new_password = (String)arg0[2];
                String get_new_password_check = (String)arg0[3];

                String link="https://esz759486.000webhostapp.com/updpassword.php";
                String data  = URLEncoder.encode("id", "UTF-8") + "=" +
                        URLEncoder.encode(id, "UTF-8");
                data += "&" + URLEncoder.encode("old_password", "UTF-8") + "=" +
                        URLEncoder.encode(get_old_password, "UTF-8");
                data += "&" + URLEncoder.encode("new_password", "UTF-8") + "=" +
                        URLEncoder.encode(get_new_password, "UTF-8");
                data += "&" + URLEncoder.encode("new_password_check", "UTF-8") + "=" +
                        URLEncoder.encode(get_new_password_check, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
    }

    @Override
    protected void onPostExecute(String result){
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
    }
}