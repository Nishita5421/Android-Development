package com.nishita.notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.Exampleviewholder> {

    private ArrayList<ExampleItem> notelist;
    private onClickListener listener;


    public interface onClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(onClickListener dlistener)
    {
        listener=dlistener;
    }
    public static class Exampleviewholder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public String heading,content;
        private Button delete;
        public  Exampleviewholder(View item,final onClickListener dlistener)
        {
            super(item);
            imageView = item.findViewById(R.id.imageview);
            textView= item.findViewById(R.id.textview);
            delete=item.findViewById(R.id.button1);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dlistener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            dlistener.onItemClick(position);
                        }

                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dlistener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            dlistener.onDeleteClick(position);
                        }

                    }
                }
            });

    }}
    public ExampleAdapter(ArrayList<ExampleItem> notelist)
    {
        this.notelist=notelist;
    }
    public void setListener(onClickListener listener)
    {
        this.listener=listener;
    }



    @Override
    public Exampleviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        Exampleviewholder vh=new Exampleviewholder(v,listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Exampleviewholder holder, final int position) {
        ExampleItem note=notelist.get(position);
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
