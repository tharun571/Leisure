package com.example.leisure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class List extends Fragment {

    private static final String TAG = "qwerty";
    double  dist;

    DatabaseReference reff;

    public ArrayList<String> dataset;
    public ArrayList<member> memberArrayList;
    private void algo(double lon1,double lon2,double lat1,double lat2){





                    lon1 = Math.toRadians(lon1);
                    lon2 = Math.toRadians(lon2);
                    lat1 = Math.toRadians(lat1);
                    lat2 = Math.toRadians(lat2);



                    // Haversine formula
                    double dlon = lon2 - lon1;
                    double dlat = lat2 - lat1;
                    double a = Math.pow(Math.sin(dlat / 2), 2)
                            + Math.cos(lat1) * Math.cos(lat2)
                            * Math.pow(Math.sin(dlon / 2), 2);

                    double c = 2 * Math.asin(Math.sqrt(a));

                    // Radius of earth in kilometers. Use 3956
                    // for miles
                    double r = 6371;
                    dist = r * c;




    }


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

    TextView noinfo;
    LottieAnimationView noinf;

    LoadingDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        Log.w(TAG,"GFD 2");




        dialog=new LoadingDialog(getActivity());

        noinfo=(TextView)view.findViewById(R.id.noinfo);
        noinfo.setVisibility(View.GONE);
        noinf=(LottieAnimationView)view.findViewById(R.id.noinfolayout);
        noinf.setVisibility(View.GONE);

        name.clear();
        loca.clear();
        la.clear();
        lo.clear();
        rate.clear();
        max.clear();

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler);

        memberArrayList=new ArrayList<>();
        dialog.startLoading();

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Entertainment");
        databaseReference.orderByChild("rate");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){



                    if(Checkdate.date1==false) {
                        algo(Double.parseDouble(dataSnapshot1.child("long").getValue().toString()), Example.lo, Double.parseDouble(dataSnapshot1.child("lat").getValue().toString()), Example.la);
                        if (Integer.parseInt(dataSnapshot1.child("rate").getValue().toString()) <= Exa.b && Integer.parseInt(dataSnapshot1.child("max").getValue().toString()) >= Exa.a && dist <= Exa.c) {
                            mem = dataSnapshot1.child("name").getValue().toString();
                            name.add(mem);
                            mem = dataSnapshot1.child("location").getValue().toString();
                            loca.add(mem);
                            Log.w(TAG, "VDF " + dataSnapshot1.child("name").getValue().toString());
                            la.add(Double.parseDouble(dataSnapshot1.child("lat").getValue().toString()));
                            lo.add(Double.parseDouble(dataSnapshot1.child("long").getValue().toString()));

                            rate.add(Integer.parseInt(dataSnapshot1.child("rate").getValue().toString()));
                            max.add(Integer.parseInt(dataSnapshot1.child("max").getValue().toString()));
                            Log.w(TAG, "FTY " + Integer.parseInt(dataSnapshot1.child("rate").getValue().toString()));
                        }

                    }

                    else{
                        algo(Double.parseDouble(dataSnapshot1.child("long").getValue().toString()), Example.lo, Double.parseDouble(dataSnapshot1.child("lat").getValue().toString()), Example.la);
                        if (Integer.parseInt(dataSnapshot1.child("rate").getValue().toString()) <= Exa.b && Integer.parseInt(dataSnapshot1.child("max").getValue().toString()) >= Exa.a && dist <= Exa.c&&dataSnapshot1.child("date").getValue().toString()=="Yes"
                        ) {
                            mem = dataSnapshot1.child("name").getValue().toString();
                            name.add(mem);
                            mem = dataSnapshot1.child("location").getValue().toString();
                            loca.add(mem);
                            Log.w(TAG, "VDF " + dataSnapshot1.child("name").getValue().toString());
                            la.add(Double.parseDouble(dataSnapshot1.child("lat").getValue().toString()));
                            lo.add(Double.parseDouble(dataSnapshot1.child("long").getValue().toString()));

                            rate.add(Integer.parseInt(dataSnapshot1.child("rate").getValue().toString()));
                            max.add(Integer.parseInt(dataSnapshot1.child("max").getValue().toString()));
                            Log.w(TAG, "FTY " + Integer.parseInt(dataSnapshot1.child("rate").getValue().toString()));
                        }



                    }
                }
                Log.w(TAG,"KIU 9");


                dialog.stopLoading();
                if(name.size()!=0) {
                    recyclerView.setHasFixedSize(true);
                    new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    mAdapter = new ListAdapter(getActivity());
                    mAdapter.add(name, loca, la, lo, rate, max);

                    recyclerView.setAdapter(mAdapter);

                } else {

                    Log.w(TAG,"IPL ");
                    noinfo.setVisibility(View.VISIBLE);
                    noinf.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.w(TAG,"KIU 1");





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
