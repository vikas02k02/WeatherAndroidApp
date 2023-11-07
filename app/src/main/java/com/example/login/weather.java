package com.example.login;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class weather extends AppCompatActivity {
    TextView resultview;
    EditText cityText, optionalText;
    Button getbtn;
    DecimalFormat df = new DecimalFormat("#.##");
    String api_key = "38436b39a00b043e62639d08451a6bf1";
    String api_url = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        cityText = findViewById(R.id.city);
        optionalText = findViewById(R.id.optional);
        getbtn = findViewById(R.id.getbtn);
        resultview=findViewById(R.id.textView2);


        getbtn.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        String url ;
        String city = cityText.getText().toString().trim();
        String country = optionalText.getText().toString().trim();
        if (city.equals("")) {
            cityText.setError("City can't be empty");
        } else {
            if (!country.equals("")) {
                url = api_url + "?q=" + city + "," + country + "&appid=" + api_key;
            } else {
                url = api_url + "?q=" + city + "&appid=" + api_key;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                Log.d("response", response);
                String output ="";
                try {
                    JSONObject jsonResponse =new JSONObject(response);
                    JSONArray jsonArray=jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather =jsonArray.getJSONObject(0);
                    String description =jsonObjectWeather.optString("description");
                    JSONObject jsonObjectMain =jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.optDouble("temp")-273.15;
                    double feelsLike =jsonObjectMain.optDouble("feels_like")-273.15;
                    float pressure =jsonObjectMain.optInt("pressure");
                    int humidity =jsonObjectMain.optInt("humidity");
                    JSONObject jsonObjectWind =jsonResponse.getJSONObject("wind");
                    String wind =jsonObjectWind.optString("speed");
                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.optString("all");
                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String CountryName =jsonObjectSys.optString("country");
                    resultview.setTextColor(Color.parseColor("#FF0000"));

                    output +=" Current weather of "+city+ " ("+ CountryName+")"
                            +"\n Temp: " + df.format(temp) + "\u2103"
                            +"\n Feels Like: " + df.format(feelsLike) + "\u2103"
                            +"\n Humidity: " + humidity + "%"
                            +"\n Description: " + description
                            +"\n Wind Speed: " + wind + "m/s"
                            +"\n Cloudlines: " + clouds + "%"
                            +"\n Pressure: " + pressure + "hpa";
                    Log.v("result",output);
                    resultview.setText(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, error -> Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show());
            RequestQueue requestQueue =Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}