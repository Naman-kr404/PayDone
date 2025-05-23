package com.pay.done;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerNotifyAdapter extends RecyclerView.Adapter<RecyclerNotifyAdapter.ViewHolder> {
    Context context;
    ArrayList<NotifyModel> arrContacts;
    RecyclerNotifyAdapter(Context context, ArrayList<NotifyModel> arrContacts){
        this.context=context;
        this.arrContacts=arrContacts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notification_layout,parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bankImg.setImageResource(arrContacts.get(position).img);
        holder.txtAmtBank.setText(arrContacts.get(position).bank);
        holder.txtTime.setText(arrContacts.get(position).time);
    }

    @Override
    public int getItemCount() {
        return arrContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAmtBank,txtTime;
//        ImageView bankImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAmtBank=itemView.findViewById(R.id.txtAmtBank);
            txtTime=itemView.findViewById(R.id.txtTime);
//            bankImg=itemView.findViewById(R.id.bankImg);
        }
    }
}
