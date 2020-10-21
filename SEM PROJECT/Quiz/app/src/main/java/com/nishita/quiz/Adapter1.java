package com.nishita.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder1> {

    private ArrayList<CardView> mCardView = null;
    public static class ViewHolder1 extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mCourseName;
        public TextView mSchoolName;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.ImageView);
            mCourseName = itemView.findViewById(R.id.CourseName);
            mSchoolName = itemView.findViewById(R.id.SchoolName);
        }
    }

    public Adapter1(ArrayList<CardView> cardView){
        mCardView = cardView;
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder1 vh = new ViewHolder1(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        CardView currentCard = mCardView.get(position);
        holder.mImageView.setImageResource(currentCard.getImageResource());
        holder.mCourseName.setText(currentCard.getCourse());
        holder.mSchoolName.setText(currentCard.getSchool());
    }

    @Override
    public int getItemCount() {
        return mCardView.size();
    }
}
