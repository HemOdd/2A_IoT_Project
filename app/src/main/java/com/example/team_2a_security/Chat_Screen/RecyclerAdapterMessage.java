package com.example.team_2a_security.Chat_Screen;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_2a_security.Home_Screen;
import com.example.team_2a_security.MainActivity;
import com.example.team_2a_security.R;
import com.example.team_2a_security.data_classes.Message;
import com.example.team_2a_security.data_classes.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerAdapterMessage extends RecyclerView.Adapter<RecyclerAdapterMessage.ViewHolder>{

    private ArrayList<Message> messageData;

    public RecyclerAdapterMessage(ArrayList<Message> messageData) {
        this.messageData=messageData;
    }


    @Override
    public int getItemViewType(int position) {
        int viewType;

        if(messageData.get(position).getUsername().equals(Home_Screen.username)){
            viewType =0;
        }else{
            viewType=1;
        }
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerAdapterMessage.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        ConstraintLayout cardView;
        if(viewType==0){
            //called to instantiate the view holder
             cardView = (ConstraintLayout) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.template_message_layout_send, viewGroup, false);
            return new ViewHolder(cardView);

        }else{
            //called to instantiate the view holder
             cardView = (ConstraintLayout) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.template_message_layout_recv, viewGroup, false);
             return new ViewHolder(cardView);
        }


    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //adding data to the card views
        ConstraintLayout cardView = viewHolder.cardView;
        TextView msg = (TextView) cardView.findViewById(R.id.tv_msg);
        msg.setText(messageData.get(i).getMessage());
        TextView date = (TextView) cardView.findViewById(R.id.tv_date);
        date.setText(messageData.get(i).getDate());
        TextView sender = (TextView) cardView.findViewById(R.id.tv_sender);
        sender.setText(messageData.get(i).getUsername());


    }

    @Override
    public int getItemCount () {
        return messageData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Define view to be used for each data item
        public ConstraintLayout cardView;

        public ViewHolder(ConstraintLayout cv) {
            super(cv);
            cardView = cv;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // item clicked
                    Toast.makeText(v.getContext(),"Item clicked",Toast.LENGTH_LONG).show();
                }
            });
        }

    }

}
