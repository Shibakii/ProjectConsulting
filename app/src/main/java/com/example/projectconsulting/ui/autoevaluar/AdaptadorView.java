package com.example.projectconsulting.ui.autoevaluar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.projectconsulting.R;
import com.example.projectconsulting.models.Kpis;

import java.util.ArrayList;

public class AdaptadorView extends PagerAdapter {

    private Context context;
    private ArrayList<Kpis> kpis;

    public AdaptadorView(Context context, ArrayList<Kpis> kpis) {
        this.context = context;
        this.kpis = kpis;
    }


    @Override
    public int getCount() {
        return kpis.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //inflarlayout
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, container, false);

        TextView titul = view.findViewById(R.id.TxtTitul);
        TextView desc = view.findViewById(R.id.TxtDescrip);

        Kpis kpis = this.kpis.get(position);
        desc.setText(kpis.getNom());
        titul.setText(kpis.getSkills());

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "seleccionada la "+ kpis.getNom(), Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
