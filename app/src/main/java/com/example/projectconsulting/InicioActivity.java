package com.example.projectconsulting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class InicioActivity extends AppCompatActivity {

    private File file;
    private Bundle configContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set de animaciones de introducción.
        TextView continuar = findViewById(R.id.intro_introduccionText);
        ImageView imagenIntro = findViewById(R.id.intro_introduccionImg);

        // Carga de animaciones.
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        Animation fadeInFinish = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeinfinish);

        // Inicio de animaciones.
        continuar.startAnimation(fadeIn);
        imagenIntro.startAnimation(fadeInFinish);

        // === Crear/Leer el fichero de config.txt ===
        file = new File(Environment.getDataDirectory().getAbsolutePath() + "/data/com.example.projectconsulting/config.txt");
        configContent = new Bundle();

        if (!file.exists())
        {
            try {

                file.createNewFile();

                FileWriter fw = new FileWriter(file, false);

                configContent = writeAllConfigLines(fw);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "No se ha podido crear el fichero de configuración", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            try {

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String txt = br.readLine();

                configContent = readAllConfigLines(txt, configContent, br);

                br.close();
                fr.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "No se ha encontrado el fichero de configuración", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "No se ha podido crear el fichero de configuración", Toast.LENGTH_LONG).show();
            }
        }

        LinearLayout ll = findViewById(R.id.intro_introduccionApp);

        if (configContent.getString("BackgroundColor") != null)
        {
            ll.setBackgroundColor(Color.parseColor(configContent.getString("BackgroundColor")));
        }

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, LoginActivity.class);
                intent.putExtra("configContent", configContent);
                finish();
                startActivity(intent);
            }
        });

    }

    // LEER ARCHIVO DE CONFIG
    private Bundle readAllConfigLines(String txt, Bundle configContent, BufferedReader br)
    {
        try {

            int i = 0;

            while (txt != null)
            {

                String[] txtSplited = txt.split(":");

                switch (i) {
                    case 0:
                        configContent.putBoolean("DarkMode", Boolean.parseBoolean(txtSplited[1]));
                        break;
                    case 1:
                        configContent.putString("BackgroundColor", txtSplited[1]);
                        break;
                    case 2:
                        configContent.putInt("TextSize", Integer.parseInt(txtSplited[1]));
                        break;
                    case 3:
                        configContent.putString("MenuColor", txtSplited[1]);
                        break;
                }

                txt = br.readLine();
                i++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return configContent;
    }

    // CREAR ARCHIVO DE CONFIG
    private Bundle writeAllConfigLines(FileWriter fw)
    {
        Bundle configContent = new Bundle();

        try {

            String textoTotal;
            textoTotal = "DarkMode:false" + "\n";
            textoTotal = textoTotal + "BackgroundColor:#F8F9F9" + "\n";
            textoTotal = textoTotal + "TextSize:15" + "\n";
            textoTotal = textoTotal + "MenuColor:#008037" + "\n";

            fw.write(textoTotal);
            fw.close();

            configContent.putString("BackgroundColor", "#F8F9F9");
            configContent.putBoolean("DarkMode", false);
            configContent.putInt("TextSize", 15);
            configContent.putString("MenuColor", "#008037");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return configContent;

    }

}