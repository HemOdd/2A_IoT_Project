package com.example.team_2a_security.Chat_Screen;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.team_2a_security.Home_Screen;
import com.example.team_2a_security.R;
import com.example.team_2a_security.data_classes.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chat extends Fragment {

    Activity activity = getActivity();
    Context context;
    String message;
    EditText et_message;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterMessage adapter;
    ArrayList<Message> allMessages = new ArrayList<>();
    // reference to the database
    DatabaseReference firebaseRef ;
    DatabaseReference mCondition;


    public Chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);

        et_message = view.findViewById(R.id.et_enteredMessage);

        Button btn_send = view.findViewById(R.id.btn_sendMessage);
        // Set a click listener for Fragment button
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message = et_message.getText().toString();


                if(message.equals("")){
                    // Create a Toast notification/message
                    Toast toast = Toast.makeText(
                            //display toast if attempt to send empty message
                            getActivity().getApplicationContext(), "Type SOmething ....", Toast.LENGTH_LONG
                    );
                    // Set the Toast display position layout center
                    toast.setGravity(Gravity.CENTER,0,0);
                    // show the toast
                    toast.show();

                }else{
                    Message currentMsg = new Message(message,getTimeStamp(), Home_Screen.username);

                    mCondition = firebaseRef.child("Message");
                    mCondition.push().setValue(currentMsg); //push msg object to firebase message section

                    et_message.getText().clear();
                }
            }
        });

        firebaseRef= FirebaseDatabase.getInstance().getReference();

        // Return the view as Fragment layout
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        recyclerView = view.findViewById(R.id.rv_message_area);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapterMessage(allMessages);
        recyclerView.setAdapter(adapter);


        mCondition = firebaseRef.child("Message");
        mCondition.addValueEventListener(new ValueEventListener() {  //listener for new messages

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allMessages.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    //Log.i("@@@@@@@@@@@@@@@@@@@@@",  data.toString());
                    Message newMsg = data.getValue(Message.class);
                    allMessages.add(newMsg);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    String getTimeStamp(){
        return  new SimpleDateFormat("dd.MM.yyyy - hh:mm", Locale.UK).format(new Date());
    }

    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        if(view != null){

        }
    }


    public void setContext(Context context) {
        this.context = context;
    }


}
