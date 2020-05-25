package com.example.team_2a_security.Map_Screen;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.team_2a_security.Account_Screen.RecyclerAdapterUsers;
import com.example.team_2a_security.R;
import com.example.team_2a_security.data_classes.Blueprints;
import com.example.team_2a_security.data_classes.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends Fragment {

    private ArrayList<Blueprints> allBlueprints = new ArrayList<>();
    // reference to the database
    private FirebaseFirestore db;
    private DatabaseReference firebaseRef ;
    private DatabaseReference mCondition;
    private TextView totalNumberOfBlueprint;
    private TextView currentBlueprintNumber_tv;
    private Button sensor1Button;
    private Button sensor2Button;
    private ImageView currentBlueprintImage;
    private int currentBlueprintNumber = 0;
    private Date currentDate;


    public Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        db = FirebaseFirestore.getInstance();
        currentDate = new Date();
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        sensor1Button = view.findViewById(R.id.btn_sensor1);
        sensor2Button = view.findViewById(R.id.btn_sensor2);

        sensor1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = sensor1Button.getAnimation();
                if(anim != null && anim.isInitialized()){
                    anim.cancel();
                    sensor1Button.setBackgroundResource(R.drawable.green_circle);
                }
            }
        });

        sensor2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = sensor2Button.getAnimation();
                if(anim != null && anim.isInitialized()){
                    anim.cancel();
                    sensor2Button.setBackgroundResource(R.drawable.green_circle);
                }
            }
        });

        db.collection("Alert")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {

                            String sensorName = doc.getString("sensorName");
                            String dateStamp = doc.getString("dateStamp");
                            String timeStamp = doc.getString("timeStamp");
                            String dateTime = dateStamp+"T"+timeStamp;
                            if(checkDateTime(dateTime) == true) {
                                System.out.println("TRU IT IS");
                                if (sensorName.equals("Sensor1")){
                                    showAlertOnButton(sensor1Button);
                                } else if (sensorName.equals("Sensor2")){
                                    showAlertOnButton(sensor2Button);
                                }
                            }
                        }
                    }
                });

    }

    private boolean checkDateTime(String inputDateTimeStr){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy'T'HH:mm:ss");
        try {
            Date d = sdf.parse(inputDateTimeStr);
            if (d.compareTo(currentDate) > 0){
                return true;
            } else {
                return false;
            }
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }

        return false;
    }

    private void showAlertOnButton(Button inButton){

        inButton.setBackgroundResource(R.drawable.red_circle);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        inButton.startAnimation(anim);

    }




}

