package com.lobomarket.volleyjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    /*
        Volley Library and Json Library
        import to build.gradle(:app:)
            implementation 'com.android.volley:volley:1.1.0'
            implementation 'com.google.code.gson:gson:2.4'
    */

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.test);

        //the api url where you should download the data
        String url = "https://api.apify.com/v2/key-value-stores/lFItbkoNDXKeSWBBA/records/LATEST?disableRedirect=true";

        //RequestQueue class is used to get the JSon object request and will show the result
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //StringRequest will be used to check if there's internet connection and it will handle the request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JsonObjectRequest will get the response (or result, i dunno what term i should use)
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.GET,
                                url,
                                null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            //JSONObject will get the response
                                            JSONObject jsonObject = new JSONObject(response.toString());

                                            //Calls the Test constructor class
                                            Test t = new Test();

                                            //Obtaining the column name or the name of every data fromURL
                                            t.setInfected(jsonObject.getLong("infected"));
                                            t.setTested(jsonObject.getLong("tested"));

                                            //showing the result
                                            textView.setText("Infected: " + String.valueOf(t.getInfected()));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.v("ERROR", error.toString());
                                    }
                                }
                        );

                        requestQueue.add(jsonObjectRequest);
                    }

                }, new Response.ErrorListener() {
            //this will show the error message which there is no connection
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("No Internet Connection");
            }
        });

        Button btn = findViewById(R.id.button);

        //this button will show the final result determining if there's internet connection or none.
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Volley.newRequestQueue(MainActivity.this).add(stringRequest);
            }
        });

    }

    public void next(View v){
        Intent next = new Intent(MainActivity.this, MultipleViewer.class);
        startActivity(next);
    }
}