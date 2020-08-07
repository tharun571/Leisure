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

import com.example.leisure.Room.AppExecutors;
import com.example.leisure.Room.Database1;
import com.example.leisure.Room.User;
import com.example.leisure.Room.check;

import java.util.ArrayList;
import java.util.List;


public class Favourites extends Fragment {

    private static final String TAG = "ASDFG";
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

    LoadingDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_favourites, container, false);



        dialog=new LoadingDialog(getActivity());

        noinfo=(TextView)view.findViewById(R.id.noinfo1);

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler1);




        final Database1 database1=Database1.getInstance(getActivity());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<User> userList=database1.dao1().getAll();

                for(int i=0;i<userList.size();i++){
                    if(name.contains(userList.get(i).nameof)==false&&loca.contains(userList.get(i).location)==false) {
                        name.add(userList.get(i).nameof);
                        loca.add(userList.get(i).location);
                        la.add(Double.parseDouble(userList.get(i).lat));
                        lo.add(Double.parseDouble(userList.get(i).longi));
                        rate.add(Integer.parseInt(userList.get(i).rate));
                        max.add(Integer.parseInt(userList.get(i).max));

                    }

                }


            }
        });


        Log.w(TAG,"OIP "+ name.size());
        if(name.size()>0) {
            recyclerView.setHasFixedSize(true);
            new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new ListAdapter(getActivity());
            mAdapter.add(name, loca, la, lo, rate, max);

            recyclerView.setAdapter(mAdapter);

        } else {

            noinfo.setVisibility(View.VISIBLE);
        }

        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
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

            database1.dao1().delete(user);
            Fragment frg = new Favourites();

            final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();


        }
    };
}
