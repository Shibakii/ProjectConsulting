package com.example.projectconsulting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectconsulting.models.Grup;
import com.example.projectconsulting.models.Usuaris;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;


public class UsuarisAdapter extends RecyclerView.Adapter<UsuarisAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Usuaris> usuaris;
    private static Context context;
    private static Grup grupo;
    private static Usuaris user;

    public UsuarisAdapter(ArrayList<Usuaris> usuaris, Context context, Grup grupo, Usuaris user) {
        this.context = context;
        this.usuaris = usuaris;
        this.grupo = grupo;
        this.user = user;
    }

    private View.OnClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView lblNom;
        TextView correu;
        ImageView img;


        public ViewHolder(@NonNull View item) {
            super(item);

            lblNom = item.findViewById(R.id.item_list_nameStudent);
            correu = item.findViewById(R.id.item_list_mailStudent);
            img = item.findViewById(R.id.item_list_imgStudent);
        }

        void bindPlanet(Usuaris usuari) {

            if (grupo != null && usuari != null) {
                int idgrupo = grupo.getId();
//                for (int i = 0; i < usuari.getGrups_has_alumnes().size(); i++) {

//                    if (usuari.getGrups_has_alumnes().get(i).getGrups_id() == idgrupo) {
                        lblNom.setText(usuari.getNom());
                        correu.setText(usuari.getCorreu());
////            img.setImageBitmap(usuari.getImagen());
//                        Picasso.get().load("abp-politecnics.com/2022/dam02/imagenesusuario/" + usuari.getDni() + ".jpg").into(img);
//                    }

//                }

            }else{
                lblNom.setText("nada" + usuari.getNom());
                correu.setText("nada"+ usuari.getCorreu());
            }


        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_listalumnos, parent, false);

        item.setOnClickListener(this);

        return new ViewHolder(item);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindPlanet(usuaris.get(position));
        holder.img = holder.itemView.findViewById(R.id.imageView);
        //  Picasso.get().load("ftp://dam_02%40abp-politecnics.com@ftp.onwindows-es.setupdns.net/imagenesusuario/" + usuaris.get(position).getDni()+".jpg").into(holder.img);
    }

    public int getItemCount() {

        return usuaris.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }
}