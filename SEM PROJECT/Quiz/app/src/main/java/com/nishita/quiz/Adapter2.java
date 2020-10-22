package com.nishita.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder2> {

    private ArrayList<ClassCard> mClassCard;
    public static class ViewHolder2 extends RecyclerView.ViewHolder{

        public ImageView mImageView1;
        public TextView mCourseName1;
        public TextView mAccessCode;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            mImageView1 = itemView.findViewById(R.id.course_bg);
            mCourseName1 = itemView.findViewById(R.id.ClassName);
            mAccessCode = itemView.findViewById(R.id.AccessCode);
        }
    }

    public Adapter2(ArrayList<ClassCard> classCard){
        mClassCard = classCard;
    }

    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_card, parent, false);
        ViewHolder2 vh2 = new ViewHolder2(view);
        return vh2;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {
        ClassCard currentCard = mClassCard.get(position);
        holder.mImageView1.setImageResource(currentCard.getImageResource1());
        holder.mCourseName1.setText(currentCard.getCourse1());
        holder.mAccessCode.setText(currentCard.getCode());
    }

    @Override
    public int getItemCount() {
        return mClassCard.size();
    }
}
