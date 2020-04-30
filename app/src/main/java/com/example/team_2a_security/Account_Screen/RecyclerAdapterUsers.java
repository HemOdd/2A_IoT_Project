package com.example.team_2a_security.Account_Screen;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_2a_security.Chat_Screen.RecyclerAdapterMessage;
import com.example.team_2a_security.Home_Screen;
import com.example.team_2a_security.MainActivity;
import com.example.team_2a_security.R;
import com.example.team_2a_security.data_classes.Users;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerAdapterUsers extends RecyclerView.Adapter<RecyclerAdapterMessage.ViewHolder>{

    private ArrayList<Users> usersData;
    ConstraintLayout inflatedCard;


    public RecyclerAdapterUsers(ArrayList<Users> UsersData) {
        this.usersData =UsersData;

    }


    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        return viewType;
    }

    @NonNull
    @Override
    public RecyclerAdapterMessage.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        ConstraintLayout card;
        //called to instantiate the view holder
        card = (ConstraintLayout) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.template_user_account_status, viewGroup, false);
        return new RecyclerAdapterMessage.ViewHolder(card);



    }

    public void onBindViewHolder(@NonNull RecyclerAdapterMessage.ViewHolder viewHolder, int i) {
        //adding data to the card views
        inflatedCard = viewHolder.cardView;
        TextView username = (TextView) inflatedCard.findViewById(R.id.tv_userName);
        username.setText(usersData.get(i).getUsername());
        TextView status = (TextView) inflatedCard.findViewById(R.id.tv_userStatus);
        //change active/inactive view
        if(usersData.get(i).getStatus()){
            status.setText(R.string.user_active);
            status.setTextColor(viewHolder.itemView.getContext().getResources().getColor(R.color.User_active));
        }else{
            status.setText(R.string.user_inactive);
            status.setTextColor(viewHolder.itemView.getContext().getResources().getColor(R.color.User_inactive));
        }
        //status.setText(usersData.get(i).getDate());
        Button logOut = (Button) inflatedCard.findViewById(R.id.btn_logOutButton);
        //only show log out button for logged in user
        if(Home_Screen.username.equals(usersData.get(i).getUsername())){
            //implement log out button
            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(inflatedCard.getContext(), MainActivity.class);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(Home_Screen.userKEY).child("status").setValue(false);
                    inflatedCard.getContext().startActivity(intent);
                }
            });

        }else{
            logOut.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount () {
        return usersData.size();
    }

}
