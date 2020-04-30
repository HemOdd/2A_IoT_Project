package com.example.team_2a_security.Map_Screen;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends Fragment {

    private ArrayList<Blueprints> allBlueprints = new ArrayList<>();
    // reference to the database
    private DatabaseReference firebaseRef ;
    private DatabaseReference mCondition;
    private TextView totalNumberOfBlueprint;
    private TextView currentBlueprintNumber_tv;
    private Button nextBlueprint;
    private Button prevBlueprint;
    private ImageView currentBlueprintImage;
    private int currentBlueprintNumber = 0;


    public Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        firebaseRef= FirebaseDatabase.getInstance().getReference();
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){


        totalNumberOfBlueprint = view.findViewById(R.id.tv_totalBlueprintNumber);
        currentBlueprintNumber_tv = view.findViewById(R.id.tv_blueprintNumber);
        currentBlueprintImage = view.findViewById(R.id.iv_blueprint);


        mCondition = firebaseRef.child("Blueprints");
       // mCondition.push().setValue(new Blueprints("HAHA"));
        mCondition.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allBlueprints.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                   // Log.i("@@@@@@@@@@@@@@@@@@@@@",  data.getValue().toString());
                    Blueprints newBlueprint = data.getValue(Blueprints.class);
                    allBlueprints.add(newBlueprint);
                }
                totalNumberOfBlueprint.setText(Integer.toString(allBlueprints.size()));
                loadNewImage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        nextBlueprint = view.findViewById(R.id.btn_nextBlueprint);
        prevBlueprint = view.findViewById(R.id.btn_prevBlueprint);


        currentBlueprintNumber_tv.setText(Integer.toString(currentBlueprintNumber+1));



        nextBlueprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentBlueprintNumber < allBlueprints.size()-1){
                    currentBlueprintNumber++;
                    loadNewImage();
                    currentBlueprintNumber_tv.setText(Integer.toString(currentBlueprintNumber+1));
                }

            }
        });

        prevBlueprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentBlueprintNumber > 0){
                    currentBlueprintNumber--;
                    loadNewImage();
                    currentBlueprintNumber_tv.setText(Integer.toString(currentBlueprintNumber+1));
                }
            }
        });


    }

    private void loadNewImage(){
        Glide.with(getActivity().getApplicationContext())
                .load(allBlueprints.get(currentBlueprintNumber).getBlueprintURI())
                .placeholder(R.drawable.loading_img)
                .error(R.color.Black)
                .into(currentBlueprintImage);
    }


}

