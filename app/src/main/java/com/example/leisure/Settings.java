package com.example.leisure;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;


public class Settings extends Fragment {

    private static final String TAG = "NICE";
    Button log,add;
    Switch aSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_settings, container, false);

        log=(Button)view.findViewById(R.id.logout);
        add=(Button)view.findViewById(R.id.add);
        aSwitch=(Switch)view.findViewById(R.id.switch1);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),Login.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment1= new Explore();
                String backStateName1 = fragment1.getClass().getName();

                FragmentManager manager1 = getActivity().getSupportFragmentManager();
                boolean fragmentPopped1 = manager1.popBackStackImmediate (backStateName1, 0);

                if (!fragmentPopped1){ //fragment not in back stack, create it.
                    FragmentTransaction ft = manager1.beginTransaction();
                    ft.replace(R.id.frame, fragment1);
                    ft.addToBackStack(backStateName1);
                    ft.commit();
                }

            }
        });


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Log.w(TAG,"TRY");
                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(getActivity(), R.drawable.bg);
                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                    DrawableCompat.setTint(wrappedDrawable, Color.BLUE);

                }else{
                    Log.w(TAG,"TRY 1");
                }
            }
        });

        return view;
    }
}
