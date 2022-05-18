package com.example.projectconsulting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.projectconsulting.R;
import com.example.projectconsulting.models.Dias;
import com.example.projectconsulting.models.Kpis;

import java.util.ArrayList;
import java.util.List;

public class AdapterPageHome extends PagerAdapter {

    private Context context;
    private ArrayList<Dias> diass;
    private ViewGroup container;

    public AdapterPageHome(Context context, ArrayList<Dias> dias) {
        this.context = context;
        this.diass = dias;
    }

    @Override
    public int getCount() {
        return diass.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_home_item, container, false);

        TextView diaTxt = view.findViewById(R.id.textoDiaSemana);
        TextView inicioHora = view.findViewById(R.id.textoHora);
        TextView finHora = view.findViewById(R.id.textoFinHora);
        TextView tasca = view.findViewById(R.id.textoTarea);
//
        Dias dias = diass.get(position);

        diaTxt.setText(dias.getDia());
        inicioHora.setText(dias.getInici());
        finHora.setText(dias.getFi());
        tasca.setText(dias.getTasca());

        container.addView(view, super.getItemPosition(dias));
        this.container = container;

        return view;
    }

    public void DeleteAllViews()
    {
        container.removeAllViews();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public void setDiass(ArrayList<Dias> diass) {
        this.diass = diass;
    }

}