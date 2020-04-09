package com.example.volley_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class main_page extends AppCompatActivity {

    String temper;
    String[] week={
            "Monday" , "Tuesday" ,"Wednesday" ,"Thursday" , "Friday" , "Saturday" , "Sunday"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String wether_string = "";
        Button btn_back = findViewById(R.id.btn_back);
        TextView[] day, min_temp, max_temp;

        day=new TextView[7];
        min_temp=new TextView[7];
        max_temp=new TextView[7];

        min_temp[0]=findViewById(R.id.min_temp_1);
        min_temp[1]=findViewById(R.id.min_temp_2);
        min_temp[2]=findViewById(R.id.min_temp_3);
        min_temp[3]=findViewById(R.id.min_temp_4);
        min_temp[4]=findViewById(R.id.min_temp_5);
        min_temp[5]=findViewById(R.id.min_temp_6);
        min_temp[6]=findViewById(R.id.min_temp_7);

        max_temp[0]=findViewById(R.id.max_temp_1);
        max_temp[1]=findViewById(R.id.max_temp_2);
        max_temp[2]=findViewById(R.id.max_temp_3);
        max_temp[3]=findViewById(R.id.max_temp_4);
        max_temp[4]=findViewById(R.id.max_temp_5);
        max_temp[5]=findViewById(R.id.max_temp_6);
        max_temp[6]=findViewById(R.id.max_temp_7);

        day[0]=findViewById(R.id.day_1);
        day[1]=findViewById(R.id.day_2);
        day[2]=findViewById(R.id.day_3);
        day[3]=findViewById(R.id.day_4);
        day[4]=findViewById(R.id.day_5);
        day[5]=findViewById(R.id.day_6);
        day[6]=findViewById(R.id.day_7);

        day[0].setText("today");

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        int index=0;
        for (int k=0;k<7;k++){
            if(dayOfTheWeek.equals(week[k])){
                index=k;
                break;
            }
        }
        for (int k=0;k<7;k++){
            day[k].setText(week[(index+k)%7]);
        }

        Log.i("day ", dayOfTheWeek);


        if (bundle != null) {
            wether_string = (String) bundle.get(MainActivity.weather_id);
        }
        String[] temperature = wether_string.split(" ");
        int n = temperature.length;

        int counter=0;

        for(int i=n-20;i<n;i=i+3){
            Log.i("tag",temperature[i]+" "+temperature[i+1]);
            max_temp[counter].setText(temperature[i]+"");
            min_temp[counter].setText(temperature[i+1]+"");
            counter++;
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_page.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
