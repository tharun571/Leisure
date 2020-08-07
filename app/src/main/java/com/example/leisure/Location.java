package com.example.leisure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.leisure.Room.Database1;
import com.example.leisure.Room.User;
import com.example.leisure.Room.check;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Location extends Fragment {

    private static final String TAG = "ASDF";
    DatabaseReference databaseReference;
    ArrayList<String> name=new ArrayList<>();

    ArrayList<String> loca=new ArrayList<>();

    ArrayList<Double> la=new ArrayList<>();
    ArrayList<Double> lo=new ArrayList<>();

    ArrayList<Integer> rate=new ArrayList<>();
    ArrayList<Integer> max=new ArrayList<>();


    String mem;

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;



    LoadingDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_location, container, false);

        dialog=new LoadingDialog(getActivity());


        name.clear();
        loca.clear();
        la.clear();
        lo.clear();
        rate.clear();
        max.clear();

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler12);

        dialog.startLoading();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Entertainment");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){





                    Log.w(TAG,"XZD "+LocationCheck.loc);
                        if (dataSnapshot1.child("location").getValue().toString().matches(LocationCheck.loc)) {

                            mem = dataSnapshot1.child("name").getValue().toString();
                            name.add(mem);
                            mem = dataSnapshot1.child("location").getValue().toString();
                            loca.add(mem);
                            la.add(Double.parseDouble(dataSnapshot1.child("lat").getValue().toString()));
                            lo.add(Double.parseDouble(dataSnapshot1.child("long").getValue().toString()));

                            rate.add(Integer.parseInt(dataSnapshot1.child("rate").getValue().toString()));
                            max.add(Integer.parseInt(dataSnapshot1.child("max").getValue().toString()));
                        }




                }
                dialog.stopLoading();

                if(name.size()!=0) {

                    recyclerView.setHasFixedSize(true);
                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    mAdapter = new ListAdapter(getActivity());
                    mAdapter.add(name, loca, la, lo, rate, max);

                    recyclerView.setAdapter(mAdapter);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


            check.add=true;

            String a=mAdapter.getPos(viewHolder.getAdapterPosition());

            int p=viewHolder.getAdapterPosition();
            User user=new User(a,loca.get(p), ""+rate.get(p),""+max.get(p),""+la.get(p),""+lo.get(p));

            Database1 database1=Database1.getInstance(getActivity());

            database1.dao1().insert(user);





        }
    };
}
