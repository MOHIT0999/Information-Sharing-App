package com.example.demo2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    TextView text;
    EditText signupusername,signupemail,signuppassword;
//    String a = "";
    Button toanother,signupbutton,tologinbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");

        signupusername = findViewById(R.id.signupusername);
        signupemail = findViewById(R.id.signupEmail);
        signuppassword = findViewById(R.id.signuppassword);


        signupbutton = findViewById(R.id.signupbutton);
        tologinbutton = findViewById(R.id.tologinbutton);

        signupbutton.setOnClickListener(this);
        tologinbutton.setOnClickListener(this);



//        text = findViewById(R.id.textView);
//        toanother=findViewById(R.id.toanother);


//   <------------------------------------------To keep login the current user after the restart app---------------------------------------->

        if (ParseUser.getCurrentUser() != null) {
            Transitiontosocialmedia();
        }




//        <-------------------------------------To push detail forcefully in databse using class name---------------------------------------->


//    public void clicked(View view){
//        ParseObject gameScore = new ParseObject("GameScore");
//        gameScore.put("score", 1337);
//        gameScore.put("playerName", "Sean Plott");
//        gameScore.put("cheatMode", false);
//        gameScore.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e==null){
//                    Toast.makeText(MainActivity.this,"gameScore is saved! ",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//

//        <-------------------------------------------------To get detail usind object id--------------------------------------------------->


//        ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("GameScore");
//        parseQuery.getInBackground("EqnCRPvonU", new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if(object!=null && e==null){
//                    text.setText(object.get("score").toString()+" "+object.get("cheatMode").toString());
//
//                }
//            }
//        });
//      }


//        <----------------------------------------------To get the list of a class in database----------------------------------------------->


//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParseQuery<ParseObject> queryall = ParseQuery.getQuery("GameScore");
//                queryall.findInBackground(new FindCallback<ParseObject>() {
//                    @Override
//                    public void done(List<ParseObject> objects, ParseException e) {
//                        if (e == null) {
//                            if (objects.size() > 0) {
//                                for (ParseObject object : objects) {
//                                    a = a + object.get("playerName").toString() + " " + object.get("score").toString() + " " + object.get("cheatMode").toString() + "\n";
//                                }
//                                text.setText(a);
//
//                            }
//                        }
//                    }
//                });
//            }
//        });


//       <-------------------------------------------------------To go on another page------------------------------------------------------>


//    toanother.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent=new Intent(MainActivity.this,SignUpLogin.class);
//            startActivity(intent);
//        }
//    });
//    }


//    <----------------------------------------------------------To signup the user-------------------------------------------------------->

//        signupbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final ParseUser user=new ParseUser();
//                user.setEmail(signupemail.getText().toString());
//                user.setUsername(signupusername.getText().toString());
//                user.setPassword(signuppassword.getText().toString());
//                ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
//                progressDialog.setMessage("Signing up "+signupusername.getText().toString());
//                progressDialog.show();
//
//                user.signUpInBackground(new SignUpCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        if (e== null){
//                            Toast.makeText(MainActivity.this, user.getUsername().toString()+" is successfully signup !",Toast.LENGTH_LONG).show();
//                            Transitiontosocialmedia();
////                            Intent intent=new Intent(MainActivity.this,login.class);
////                            startActivity(intent);
//
//                        }
//                        else {
//                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                        }
//                        progressDialog.dismiss();
//                    }
//                });
//            }
//        });
//
//    tologinbutton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent=new Intent(MainActivity.this,login.class);
//            startActivity(intent);
//            finish();
//        }
//    });


    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signupbutton:
                final ParseUser user=new ParseUser();
                user.setEmail(signupemail.getText().toString());
                user.setUsername(signupusername.getText().toString());
                user.setPassword(signuppassword.getText().toString());
                ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Signing up "+signupusername.getText().toString());
                progressDialog.show();

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e== null){
                            Toast.makeText(MainActivity.this, user.getUsername().toString()+" is successfully signup !",Toast.LENGTH_LONG).show();
//                            Transitiontosocialmedia();
                            Intent intent=new Intent(MainActivity.this,login.class);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

                break;
            case R.id.tologinbutton:
                Intent intent=new Intent(MainActivity.this,login.class);
                startActivity(intent);
                finish();
                break;

        }
    }
    private void Transitiontosocialmedia(){
        Intent intent=new Intent(MainActivity.this,socialmedia.class);
        startActivity(intent);
        finish();


    }
}