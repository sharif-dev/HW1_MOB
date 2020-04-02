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



public class LatitudeAndLongitude extends Thread{

    private String cityChosenByUser;
    private Context context;
    private Handler handler;
    LatitudeAndLongitude(String cityChosenByUser, final Context context, final Handler handler){
        this.cityChosenByUser = cityChosenByUser;
        this.context = context;
        this.handler = handler;
    }
    @Override
    public void run() {
        final String[] latitude = {null};
        final String[] longitude = {null};
        final String[] result = {""};
        String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/"+cityChosenByUser+".json?access_token=pk.eyJ1IjoibWFyeWFtdnQiLCJhIjoiY2s3eXY4MjJiMDhsbzNocGRxNWVsbTVkNyJ9.GteApMhSR504o_lSSgzIEQ";
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest mStringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("features");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject feature = jsonArray.getJSONObject(i);
                        JSONObject geometry = feature.getJSONObject("geometry");
                        JSONArray coordinates = geometry.getJSONArray("coordinates");
                        latitude[0] = coordinates.getString(0);
                        longitude[0] = coordinates.getString(1);
                        break;


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (latitude[0] != null && longitude[0] != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result[0] =latitude[0]+"_"+longitude[0];
                        bundle.putString("result", result[0]);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result[0] = "Unable to get Latitude and Longitude for this address location.";
                        bundle.putString("result", result[0]);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(MainActivity.TAG, "error = " + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
        Log.i(MainActivity.TAG, "   pid = " + android.os.Process.myPid() + " tid = " + android.os.Process.myTid() + " id = " + Thread.currentThread().getId());

    }
}



