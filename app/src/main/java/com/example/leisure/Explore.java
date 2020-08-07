package com.example.leisure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Explore extends Fragment {

EditText b,f,h;
    Button g;
    member memb;
    DatabaseReference reff;
    int i=3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_explore, container, false);

        b=(EditText)view.findViewById(R.id.name1);
        f=(EditText)view.findViewById(R.id.rate);
        h=(EditText)view.findViewById(R.id.locat);
        g=(Button)view.findViewById(R.id.confirm);
        reff= FirebaseDatabase.getInstance().getReference().child("Verify");

        memb=new member();
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                memb.setName(b.getText().toString());
                memb.setCity(f.getText().toString());
                memb.setLocation(h.getText().toString());

                reff.push().setValue(memb);
                i++;



            }
        });
        return view;
    }
}
