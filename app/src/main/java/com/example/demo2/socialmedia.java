package com.example.demo2;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class socialmedia extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialmedia);

        setTitle("Social Media App");

        toolbar=findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        viewPager=findViewById(R.id.viewpager);
        tabAdapter=new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        tabLayout=findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager,false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.postImageItem){
            if (Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(socialmedia.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE},3000);


            }
            else {
                captureImage();
            }
        }
        else {
            if (item.getItemId()==R.id.logoutUserItem){
                ParseUser.getCurrentUser().logOut();
                finish();
                Intent intent=new Intent(socialmedia.this,MainActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==3000){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                captureImage();
            }
        }
    }

    private void captureImage() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,4000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==4000) {
            if (resultCode == Activity.RESULT_OK && data != null) {                //means we get/selected the image
                //Do something with your capture image
                try {
                    Uri captureImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), captureImage);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();

                    ParseFile parseFile=new ParseFile("pic.png",bytes);              //pic is name of selected image
                    ParseObject parseObject=new ParseObject("Photo");          //Photo is class in dashboard
                    parseObject.put("picture",parseFile);
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());   //to know who upload image in photo class
                    final ProgressDialog progressDialog=new ProgressDialog(socialmedia.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.show();
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e== null){
                                Toast.makeText(socialmedia.this,"Image is shared !",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(socialmedia.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}