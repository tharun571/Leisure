package com.example.leisure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leisure.Room.check;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {

    private static final String TAG = "QWERTYU";
    Button b;
    FirebaseAuth.AuthStateListener firebaseAuth;
    FirebaseAuth mfirebaseAuth;

    TextView text;
    List<Address> addresses;
    Geocoder geocoder;

    Double lat,longi;
    LocationManager locationManager;

    private static final int REQUEST_LOCATION = 1;
    String address;

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int bool=0;


        lat=Example.la;
        longi=Example.lo;
        GPS();

        check.add=false;







        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        if(savedInstanceState==null){
            bottomNavigationView.setSelectedItemId(R.id.action_recents);
            Fragment fragment= new MainFrag();


            FragmentManager manager = getSupportFragmentManager();



                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.frame, fragment);
                ft.commit();

        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_recents:

                        Fragment fragment= new MainFrag();
                        String backStateName = fragment.getClass().getName();

                        FragmentManager manager = getSupportFragmentManager();
                        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

                        if (!fragmentPopped){ //fragment not in back stack, create it.
                            FragmentTransaction ft = manager.beginTransaction();
                            ft.replace(R.id.frame, fragment,"MAIN");
                            ft.addToBackStack(backStateName);
                            ft.commit();
                        }


                        break;

                    case R.id.action_nearby:

                        Fragment fragment1= new Settings();
                        String backStateName1 = fragment1.getClass().getName();

                        FragmentManager manager1 = getSupportFragmentManager();
                        boolean fragmentPopped1 = manager1.popBackStackImmediate (backStateName1, 0);

                        if (!fragmentPopped1){ //fragment not in back stack, create it.
                            FragmentTransaction ft = manager1.beginTransaction();
                            ft.replace(R.id.frame, fragment1,"FAV");
                            ft.addToBackStack(backStateName1);
                            ft.commit();
                        }
                        break;
                    case R.id.fav:

                        Fragment fragment11= new Favourites();
                        String backStateName11 = fragment11.getClass().getName();

                        FragmentManager manager11 = getSupportFragmentManager();
                        boolean fragmentPopped11 = manager11.popBackStackImmediate (backStateName11, 0);

                        if (!fragmentPopped11){ //fragment not in back stack, create it.
                            FragmentTransaction ft = manager11.beginTransaction();
                            ft.replace(R.id.frame, fragment11,"SETTINGS");


                            ft.addToBackStack(backStateName11);
                            ft.commit();
                        }
                        break;
                }


                return true;
            }
        });


        layout=(LinearLayout)findViewById(R.id.toploc);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationOf.lat=Example.la;
                locationOf.longi=Example.lo;
                startActivity(new Intent(Home.this,Map.class));
            }
        });




    }

    private void GPS(){


        if(lat!=null) {


            text = (TextView) findViewById(R.id.textView1);
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(lat, longi, 1);

                address = addresses.get(0).getAddressLine(0);
                String area = addresses.get(0).getLocality();
                String city = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String code = addresses.get(0).getPostalCode();

                String fullAddress = address;

                Log.w(TAG, "CXS " + ", " + address + " ;;");
                if (fullAddress != null) {
                    text.setText(fullAddress);
                } else {
                    Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
