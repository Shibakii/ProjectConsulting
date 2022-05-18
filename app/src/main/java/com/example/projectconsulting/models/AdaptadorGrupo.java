package com.example.projectconsulting.models;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.projectconsulting.R;

import java.util.List;

public class AdaptadorGrupo extends BaseAdapter implements SpinnerAdapter {
    private Activity activity;
    private List<Grup> list_bsl;

    public AdaptadorGrupo(Activity activity, List<Grup> list_bsl) {
        this.activity = activity;
        this.list_bsl = list_bsl;
    }

    public int getCount() {
        return list_bsl.size();
    }

    public Object getItem(int position) {
        return list_bsl.get(position);
    }

    public long getItemId(int position) {
        return list_bsl.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View spinView;
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            spinView = inflater.inflate(R.layout.fragment_listalumnos, null);
        } else {
            spinView = convertView;
        }
        TextView t1 = (TextView) spinView.findViewById(R.id.txtitemgrupo);
        t1.setText(String.valueOf(list_bsl.get(position).getNom()));
        return spinView;
    }
}