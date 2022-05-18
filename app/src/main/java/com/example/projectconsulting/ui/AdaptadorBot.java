package com.example.projectconsulting.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectconsulting.R;

import java.util.ArrayList;

public class AdaptadorBot extends RecyclerView.Adapter<AdaptadorBot.ViewHolder> implements View.OnClickListener
{

    private ArrayList<String> frase;
    Context context;
    private View.OnClickListener listener;


    public AdaptadorBot(ArrayList<String> frase, Context context) {
        this.frase = frase;
        this.context = context;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView pregunta;

        public ViewHolder(@NonNull View item){
            super(item);
            pregunta = item.findViewById(R.id.TxtitemBoot);
        }

        void bindPlanet(String a){
            pregunta.setText(a);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.itembot, parent, false);

        item.setOnClickListener(this);

        return new ViewHolder(item);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.bindPlanet(frase.get(position));
    }

    public int getItemCount(){

        return frase.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v){
        if (listener != null){
            listener.onClick(v);
        }
    }

}