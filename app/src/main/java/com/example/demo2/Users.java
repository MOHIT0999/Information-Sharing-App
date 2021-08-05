package com.example.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Users#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Users extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView listView;
    private TextView textView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Users() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Users.
     */
    // TODO: Rename and change types and number of parameters
    public static Users newInstance(String param1, String param2) {
        Users fragment = new Users();
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
        View view= inflater.inflate(R.layout.fragment_users, container, false);
//        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_users,container,false);
        listView=view.findViewById(R.id.listview);
        textView=view.findViewById(R.id.textloadingUsers);
        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);


//   <----------------------------------------------------to see the post of clicked user------------------------------------->
        listView.setOnItemClickListener(Users.this);
        listView.setOnItemLongClickListener(Users.this);



//   <-------------------------------------------------------------------------------------------------------------------------------->

        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e==null) {
                    if (users.size() > 0) {
                        for (ParseUser user : users) {
                            arrayList.add(user.getUsername());

                        }
                        listView.setAdapter(arrayAdapter);
                        textView.animate().alpha(0).setDuration(1000);
                        listView.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        return  view;
    }



//    <--------------------------------------function to see the posts------------------------------------------------->
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(getContext(),users_posts.class);
        intent.putExtra("username",arrayList.get(position));                 //putExtra is used to send data from one activity to another activity
        startActivity(intent);



//        <----------------------------------------method to see the user details by pressing more then 2 sec-------------------------->
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereEqualTo("username",arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null && e==null){
                    final FlatDialog flatDialog=new FlatDialog(getContext());
                    flatDialog.setTitle(user.getUsername()+"'s Info");
                    flatDialog.setFirstTextField(user.get("Profession")+"");
                    flatDialog.setSecondTextField(user.get("Bio")+"");
                    flatDialog.setFirstButtonText("Ok");
                    flatDialog.withFirstButtonListner(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            flatDialog.dismiss();
                        }
                    }).show();


                }
            }
        });

        return true;
    }
}