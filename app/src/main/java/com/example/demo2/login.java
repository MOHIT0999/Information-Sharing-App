package com.example.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import id.ionbit.ionalert.IonAlert;

public class login extends AppCompatActivity implements View.OnClickListener {

    EditText loginemail,loginpassword;
    Button loginbutton,tosignupbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login ");

        loginemail=findViewById(R.id.loginemail);
        loginpassword=findViewById(R.id.loginpassword);

        loginbutton=findViewById(R.id.loginbutton);
        tosignupbutton=findViewById(R.id.tosignupbutton);

        loginbutton.setOnClickListener(this);
        tosignupbutton.setOnClickListener(this);


//     loginbutton.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//             ParseUser.logInInBackground(loginemail.getText().toString(), loginpassword.getText().toString(), new LogInCallback() {
//                 @Override
//                 public void done(ParseUser user, ParseException e) {
//                     if (user!=null && e==null){
//                         Toast.makeText(login.this,user.getUsername().toString()+" is login successfully !",Toast.LENGTH_LONG).show();
//                         Transitiontosocialmedia();
//                     }
//                     else {
//                         Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                     }
//                 }
//             });
//         }
//     });
//     tosignupbutton.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//             Intent intent=new Intent(login.this,MainActivity.class);
//             startActivity(intent);
//         }
//     });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginbutton:
                ParseUser.logInInBackground(loginemail.getText().toString(), loginpassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user!=null && e==null){
                            if (user.getBoolean("emailVerified")){
//                                Toast.makeText(login.this,user.getUsername().toString()+" is login successfully !",Toast.LENGTH_LONG).show();
                                new IonAlert(login.this,IonAlert.SUCCESS_TYPE)
                                        .setTitleText("Welcome "+user.getUsername()+"!")
                                        .setConfirmClickListener(new IonAlert.ClickListener() {
                                            @Override
                                            public void onClick(IonAlert ionAlert) {
                                                Transitiontosocialmedia();
                                            }
                                        })
                                        .show();
//

//                                Transitiontosocialmedia();
                            }
                            else {
                                    new IonAlert(login.this,IonAlert.ERROR_TYPE)
                                            .setTitleText("Login fail")
                                            .setContentText("Please Verify your email first")
                                            .show();

//                                    Toast.makeText(login.this,"Login fail"+"\n"+"Please Verify your email first",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
//                            Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            new IonAlert(login.this,IonAlert.ERROR_TYPE)
                                    .setTitleText(e.getMessage())
                                    .setContentText("")
                                    .show();

                        }
                    }
                });
                break;
            case R.id.tosignupbutton:

                Intent intent=new Intent(login.this,MainActivity.class);
                startActivity(intent);
                break;

        }

    }
    private void Transitiontosocialmedia(){
        Intent intent=new Intent(login.this,socialmedia.class);
        startActivity(intent);
        finish();
    }
}