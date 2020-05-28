package com.example.team_2a_security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.team_2a_security.data_classes.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    // reference to the database --TEsting
    DatabaseReference firebaseRef ;
    DatabaseReference mCondition;
    private EditText usernameEntered;
    private EditText passwordEntered;
    private Boolean logInSuccessful = false;
    public static final String CHANNEL_ID = "my_channel_01";
    public static final String CHANNEL_NAME = "Simplified Coding Notification";
    public static final String CHANNEL_DESCRIPTION = "simplified coding notification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEntered = findViewById(R.id.et_Username);
        passwordEntered = findViewById(R.id.et_password);

        /*

       This code below is for testing purposes, that is adding users before app is instatiated
         */
//////////////////////////////////////////////////////////////////////////////////////////////
        firebaseRef= FirebaseDatabase.getInstance().getReference();                         //
        mCondition = firebaseRef.child("Users");                                            //
       // mCondition.push().setValue(new Users("Hemad","1234",true));              //
//////////////////////////////////////////////////////////////////////////////////////////////
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            mChannel.setDescription(CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }

        MyNotificationManager.getInstance(this).displayNotification("Greetings", "Hello how are you?");
        FirebaseMessaging.getInstance().subscribeToTopic("notification");
    }

    /**
     * function activates when log in button is clicked. pulls all user info from database ONCE
     * and compares against entered values and authenticates the user. At the same time this
     * function toggles the user as "online" on the database.
     * @param view
     */
    public void OnLoginClicked(View view) {
        mCondition.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    //Log.i("@@@@@@@@@@@@@@@@@@@@@",  child.toString());
                    if(child.getValue(Users.class).getUsername().equals(usernameEntered.getText().toString())){
                        if(child.getValue(Users.class).getPassword().equals(passwordEntered.getText().toString())){
                            startApp(child);
                            logInSuccessful = true;
                            mCondition.child(child.getKey()).child("status").setValue(true);  //change user status in database
                        }
                    }
                }
                if (logInSuccessful){
                    successfulLoginToast();
                }else{
                    unsuccessfulLoginToast();
                }
                logInSuccessful=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("@@@@@@@@@@@@@@@@@@@@@",  "Bitch, the app is not getting data from firebase");
                databaseErrorToast();
            }
        });
    }

    /**
     * function to start the application and land the user on a home screen if the authentication
     * is successful. appending the username and the generated user ID to the home screen data
     *
     * @param child
     */
    public void startApp(DataSnapshot child){
        Intent intent = new Intent(this, Home_Screen.class);
        intent.putExtra("USERNAME",usernameEntered.getText().toString());
        intent.putExtra("USER_CODE",child.getKey());
        startActivity(intent);
    }

    /**
     * toast function
     */
    public void successfulLoginToast(){
        Toast.makeText(this,"Log In SUCCESSFUL !!",Toast.LENGTH_LONG).show();
    }

    /**
     * toast function
     */
    public void unsuccessfulLoginToast(){
        Toast.makeText(this,"Invalid Credentials !!",Toast.LENGTH_LONG).show();
    }

    /**
     * toast function
     */
    public void databaseErrorToast(){
        Toast.makeText(this," Database Error !! ",Toast.LENGTH_LONG).show();
    }

}
