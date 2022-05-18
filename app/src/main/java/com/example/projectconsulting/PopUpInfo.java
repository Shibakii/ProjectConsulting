package com.example.projectconsulting;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_info_layout);

        int ancho, alto;

        // Asignando el tamaño del popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(medidasVentana);
        ancho = medidasVentana.widthPixels;
        alto = medidasVentana.heightPixels;
        // Haciendo la ventana del popup
        getWindow().setLayout( (int) (ancho * 0.75), (int) (alto * 0.50));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

}
