package com.nishita.notepad;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.Exampleviewholder> {

    private ArrayList<ExampleItem> notelist;
    private onClickListener listener;


    public static class Exampleviewholder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;

        public  Exampleviewholder(View item )
        {
            super(item);
            imageView = item.findViewById(R.id.imageview);
            textView= item.findViewById(R.id.textview);

    }}
    public ExampleAdapter(ArrayList<ExampleItem> notelist)
    {
        this.notelist=notelist;
    }
    public void setListener(onClickListener listener)
    {
        this.listener=listener;
    }


    @NonNull
    @Override
    public Exampleviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        Exampleviewholder vh=new Exampleviewholder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Exampleviewholder holder, int position) {
        final ExampleItem note=notelist.get(position);
        holder.imageView.setImageResource(note.getImageResource());
        holder.textView.setText(note.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),edit_note.class);
                v.getContext().startActivity(intent);
                }
        });
    }



    @Override
    public int getItemCount() {
        return notelist.size();
    }
}
