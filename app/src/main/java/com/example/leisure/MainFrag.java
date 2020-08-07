package com.example.leisure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class MainFrag extends Fragment {


    private static final String TAG = "lpoiklo";
    EditText ppl,budg,dis;
    Button done;
    Switch date;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_main, container, false);



        ppl=(EditText)view.findViewById(R.id.ppl);
        budg=(EditText)view.findViewById(R.id.budg);
        dis=(EditText)view.findViewById(R.id.dist);
        done=(Button)view.findViewById(R.id.don);
        date=(Switch)view.findViewById(R.id.dateswitch);

        Checkdate.date1=  false;

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            Checkdate.date1=true;

                            // The toggle is enabled
                        }
                    }
                });



                Log.w(TAG,"LKI "+ppl.getText().toString());
                if(ppl.getText().toString().matches("")|budg.getText().toString().matches("")|dis.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();

                }
                else if(ppl.getText().toString().isEmpty()|budg.getText().toString().isEmpty()|dis.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int p=Integer.parseInt(ppl.getText().toString());
                    int b=Integer.parseInt(budg.getText().toString());
                    int d=Integer.parseInt(dis.getText().toString());

                    if(p<2|p>15){

                        Toast.makeText(getActivity(), "Min People:2, Max People:15", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    Exa.a=p;
                    Exa.b=b;
                    Exa.c=d;

                    Fragment fragment1= new List();
                    Fragment fragment2= new List();
                    String backStateName1 = fragment1.getClass().getName();

                    FragmentManager manager1 = getActivity().getSupportFragmentManager();
                    boolean fragmentPopped1 = manager1.popBackStackImmediate (backStateName1, 0);

                    if (!fragmentPopped1) { //fragment not in back stack, create it.
                        FragmentTransaction ft = manager1.beginTransaction();
                        ft.replace(R.id.frame, fragment1);
                        ft.addToBackStack(backStateName1);
                        ft.commit();
                    }
                    }
                }
            }
        });







        return view;
    }

}
