package com.example.leisure;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;


public class MapsInfo extends Fragment implements OnMapReadyCallback {


    GoogleMap map;
    TextView name,location,price,max;
    TextView a,b;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_maps_info, container, false);

        name=view.findViewById(R.id.place1);
        location=view.findViewById(R.id.placeloc1);
        price=view.findViewById(R.id.price1);
        max=view.findViewById(R.id.max11);
        a=view.findViewById(R.id.showLoc);
        b=view.findViewById(R.id.showLocList);

        SupportMapFragment mapFragment= ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_info));
        mapFragment.getMapAsync(this);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", locationOf.lat, locationOf.longi);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getActivity().startActivity(intent);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationCheck.loc=locationOfInfo.location;

                Fragment fragment1= new Location();
                String backStateName1 = fragment1.getClass().getName();

                Home list1=(Home) view.getContext();

                FragmentManager manager1 =list1.getSupportFragmentManager();
                boolean fragmentPopped1 = manager1.popBackStackImmediate (backStateName1, 0);

                if (!fragmentPopped1){ //fragment not in back stack, create it.
                    FragmentTransaction ft = manager1.beginTransaction();
                    ft.replace(R.id.frame, fragment1);
                    ft.addToBackStack(backStateName1);
                    ft.commit();
                }
            }
        });

     return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;

        LatLng latLng=new LatLng(locationOfInfo.lat,locationOfInfo.longi);
        map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }
}