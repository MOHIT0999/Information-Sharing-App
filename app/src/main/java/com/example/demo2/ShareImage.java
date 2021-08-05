package com.example.demo2;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShareImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareImage extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView imageshare;
    private EditText imagedescription;
    private Button shareimagebutton;
    Bitmap recivedImagebitmap;

    public ShareImage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShareImage.
     */
    // TODO: Rename and change types and number of parameters
    public static ShareImage newInstance(String param1, String param2) {
        ShareImage fragment = new ShareImage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_image, container, false);

        imageshare=view.findViewById(R.id.imageshare);
        imagedescription=view.findViewById(R.id.imagedescription);
        shareimagebutton=view.findViewById(R.id.shareimagebutton);

        imageshare.setOnClickListener(ShareImage.this);
        shareimagebutton.setOnClickListener(ShareImage.this);

        return view;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageshare:
                if (Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},1000);


                }
                else {
                    getChosenimage();
                }
                break;
            case R.id.shareimagebutton:
                if(recivedImagebitmap!=null){
                    if(imagedescription.getText().toString().equals("")){
                        Toast.makeText(getContext(),"Enter the image description ! ",Toast.LENGTH_LONG).show();
                    }
                    else {
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();    //store image in array (basically used of lage data)

                        recivedImagebitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes=byteArrayOutputStream.toByteArray();                        //converted image into bytr
                        ParseFile parseFile=new ParseFile("pic.png",bytes);              //pic is name of selected image
                        ParseObject parseObject=new ParseObject("Photo");          //Photo is class in dashboard
                        parseObject.put("picture",parseFile);
                        parseObject.put("description",imagedescription.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());   //to know who upload image in photo class
                        final ProgressDialog progressDialog=new ProgressDialog(getContext());
                        progressDialog.setMessage("Loading....");
                        progressDialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e== null){
                                    Toast.makeText(getContext(),"Image is shared !",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        });

                    }

                }
                else {
                    Toast.makeText(getContext(),"Please select the image !",Toast.LENGTH_LONG).show();
                }
                break;

        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1000){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getChosenimage();
            }
        }
    }
    private void getChosenimage() {
//        Toast.makeText(getContext(),"We can access the image",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2000){
            if(resultCode== Activity.RESULT_OK){                //means we get/selected the image
                //Do something with your capture image
                try {
                    Uri selectedImage=data.getData();
                    String[] filePathColumn={ MediaStore.Images.Media.DATA};
                    Cursor cursor=getActivity().getContentResolver().query(selectedImage,filePathColumn,null,null,
                            null,null);
                    cursor.moveToFirst();                           //we want the first object
                    int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath=cursor.getString(columnIndex);
                    cursor.close();
                    recivedImagebitmap= BitmapFactory.decodeFile(picturePath);            //image is converted into bitmap
                    imageshare.setImageBitmap(recivedImagebitmap);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}