package com.example.volley_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Main";
    TextView showLatAndLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_getLatAndLng = findViewById(R.id.getLatAndLng);
        showLatAndLng =findViewById(R.id.showLatAndLng);


        Log.i(TAG, "pid = " + android.os.Process.myPid() + " tid = "+ android.os.Process.myTid() + " id = " + Thread.currentThread().getId());

        btn_getLatAndLng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LatitudeAndLongitude("" , getApplicationContext() , new threadHandler() ).start();

            }
        });
    }

    private class threadHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String LatAndLng;
            if (message.what == 1)
//                    Bundle bundle = message.getData();
                    LatAndLng = message.getData().getString("result");
            else
                    LatAndLng = null;

            showLatAndLng.setText(LatAndLng);
        }
    }


}
