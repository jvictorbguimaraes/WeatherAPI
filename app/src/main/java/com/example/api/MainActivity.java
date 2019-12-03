package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    Gson gson = new Gson();
    String mainUrl = "https://api.weatherbit.io/v2.0/current?";
    String apiKey = "&key=2bf859166968461192d0ac8475a4b806";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        //{
        //    ActivityCompat.requestPermissions(
        //            this,
        //            new String [] { android.Manifest.permission.ACCESS_FINE_LOCATION },
        //            1
        //    );
        //}
        //LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public void test(View view){
        try{
            httpGet();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void httpGet() {

        AsyncTask async = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    String apiUrl = mainUrl + "city_id=4487042" + apiKey;
                    URL url = new URL(apiUrl);
                    HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
                    httpConn.setRequestMethod("GET");
                    InputStream inputStream = httpConn.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = bufferedReader.readLine();
                    WeatherList object = gson.fromJson(line, WeatherList.class);
                }catch (Exception e){
                }
                return  null;
            }

            @Override
            protected void onPostExecute(Object o) {

            }
        }.execute();

    }
}
