package com.lobomarket.volleyjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MultipleViewer extends AppCompatActivity {

    /*
        Import Picasso anf Swipe refresh layout in gradle

        implementation 'com.squareup.picasso:picasso:2.71828'
        implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    */
    private int dataCount; //this dataCount will be used to determine the number of data parsed within the recyclerview
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter cAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;
    List<Users> user;
    private static String JSON_URL = "https://my-json-server.typicode.com/cubby02/Fake_API/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_viewer);

        recyclerView = findViewById(R.id.usersList);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        textView = findViewById(R.id.textView);
        user = new ArrayList<>();

        //check first if there's internet connection, if it's available then parse the data from url then load to recycler view
        //but if there's no connection, no data will be loaded
        refreshList();

        //use to refresh the layout when internet or new data is added
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });



    }

    private void refreshList() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setVisibility(View.GONE);
                        showUsers();
                        if(dataCount > 0){ //in order to avoid data redundancy when being refreshed this if statement will clear the current
                            clearData();   //list then reload the list
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //no internet
                        textView.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        Volley.newRequestQueue(MultipleViewer.this).add(stringRequest);

    }


    private void showUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                JSON_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject userObject = response.getJSONObject(i);

                                Users u = new Users();
                                u.setFname(userObject.getString("firstName"));
                                u.setLname(userObject.getString("lastName"));
                                u.setAge(userObject.getInt("age"));
                                u.setUserImg(userObject.getString("id_photo"));

                                user.add(u);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        cAdapter = new CustomAdapter(getApplicationContext(), user);
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(cAdapter);
                        dataCount = cAdapter.getItemCount();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "onResponse: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);

    }

    public void clearData() {
        user.clear(); // clear list
        cAdapter.notifyDataSetChanged(); // let your adapter know about the changes and reload view.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cAdapter);
    }
}