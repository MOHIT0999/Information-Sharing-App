package com.example.demo2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    private EditText profilename,bio,profession,favoritespoet;
    private Button update;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        profilename=view.findViewById(R.id.profilename);
        bio=view.findViewById(R.id.bio);
        profession=view.findViewById(R.id.profession);
        favoritespoet=view.findViewById(R.id.favoritesport);
        update=view.findViewById(R.id.update);


        ParseUser parseUser=ParseUser.getCurrentUser();

//        <--------------------------------------------------To get data from server--------------------------------------------------->

        if(parseUser.get("ProfileName")==null){
            profilename.setText("");
        }
        else {
            profilename.setText(parseUser.get("ProfileName").toString());
        }
        if(parseUser.get("Bio")==null){
            bio.setText("");
        }
        else{
            bio.setText(parseUser.get("Bio").toString());
        }
        if(parseUser.get("Profession")==null){
            profession.setText("");
        }
        else{
            profession.setText(parseUser.get("Profession").toString());
        }
        if(parseUser.get("FavoriteSport")==null){
            favoritespoet.setText("");
        }
        else{
            favoritespoet.setText(parseUser.get("FavoriteSport").toString());
        }


//        <----------------------------------------------------To upload data on the server-------------------------------------------->




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseUser.put("ProfileName",profilename.getText().toString());
                parseUser.put("Bio",bio.getText().toString());
                parseUser.put("Profession",profession.getText().toString());
                parseUser.put("FavoriteSport",favoritespoet.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(parseUser!=null && e==null){
                            Toast.makeText(getContext(),"Info Updated",Toast.LENGTH_LONG).show();


                        }
                        else {
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });

        return view;
    }

}