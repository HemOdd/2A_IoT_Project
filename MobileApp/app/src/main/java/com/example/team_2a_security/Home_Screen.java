package com.example.team_2a_security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.team_2a_security.Account_Screen.UserAccount;
import com.example.team_2a_security.Chat_Screen.Chat;
import com.example.team_2a_security.History_Screen.History;
import com.example.team_2a_security.Map_Screen.Map;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class Home_Screen extends AppCompatActivity {



    public static String username = "";
    public static String userKEY = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //get data sent by login screen upon successful login
        username = getIntent().getStringExtra("USERNAME");
        userKEY = getIntent().getStringExtra("USER_CODE");

        BottomNavigationView navigation = findViewById(R.id.bnv_mainMenuItems);
        navigation.setOnNavigationItemSelectedListener(bnv_listener);


        loadFragment(new Map()); //load default option for user (i.e map Option)
    }

    //telling the app which fragment to load when pressing what option.
    private BottomNavigationView.OnNavigationItemSelectedListener bnv_listener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.Map:
                    fragment = new Map();
                    loadFragment(fragment);
                    return true;
                case R.id.Chat:
                    fragment = new Chat();
                    loadFragment(fragment);
                    return true;
                case R.id.History:
                    fragment = new History();
                    loadFragment(fragment);
                    return true;
                case R.id.Account_Settings:
                    fragment = new UserAccount();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };


    /**
     * Helper function to load  the fragment of selected option.
     * @param fragment Fragment to be loaded on screen
     */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
       // transaction.addToBackStack(null);
        transaction.commit();
    }


    /**
     * Overridden method to ensure user logs out on the database when app unexpectedly is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseDatabase.getInstance().getReference().child("Users").child(userKEY).child("status").setValue(false);
    }


}
