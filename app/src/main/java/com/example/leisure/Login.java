package com.example.leisure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText mail,pass;
    Button lo;
    FirebaseAuth.AuthStateListener firebaseAuth;
    FirebaseAuth mfirebaseAuth;

    Pair[] pairs=new Pair[3];
    TextView title,reg,forg;
    ImageView image;
    CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        title=(TextView)findViewById(R.id.name);
        image=(ImageView)findViewById(R.id.name_image);

        mfirebaseAuth= FirebaseAuth.getInstance();
        mail=(EditText)findViewById(R.id.editText);
        pass=(EditText)findViewById(R.id.editText2);

        lo=(Button)findViewById(R.id.login);
        reg=(TextView) findViewById(R.id.register);
        forg=(TextView) findViewById(R.id.forgot);

        saveLoginCheckBox=(CheckBox)findViewById(R.id.saveLoginCheckBox);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            mail.setText(loginPreferences.getString("username", ""));
            pass.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);

        }

        pairs[0]=new Pair<View,String>(title,"leisure");
        pairs[1]=new Pair<View,String>(image,"leisure_image");
        pairs[2]=new Pair<View,String>(reg,"button");


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registration.class);
                ActivityOptions options= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                }
                startActivity(intent,options.toBundle());
            }
        });

        firebaseAuth=new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mUser=mfirebaseAuth.getCurrentUser();
                if (mUser!=null) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this,Home.class));
                }



            }
        };


        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email = mail.getText().toString();
                String pwd = pass.getText().toString();





                if (email.isEmpty()){
                    mail.setError("Please enter mail id");
                    mail.requestFocus();
                }
                else if (pwd.isEmpty()){
                    pass.setError("Please enter mail id");
                    pass.requestFocus();
                }


                else{
                    if (saveLoginCheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", email);
                        loginPrefsEditor.putString("password", pwd);
                        loginPrefsEditor.commit();

                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                    mfirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Login.this, "Login Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                startActivity(new Intent(Login.this,Home.class));
                            }
                        }
                    });
                }


            }
        });


        forg.setOnClickListener(new View.OnClickListener() {





            @Override
            public void onClick(View v) {
                String email = mail.getText().toString();
                if(email!=null) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(Login.this, "Email sent", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(Login.this, "Enter Email ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
