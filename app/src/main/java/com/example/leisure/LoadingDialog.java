package com.example.leisure;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;
    TextView loader;

    ArrayList<String> message=new ArrayList<>();

    public LoadingDialog(Activity a){
        activity=a;
    }

    public void startLoading(){


        message.add("Making your day.....");
        message.add("Brewing your schedule....");
        message.add("Finding locations....");



        LayoutInflater inflater=activity.getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog,null,false);

        loader=(TextView)view.findViewById(R.id.textView11);

        Random random=new Random();
        loader.setText(message.get(random.nextInt(message.size())));

        AlertDialog.Builder builder=new AlertDialog.Builder(activity);


        builder.setView(inflater.inflate(R.layout.dialog,null));
        builder.setCancelable(false);

        alertDialog=builder.create();
        alertDialog.show();
    }


    public void stopLoading(){

        alertDialog.dismiss();


    }
}
