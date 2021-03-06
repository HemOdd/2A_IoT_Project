package com.example.team_2a_security.History_Screen;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.team_2a_security.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    RecyclerAdapterHistory adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private final String TAG = "HISTORY_FRAGMENT";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Sensor> message = new ArrayList<>();

    //string array where alerts are added
    //any item added in array is auto added to view
//    private String[] history = { "Alert on floor plan No. 3 @ XX/XX/XXX - 3:23 AM",
//            "Alert on floor plan No. 6 @ XX/XX/XXX - 7:28 PM",
//            "Alert on floor plan No. 4 @ XX/XX/XXX - 1:09 AM"
//            };


    public History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_history, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        recyclerView =  view.findViewById(R.id.history_rv);

        layoutManager = new LinearLayoutManager(getContext());  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager

        adapter = new RecyclerAdapterHistory(); // recycler adapter responsible to show how many view shows in the layout
        recyclerView.setAdapter(adapter);   //set adapter
        adapter.setPayload(message);


//        Sensor newSensorMsg = new Sensor(item5, item1, item2, item3, item4);
//        message.add(newSensorMsg);

        adapter.notifyDataSetChanged();

        // adapted from firebase documentation
        //https://firebase.google.com/docs/firestore/query-data/listen#listen_to_multiple_documents_in_a_collection
        db.collection("Log").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                message.clear();
                for (QueryDocumentSnapshot doc : value) {
                    Log.d("data", doc.getData() + "");
                    double test = (double) doc.get("payload");
                    Log.d("data", test + "");
                    int sensorid = doc.getLong("sensorId").intValue();
                    String dateStamp = doc.getString("dateStamp");
                    String sensorType = doc.getString("sensorType");
                    double sensorData = (double) doc.get("payload");
                    String timeStamp = doc.getString("timeStamp");
                    String location = doc.getString("location");
                    String dateTime = dateStamp + "-" + timeStamp;
                    Sensor newData = new Sensor(sensorid,sensorType,dateTime,location,sensorData);
                    message.add(newData);
                    adapter.notifyDataSetChanged();
                }
            }
        });


//        db.collection("Alert")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                Log.d(TAG, document.getString("sensorName"));
//                                String sensorName = document.getString("sensorName");
//                                String dateStamp = document.getString("dateStamp");
//                                String value = document.getString("temperature");
//                                String timeStamp = document.getString("timeStamp");
//                                String location = document.getString("location");
//                                String dateTime = dateStamp + "-" + timeStamp;
//                                Sensor newData = new Sensor(sensorName,dateTime,location);
//                                message.add(newData);
//                                adapter.notifyDataSetChanged();
//
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });
    }
}
