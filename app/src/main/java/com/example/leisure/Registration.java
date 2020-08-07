package com.example.leisure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    EditText mail,pass;
    Button reg;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth=FirebaseAuth.getInstance();

        mail=(EditText)findViewById(R.id.editTextR);
        pass=(EditText)findViewById(R.id.editText2R);


        reg=(Button)findViewById(R.id.register1);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString();
                String pwd = pass.getText().toString();


                if (email.isEmpty()){
                    mail.setError("Please enter mail id");
                    mail.requestFocus();
                }
                else if (pwd.isEmpty()){
                    pass.setError("Please enter password");
                    pass.requestFocus();
                }


                else{

                    boolean up=false,lo=false,nom=false;
                    for(int i=0;i<pwd.length();i++){
                        char ch=pwd.charAt(i);
                        if(Character.isDigit(ch)&&nom==false){
                            nom=true;
                        }

                        if(Character.isUpperCase(ch)&&up==false){
                            up=true;
                        }

                        if(Character.isLowerCase(ch)&&lo==false){
                            lo=true;
                        }
                    }

                    if(nom&&lo&&up&&pwd.length()>=6) {

                        firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Registration.this, "Registration Unsuccessful, Please Try Agin", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Registration.this, Login.class));

                                }
                            }
                        });
                    }

                    else{
                        Toast.makeText(Registration.this, "Make sure your password has more than 6 characters and has atleast one lower case, upper case, one digit respectively", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}
