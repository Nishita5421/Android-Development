package com.nishita.quiz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> {
private List<Modal> list;
private Context context;

    public Adapter(List<Modal> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new viewholder(view) ;

    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, int position) {
        Modal item=list.get(position);
        holder.imageView.setImageResource(item.getImageResource());
        holder.textView.setText(item.getText());


        }

        @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressWarnings("deprecation")
    public class viewholder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;

        public viewholder(@NonNull View itemview)
        {
            super(itemview);
            imageView=itemview.findViewById(R.id.imageview);
            textView=itemview.findViewById(R.id.textview4);
            context=itemview.getContext();
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (getAdapterPosition()+1);
                    Toast.makeText(v.getContext(),"Clicked on Question"+position,Toast.LENGTH_SHORT).show();

                    Intent inte=new Intent(context, Questions.class);
                    inte.putExtra("Questions",position);
                    context.startActivity(inte);
                }
            });
        }

    }
}
