package com.example.leisure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leisure.Intro.PrefManager;
import com.example.leisure.Intro.WelcomeActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "com.example.leisure";


    Pair[] pairs = new Pair[2];
    TextView title;
    ImageView image;

    private static final int REQUEST_LOCATION = 1;
    Double lat= Double.valueOf(0), longi= Double.valueOf(0);
    LocationManager locationManager;
    Location currentLocation;

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w(TAG,"FCG 1");

        PrefManager prefManager = new PrefManager(getApplicationContext());
        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        }



        Log.w(TAG,"FCG 12");

        title = (TextView) findViewById(R.id.titlename);
        pairs[0] = new Pair<View, String>(title, "leisure");

        image = (ImageView) findViewById(R.id.logo);
        pairs[1] = new Pair<View, String>(image, "leisure_image");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fetchlocation();



        Log.w(TAG,"FCG 14");




    }

    private void fetchlocation(){
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Log.w(TAG,"FCG 13");
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.w(TAG,"FCG");
                    currentLocation = location;

                    lat=currentLocation.getLatitude();
                    longi=currentLocation.getLongitude();
                    Toast.makeText(getApplicationContext(), "La= "+lat + "   " +"Lo="+ longi, Toast.LENGTH_SHORT).show();

                    Example.la = lat;
                    Example.lo = longi;


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, Login.class);

                            ActivityOptions options = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                            }
                            startActivity(intent, options.toBundle());
                            finish();
                        }
                    }, 3000);


                }
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchlocation();
                }
                break;
        }
    }


   

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        longi = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

class Example{

    public static double la;
    public static double lo;


}