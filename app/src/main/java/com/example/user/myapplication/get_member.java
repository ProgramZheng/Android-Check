package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class get_member extends AsyncTask<String, Void, String> {
    private Context context;
    Spinner personal_data;
    LinearLayout member_data_layout;
    ArrayList<String> list,id_list;
    //flag 0 means get and 1 means post.(By default it is get.)
    public get_member(Context context, Spinner personal_data, LinearLayout member_data_layout) {
        this.context = context;
        this.personal_data=personal_data;
        this.member_data_layout=member_data_layout;
    }
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            String id = (String)arg0[0];

            String link="https://esz759486.000webhostapp.com/manage.php";
            String data  = URLEncoder.encode("id", "UTF-8") + "=" +
                    URLEncoder.encode(id, "UTF-8");
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
        list = new ArrayList<String>();
        id_list = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray member = new JSONArray(jsonObject.getString("member"));
            ArrayAdapter<String> adapter;
            for (int i=0;i<member.length();i++) {
                String member_name = member.getJSONObject(i).getString("name");
                list.add(member_name);
                String member_id = member.getJSONObject(i).getString("id");
                id_list.add(member_id);
                adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, list);
                personal_data.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        personal_data.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                new get_member_data(context,member_data_layout).execute(id_list.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }
}