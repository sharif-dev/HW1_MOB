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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private  static final String[] COUNTRIES=new String[]{
            "afghanestan","Albania","Algebra","Andorra","Angola"

    };

    getWeather getWeather_=new getWeather();
    Handler handler= new threadHandler1();
    String weather_status;


    public static final String TAG = "Main";
    TextView showLatAndLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_getLatAndLng = findViewById(R.id.getLatAndLng);
        showLatAndLng =findViewById(R.id.showLatAndLng);

        final AutoCompleteTextView editText=findViewById(R.id.actv);

        final String[] input_ = new String[1];

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,COUNTRIES);
        editText.setAdapter(adapter);


        Log.i(TAG, "pid = " + android.os.Process.myPid() + " tid = "+ android.os.Process.myTid() + " id = " + Thread.currentThread().getId());

        btn_getLatAndLng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_[0] =editText.getText().toString();
                new LatitudeAndLongitude(input_[0] , getApplicationContext() , new threadHandler() ).start();

            }
        });
    }

    private class threadHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            showLatAndLng.setText("waiting for response");
            String LatAndLng;
            if (message.what == 1)
//                    Bundle bundle = message.getData();
                    LatAndLng = message.getData().getString("result");
            else
                    LatAndLng = null;
            Log.i(TAG, "handleMessage: "+LatAndLng);

           String[] LatAndLng_=LatAndLng.split("_");
//
           Log.i(TAG, "handleMessage: "+LatAndLng_[0]+" "+LatAndLng_[1]);
           getWeather_.getWeatherstatus(LatAndLng_[0],LatAndLng_[1],getApplicationContext(),handler);
        }
    }

    private class threadHandler1 extends Handler {
        private static final String TAG = "threadHandler";


        @Override
        public void handleMessage(Message message) {


            Log.d(TAG, "handleMessageid: "+android.os.Process.myTid());

            Log.d(TAG, "handleMessage: ");
            Bundle bundle = message.getData();
            weather_status= bundle.getString("result");
            Log.d(TAG, "handleMessage: "+weather_status);


        }
    }


}
