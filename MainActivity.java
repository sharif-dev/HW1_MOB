package com.example.volley_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    getWeather getWeather_ = new getWeather();
    Handler handler = new threadHandler1();
    String weather_status;
    ProgressBar waiting;
    static final String weather_id = "weather_id";

//    private  static final String[] con={
//            "iran","iran2"
//    };

    public static final String TAG = "Main";
    TextView showLatAndLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_getLatAndLng = findViewById(R.id.nextpage);
        waiting = findViewById(R.id.progressBar);
        waiting.setVisibility(View.GONE);
        final EditText inp = findViewById(R.id.plain_text_input);
        //showLatAndLng = findViewById(R.id.showLatAndLng);

        //final String input_ = new String[1];


        //Log.i(TAG, "pid = " + android.os.Process.myPid() + " tid = " + android.os.Process.myTid() + " id = " + Thread.currentThread().getId());

        btn_getLatAndLng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //test
                //input_[0] =menu.getText().toString();
                //input_[0] = "paris";
                waiting.setVisibility(v.VISIBLE);

                new LatitudeAndLongitude(inp.getText().toString(), getApplicationContext(), new threadHandler()).start();


            }
        });
    }

    private class threadHandler extends Handler {
        //final AutoCompleteTextView menu = findViewById(R.id.auto_com);
        final ListView listview = (ListView) findViewById(R.id.list_v);
        ArrayAdapter<String> adapter;

        @Override
        public void handleMessage(Message message) {
            //showLatAndLng.setText("waiting for response");
            String LatAndLng;
            if (message.what == 1)
//                    Bundle bundle = message.getData();
                LatAndLng = message.getData().getString("result");
            else
                LatAndLng = null;
            Log.i(TAG, "handleMessage here: " + LatAndLng);

            String[] LatAndLng_ = LatAndLng.split("\n", 0);
            int number_of_cities;
            number_of_cities = LatAndLng_.length;
            String[] CITIES = new String[number_of_cities];
            final String[][] coordinate = new String[number_of_cities][2];
            for (int i = 0; i < number_of_cities; i++) {
                String[] tem = LatAndLng_[i].split(" ");
                int n = tem.length;
                coordinate[i][0] = (tem[n - 2]);
                coordinate[i][1] = (tem[n - 1]);
                CITIES[i] = tem[0];
                for (int j = 1; j < n - 2; j++) {
                    //Log.i(TAG,CITIES[i]+" + "+ tem[j]);
                    CITIES[i] = CITIES[i] + tem[j];
                }
                Log.i(TAG, i + "city: " + CITIES[i] + " coordinate : " + coordinate[i][0] + coordinate[i][1]);
                //Log.i(TAG,n+" "+ tem[n-2]);
            }
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, CITIES);
            Log.i(TAG, adapter.getItem(0));
            waiting.setVisibility(View.GONE);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    waiting.setVisibility(View.VISIBLE);
                    getWeather_.getWeatherstatus(coordinate[position][0], coordinate[position][1], getApplicationContext(), handler);
                    //Log.i(TAG,"selecetd:  "+adapter.getItem(position));
                }
            });
//
            //
        }
    }

    private class threadHandler1 extends Handler {
        private static final String TAG = "threadHandler";

        @Override
        public void handleMessage(Message message) {


            //Log.d(TAG, "handleMessageid: "+android.os.Process.myTid());

            Log.d(TAG, "handleMessage: ");
            Bundle bundle = message.getData();
            weather_status = bundle.getString("result");
            Log.d(TAG, "handleMessage weather: " + weather_status);
            waiting.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this, main_page.class);
            intent.putExtra(weather_id, weather_status);
            startActivity(intent);


        }
    }


}
