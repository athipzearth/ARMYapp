package com.example.armyapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.armyapp.R;
import com.example.armyapp.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context mContext;
    private User[] mUsers;

    public UserAdapter(Context context, User[] users) {
        this.mContext = context;
        this.mUsers = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_user, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = mUsers[position];
        String date = user.date;
        holder.fullNameTextView.setText(user.name);
        //เก็บวันที่ปัจจุบันไว้ใน currentDate
        String currentDate = new SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(new Date());
        Date date1 = null;
        try {
            date1=new SimpleDateFormat("d/M/yyyy").parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        Date date2 = null;
        try {
            date2=new SimpleDateFormat("d/M/yyyy").parse(currentDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        //หาความแตกต่างระหว่างวันที่ปัจจุบันกับวันที่ใน database
        long difference = Math.abs(date1.getTime() - date2.getTime());
        //จะได้ค่าเป็น long แล้วนำไปหาร 86400000 เพราะ 1 วันมี 86400000
        holder.birthDateTextView.setText(String.valueOf(difference/86400000)+" Days");
    }

    @Override
    public int getItemCount() {
        return mUsers.length;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullNameTextView;
        TextView birthDateTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.fullNameTextView = itemView.findViewById(R.id.name);
            this.birthDateTextView = itemView.findViewById(R.id.day);

        }
    }
}
