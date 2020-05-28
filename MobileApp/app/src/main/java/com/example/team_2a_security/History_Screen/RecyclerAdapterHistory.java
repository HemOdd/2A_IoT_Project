package com.example.team_2a_security.History_Screen;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_2a_security.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterHistory extends RecyclerView.Adapter<RecyclerAdapterHistory.ViewHolder>{
    List<Sensor> payload = new ArrayList<>();

    public RecyclerAdapterHistory ()
    {
    }

    public void setPayload(List<Sensor> payload) {
        this.payload = payload;
    }

    @NonNull
    @Override
    public RecyclerAdapterHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_history_layout, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterHistory.ViewHolder holder, int position) {
        String tempIdHolder = String.valueOf(payload.get(position).getId());
        holder.alertMsg.setText("Alert from: " + payload.get(position).getSensorType() + "(" + tempIdHolder+ ")");
        holder.dateTime.setText(payload.get(position).getDateTime());
        holder.location.setText(payload.get(position).getLocation());
        holder.valueMsg.setText(String.valueOf(payload.get(position).getValue()));

        final int fPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() { //set back to itemView for students
            @Override public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return payload.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        //        public ImageView itemImage;
        public TextView alertMsg;
        public TextView dateTime;
        public TextView location;
        public TextView valueMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
//            itemImage = itemView.findViewById(R.id.item_image);
            valueMsg = itemView.findViewById(R.id.value);
            alertMsg = itemView.findViewById(R.id.alert_msg);
            dateTime = itemView.findViewById(R.id.dateTime);
            location = itemView.findViewById(R.id.locationHistory);

        }
    }

}
