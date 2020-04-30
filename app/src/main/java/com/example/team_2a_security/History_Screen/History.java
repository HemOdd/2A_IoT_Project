package com.example.team_2a_security.History_Screen;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.team_2a_security.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    ArrayAdapter historyAdapter;
    ListView listView;

    //string array where alerts are added
    //any item added in array is auto added to view
    private String[] history = { "Alert on floor plan No. 3 @ XX/XX/XXX - 3:23 AM",
            "Alert on floor plan No. 6 @ XX/XX/XXX - 7:28 PM",
            "Alert on floor plan No. 4 @ XX/XX/XXX - 1:09 AM"
            };


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
        listView = view.findViewById(R.id.lv_history);
        historyAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, history);
        listView.setAdapter(historyAdapter);

    }
}
