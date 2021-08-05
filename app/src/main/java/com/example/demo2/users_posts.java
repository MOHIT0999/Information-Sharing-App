package com.example.demo2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class users_posts extends AppCompatActivity {

    private LinearLayout linearLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        linearLayout=findViewById(R.id.linearlayout);
        textView=findViewById(R.id.textView);


        Intent recivedIntentObject=getIntent();
        String recivedUserName=recivedIntentObject.getStringExtra("username");
        Toast.makeText(users_posts.this,recivedUserName,Toast.LENGTH_LONG).show();

        setTitle(recivedUserName+"'s Posts");

        ParseQuery<ParseObject> parseQuery=new ParseQuery("Photo");
        parseQuery.whereEqualTo("username",recivedUserName);                                         //only show the selected user post
//        parseQuery.orderByAscending("createdAt");

//        inventoryQuery.addDescendingOrder("createdAt");
        parseQuery.addDescendingOrder("createdAt");

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size()>0 && e==null){
                    for (ParseObject post:objects){
                        TextView imageDes=new TextView(users_posts.this);

                        if(post.get("description")==null){
                            imageDes.setText("");
                            imageDes.setVisibility(View.GONE);

                        }
                        else {
                            imageDes.setText(post.get("description").toString());
                        }
//                        imageDes.setText(post.get("description")+" ");

                        ParseFile parseFile=(ParseFile) post.get("picture");
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data!=null && e==null){
                                    Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);           //load the image in bit map
                                    ImageView imageView=new ImageView(users_posts.this);
                                    LinearLayout.LayoutParams imagelayoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imagelayoutParams.setMargins(5,5,5,5);
                                    imageView.setLayoutParams(imagelayoutParams);
                                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    imageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams imageDeslayoutParam=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageDeslayoutParam.setMargins(5,5,5,5);
                                    imageDes.setLayoutParams(imageDeslayoutParam);
                                    imageDes.setGravity(Gravity.CENTER);
                                    imageDes.setTextColor(Color.WHITE);
                                    imageDes.setBackgroundColor(Color.BLUE);
                                    imageDes.setTextSize(24);

                                    linearLayout.addView(imageView);
                                    linearLayout.addView(imageDes);

                                }
                            }

                        });
                    }
                }
                else {
                    LinearLayout.LayoutParams textlayoutParam=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    textlayoutParam.setMargins(5,5,5,5);
                    textView.setLayoutParams(textlayoutParam);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(Color.BLACK);
                    textView.setText(recivedUserName+" doesn't have any post");
                    textView.setTextSize(24);
                    textView.setVisibility(View.VISIBLE);

//                    Toast.makeText(users_posts.this,recivedUserName+" doesn't have any post ",Toast.LENGTH_LONG).show();
//                    finish();
                }
            progressDialog.dismiss();
            }

        });
    }
}