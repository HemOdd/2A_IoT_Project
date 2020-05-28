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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

    private Button sensor1Button;
    private Button sensor2Button;

    private Date currentDate;
    private static String TAG = "maplog"; // for logging tag

    public Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        db = FirebaseFirestore.getInstance(); // get reference to root db
        currentDate = new Date();
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        //bind id
        sensor1Button = view.findViewById(R.id.btn_sensor1);
        sensor2Button = view.findViewById(R.id.btn_sensor2);

        //event listener on button click for sensor 1
        sensor1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = sensor1Button.getAnimation();
                if(anim != null && anim.isInitialized()){
                    anim.cancel();
                    sensor1Button.setBackgroundResource(R.drawable.green_circle);
                    updateDb("distance");
                }
            }
        });

        //event listener on button click for sensor 2
        sensor2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = sensor2Button.getAnimation();
                if(anim != null && anim.isInitialized()){
                    anim.cancel();
                    sensor2Button.setBackgroundResource(R.drawable.green_circle);
                    updateDb("temperature");
                    updateDb("humidity");
                }
            }
        });
        // get reference to DB
        // modified from firebase document
        // available on https://firebase.google.com/docs/firestore/query-data/listen#java
        DocumentReference docRef = db.collection("Alert").document("sensor");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData()); // logging purposes

                    // bind all data so that it can be compared
                    boolean distance = (boolean) snapshot.get("distance");
                    boolean temperature = (boolean) snapshot.get("temperature");
                    boolean humidity = (boolean) snapshot.get("humidity");

                    if (distance)
                    {
                        //if true means distance sensor triggered
                        showAlertOnButton(sensor1Button);
                    }

                    if (temperature || humidity)
                    {
                        //if true means either humidity or temperature sensor is triggered
                        //combined because it is actually the same sensor
                        showAlertOnButton(sensor2Button);
                    }

                } else {
                    //for logging purposes
                    Log.d(TAG, "Current data: null");
                }
            }
        });


    }


    private void showAlertOnButton(Button inButton){
        /**
         * Make the button blink
         */

        inButton.setBackgroundResource(R.drawable.red_circle);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        inButton.startAnimation(anim);

    }

    private void updateDb(String toUpdate)
    {
        /**
         * to update firestore to false because when is true it will set the button to blink
         * adapted from firebase documentation
         * https://firebase.google.com/docs/firestore/manage-data/add-data#java_16
         * */
        DocumentReference alertRef = db.collection("Alert").document("sensor");
        alertRef.update(toUpdate, false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }


}

