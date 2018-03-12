package com.example.mohamedniyaz.volley;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class BackGroundSerice extends IntentService {

    SQLiteHelper sqLiteHelper;


    private static final String urlJsonObj = "https://test-api.nevaventures.com";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public BackGroundSerice() {
        super("com.example.mohamedniyaz.volley");
    }



    @Override
    protected void onHandleIntent(final Intent intent) {




        //creating a string request to send request to the url
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                System.out.println("Name : "+response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    System.out.println(jsonArray);

                    for(int i =0; i<jsonArray.length();i++){
                        String id ="";
                        String name = "";
                        String skills = "";
                        String image = "";
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        if(jsonObject.has("id")) {
                            id = jsonObject.getString("id");
                            System.out.println(id);
                        }
                        if(jsonObject.has("name")){

                            name = jsonObject.getString("name");

                        }
                        if(jsonObject.has("skills")){

                            skills = jsonObject.getString("skills");

                        }
                        if(jsonObject.has("image")){

                            image = jsonObject.getString("image");

                        }
                      //  hasList.add(new Data(id,name,skills,image));
                        try {
                            sqLiteHelper = new SQLiteHelper(getApplicationContext());
                            sqLiteHelper.insert(Integer.valueOf(id), name, skills, image);
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                        }
                    }


                    ArrayList<Data> hasList = (ArrayList<Data>) sqLiteHelper.getDetails();

                    //Sending broadcast to main activity
                    Intent intent1 = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("Array",hasList);
                    System.out.println("PAss"+hasList.toString());
                    intent1.putExtras( bundle);
                    intent1.setAction("ArrayList");
                    intent1.addCategory(Intent.CATEGORY_DEFAULT);
                    sendBroadcast(intent1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "SHOWING IN OFFLINE MODE", Toast.LENGTH_LONG).show();

            }
        });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(jsonObjReq);


    }


}
