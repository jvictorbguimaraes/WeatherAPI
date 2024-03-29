package com.example.api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    ImageView currentImage, imageForecast1, imageForecast2, imageForecast3;
    EditText city, country;
    TextView current,forecast1,forecast2,forecast3, currentTemp, temp1, temp2, temp3;

    Gson gson = new Gson();
    WeatherList object;
    String mainUrl = "https://api.weatherbit.io/v2.0/";
    String apiKey = "&key=2bf859166968461192d0ac8475a4b806";
    String iconUrl = "https://www.weatherbit.io/static/img/icons/";
    String params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        current = findViewById(R.id.current);
        forecast1 = findViewById(R.id.forecast1);
        forecast2 = findViewById(R.id.forecast2);
        forecast3 = findViewById(R.id.forecast3);
        currentImage = findViewById(R.id.image);

        imageForecast1 = findViewById(R.id.imageForecast1);
        imageForecast2 = findViewById(R.id.imageForecast2);
        imageForecast3 = findViewById(R.id.imageForecast3);

//        if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
//        {
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String [] { android.Manifest.permission.ACCESS_FINE_LOCATION },
//                    1
//            );
//        }
//        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public void getWeather(View view){

        params = "current?city=" + city.getText().toString() + "&country=" + country.getText().toString();
        getCurrentWeather();
        params = "forecast/daily?city=" + city.getText().toString() + "&country=" + country.getText().toString();
        getForecast();
    }

    public void getCurrentWeather() {
        AsyncTask async = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    String apiUrl = mainUrl + params + apiKey;
                    String line = httpGet(apiUrl);
                    object = gson.fromJson(line, WeatherList.class);
                }catch (Exception e){
                }
                return  null;
            }

            @Override
            protected void onPostExecute(Object o) {
                try {
                    if(object != null) {
                        current.setText(object.data.get(0).weather.description);
                        //currentTemp.setText(object.data.get(0).temp + "°");
                        Picasso.get().load(iconUrl + object.data.get(0).weather.icon + ".png").into(currentImage);
                    }else{
                        Toast.makeText(getApplicationContext(), "City not found",Toast.LENGTH_LONG);
                    }
                }catch (Exception e){
                }
            }
        }.execute();
    }

    public void getForecast() {
        AsyncTask async = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    String apiUrl = mainUrl + params + apiKey;
                    String line = httpGet(apiUrl);
                    object = gson.fromJson(line, WeatherList.class);
                }catch (Exception e){
                }
                return  null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if(object != null) {
                    forecast1.setText(object.data.get(1).weather.description);
                    //temp1.setText(object.data.get(1).temp + "°");
                    Picasso.get().load(iconUrl + object.data.get(1).weather.icon + ".png").into(imageForecast1);
                    forecast2.setText(object.data.get(2).weather.description);
                    //temp2.setText(object.data.get(2).temp + "°");
                    Picasso.get().load(iconUrl + object.data.get(2).weather.icon + ".png").into(imageForecast2);
                    forecast3.setText(object.data.get(3).weather.description);
                    //temp3.setText(object.data.get(3).temp + "°");
                    Picasso.get().load(iconUrl + object.data.get(3).weather.icon + ".png").into(imageForecast3);
                }else{
                    Toast.makeText(getApplicationContext(), "City not found",Toast.LENGTH_LONG);
                }
            }
        }.execute();
    }

    public String httpGet(String getUrl){
        try {
            URL url = new URL(getUrl);
            HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");

            InputStream inputStream = httpConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.readLine();
        }catch (Exception ex) {
        }
        return null;
    }
}
