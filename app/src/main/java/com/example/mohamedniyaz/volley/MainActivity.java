package com.example.mohamedniyaz.volley;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private MyBroadcastReciever mybroadcastReceiver;

    RecyclerView recyclerView;

    ProgressBar progressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intent = new Intent(MainActivity.this, BackGroundSerice.class);
        startService(intent);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        mybroadcastReceiver = new MyBroadcastReciever();


        IntentFilter intentFilter = new IntentFilter("ArrayList");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mybroadcastReceiver, intentFilter);

        SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
        sqLiteHelper.getWritableDatabase();

        final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connMgr != null) {
            activeNetworkInfo = connMgr.getActiveNetworkInfo();
        }
        if(activeNetworkInfo == null) {
            ArrayList<Data> hasList = (ArrayList<Data>) sqLiteHelper.getDetails();
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, hasList);
            recyclerView.setAdapter(recyclerAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        }




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mybroadcastReceiver);
    }



    public class MyBroadcastReciever extends BroadcastReceiver {

        ArrayList<Data> arrayList = new ArrayList<>();

        public MyBroadcastReciever() {

            super();

        }

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("Recive", "onReceive: ");


            arrayList = intent.getParcelableArrayListExtra("Array");


            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(context,arrayList);
            recyclerView.setAdapter(recyclerAdapter);

            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}

