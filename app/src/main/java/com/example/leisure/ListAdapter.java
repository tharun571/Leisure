package com.example.leisure;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
TextView name;

    private static final String TAG = "qwwerty";
ArrayList<String> name1 =new ArrayList<>();
ArrayList<String> name2 =new ArrayList<>();
ArrayList<Double> l1 =new ArrayList<>();
ArrayList<Double> l2 =new ArrayList<>();
ArrayList<Integer> rate =new ArrayList<>();
ArrayList<Integer> max =new ArrayList<>();

Context context;

public String getPos(int position){
    return name1.get(position);
}



    public ListAdapter(  Context context) {

        this.context = context;
        Log.w(TAG,"KIU 3");

    }

    public  void add(ArrayList<String> n1,ArrayList<String> n2,ArrayList<Double> l11,ArrayList<Double> l22, ArrayList<Integer> rate1, ArrayList<Integer> rate2){
        name1.addAll(n1);
        name2.addAll(n2);

        l1.addAll(l11);
        l2.addAll(l22);

        rate.addAll(rate1);
        max.addAll(rate2);
    }

    @Override
    public int getItemCount() {
        return name1.size();
    }
    @NonNull
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);




        return new MyViewHolder(view);

    }

Home home;
int ra=1,l;
    @Override
    public void onBindViewHolder(@NonNull final ListAdapter.MyViewHolder holder, int position) {

        holder.textView.setText(name1.get(position));
        holder.loc.setText(name2.get(position));

        Log.w(TAG,"KIU 4"+rate.get(position).toString());

        holder.price.setText(""+rate.get(position).toString());
        holder.max1.setText(""+max.get(position).toString());


        if(ra==2){
            holder.imageView.setImageResource(R.drawable.castle);
        }
        else if(ra==3){
            holder.imageView.setImageResource(R.drawable.rc);
            ra=0;
        }

        else if(ra==1){
            holder.imageView.setImageResource(R.drawable.park);

        }

        ra++;

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(final View view, int position) {
                l=position;

                Log.w(TAG,"KIU 56");
                home=(Home)view.getContext();
                locationOfInfo.lat=l1.get(position);
                locationOfInfo.longi=l2.get(position);
                locationOfInfo.name=name1.get(position);
                locationOfInfo.location=name2.get(position);
                locationOfInfo.price=""+rate.get(position);
                locationOfInfo.max=""+max.get(position);


                Fragment fragment1= new MapsInfo();
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

    }



    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // each data item is just a string in this case
        public TextView textView,show,loc,price,max1,loccc;
        LinearLayout des;
        ImageView imageView;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.place);
            des=itemView.findViewById(R.id.desc);
            show=itemView.findViewById(R.id.show);
            loc=itemView.findViewById(R.id.placeloc);
            max1=itemView.findViewById(R.id.max1);
            price=itemView.findViewById(R.id.price);
            imageView=itemView.findViewById(R.id.placeholder);
            loccc=itemView.findViewById(R.id.loccc);


            Log.w(TAG,"KIU 2");

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            itemClickListener.OnClick(v,getAdapterPosition());
        }
    }
}
