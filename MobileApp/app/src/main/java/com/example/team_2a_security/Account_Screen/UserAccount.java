package com.example.team_2a_security.Account_Screen;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.team_2a_security.Chat_Screen.RecyclerAdapterMessage;
import com.example.team_2a_security.Home_Screen;
import com.example.team_2a_security.MainActivity;
import com.example.team_2a_security.R;
import com.example.team_2a_security.data_classes.Message;
import com.example.team_2a_security.data_classes.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserAccount extends Fragment {


    private static final int VERTICAL_ITEM_SPACE = 48 ;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterUsers adapter;
    private ArrayList<Users> allUsers = new ArrayList<>();
    // reference to the database
    private DatabaseReference firebaseRef ;
    private DatabaseReference mCondition;

    public UserAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_accout, container, false);
        firebaseRef= FirebaseDatabase.getInstance().getReference();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        recyclerView = view.findViewById(R.id.rv_UsersOnline);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new RecyclerAdapterUsers(allUsers);
        recyclerView.setAdapter(adapter);


        mCondition = firebaseRef.child("Users");
        mCondition.addValueEventListener(new ValueEventListener() {  //listener to display all users

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allUsers.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    //Log.i("@@@@@@@@@@@@@@@@@@@@@",  data.toString());
                    Users newUser = data.getValue(Users.class);
                    allUsers.add(newUser);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

}
