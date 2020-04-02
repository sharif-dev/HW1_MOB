package com.example.volley_ex;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getWeather {

    private static final String TAG = "getWeather";

    public  void getWeatherstatus(final String longitude, final String latitude, final Context context, final Handler handler) {
        Thread thread = new Thread() {

            public void run() {
                String server_url="https://api.weatherapi.com/v1/forecast.json?q="+latitude+","+longitude+"&key=1dcc0a5f5df54b03863131439200104%20&days=3";
                RequestQueue requestQueue;
                requestQueue= Volley.newRequestQueue(context);
                {
                    JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, server_url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject location=response.getJSONObject("location");
                                String region=location.getString("region");
                                String country=location.getString("country");

                                JSONObject current_weather=response.getJSONObject("current");
                                String temp_c=current_weather.getString("temp_c");

                                JSONObject current_weather_condition=current_weather.getJSONObject("condition");

                                String text=current_weather_condition.getString("text");

                                JSONObject forecast=response.getJSONObject("forecast");

                                JSONArray forecastday=forecast.getJSONArray("forecastday");

                                String date[]=new String[3];
                                String maxtemp_c[]=new String[3];
                                String mintemp_c[]=new String[3];
                                String avgtemp_c[]=new String[3];

                                for (int i = 0; i < forecastday.length(); i++) {

                                    JSONObject days  = forecastday.getJSONObject(i);
                                    date[i]=days.getString("date");
                                    JSONObject day=days.getJSONObject("day");
                                    maxtemp_c[i]=day.getString("maxtemp_c");
                                    mintemp_c[i]=day.getString("mintemp_c");
                                    avgtemp_c[i]=day.getString("avgtemp_c");


                                }

                                Message message = Message.obtain();
                                message.setTarget(handler);
                                message.what = 1;
                                Bundle bundle = new Bundle();
                                String status=region+" "+country+" "+temp_c+" "+text+" "+maxtemp_c[0]+" "+mintemp_c[0]+" "+avgtemp_c[0]+" "+maxtemp_c[1]+ " "+mintemp_c[1]+" "+avgtemp_c[1]+" "+maxtemp_c[2]+" "+mintemp_c[2]+" "+avgtemp_c[2];
                                bundle.putString("result",status);
                                message.setData(bundle);
                                message.sendToTarget();
                                Log.i(TAG, "response "+status+" "+android.os.Process.myTid());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();

                        }
                    });

                    requestQueue.add(request);




                }

            }
        };
        thread.start();
    }
}
