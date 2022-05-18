package com.example.projectconsulting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectconsulting.api.Api;
import com.example.projectconsulting.api.Services.AbpService;
import com.example.projectconsulting.models.Cursos;
import com.example.projectconsulting.models.Grup;

import com.example.projectconsulting.models.Grups_has_alumnes;
import com.example.projectconsulting.models.Grups_has_docents;
import com.example.projectconsulting.models.Grups_has_llistes_skills;
import com.example.projectconsulting.models.Usuaris;
import com.google.gson.Gson;

import org.mindrot.jbcrypt.BCrypt;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Connection connect;
    List<Usuaris> userLogeando;
    List<Cursos> allCursos;
    Usuaris usuari;
    Button loginButton;
    CheckBox mostrarcontraseña;
    Button contraseña;
    EditText contra;
    EditText usuario;
    Toast toast;
    List<Grup> grupos = new ArrayList<>();
    List<Grups_has_alumnes> gha;
    List<Grups_has_docents> ghd;
    List<Grups_has_llistes_skills> ghl;
    List<Grup> pasoGrupo = new ArrayList<>();
    List<Cursos> cursoFiltrado = new ArrayList<>();
    Cursos curso;
    boolean todoCorrecto = false;
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String username = "jguius2021@cepnet.net";
    public static final String password = "pepe";
    private  boolean showPassword = false;

    Bundle bundleConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        bundleConfig = intent.getBundleExtra("configContent");
        obtenerGrupos();

        if (bundleConfig.getString("BackgroundColor") != null)
        {
            LinearLayout ll = findViewById(R.id.login_background);
            ll.setBackgroundColor(Color.parseColor(bundleConfig.getString("BackgroundColor")));
        }

        loginButton = findViewById(R.id.login_loginButton);
        contra = findViewById(R.id.contrasenya);
        mostrarcontraseña = findViewById(R.id.mostrarcontrasenia);
        usuario = findViewById(R.id.usuario);
        toast = Toast.makeText(this, "La contraseña o el usuario no son correctos", Toast.LENGTH_SHORT);

        contra.setTransformationMethod(PasswordTransformationMethod.getInstance());
        showPassword = false;

        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        mostrarcontraseña.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(showPassword)
                {
                    contra.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword = false;
                }
                else
                {
                    contra.setTransformationMethod(null);
                    showPassword = true;
                }
            }
        });



//        if(sharedPreferences.contains(username)){
//            obtenerUsuario();
//            obtenerGrupos();
//            Intent i =  new Intent(LoginActivity.this, MenuActivity.class);
//            i.putExtra("user", usuari);
//            i.putExtra("grupsUser", (Serializable) pasoGrupo);
//            startActivity(i);
//
//        }else{
//            LoginActivity loginActivity = new LoginActivity();
//            loginActivity.onRestart();
//        }



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerGrupos();
                obtener();


            }
        });

        ImageButton infoButton = findViewById(R.id.login_infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PopUpInfo.class);
                startActivity(intent);
            }
        });
    }


    public void obtenerCurso(){
        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<List<Cursos>> cursosCall = abpService.getCursos(1);

        cursosCall.enqueue(new Callback<List<Cursos>>() {
            @Override
            public void onResponse(Call<List<Cursos>> call, Response<List<Cursos>> response) {
                switch(response.code()){

                    case 200:

                        List<Cursos> cursos1 = response.body();
                        curso = cursos1.get(0);
                        break;

                    case 400:
                        Toast.makeText(LoginActivity.this, "400", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(LoginActivity.this, "oops", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onFailure(Call<List<Cursos>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    public void obtenerGrupos() {
        AbpService abpService = Api.getApi().create(AbpService.class);

        Call<List<Grup>> grupsCall = abpService.getGrup();
        grupos = new ArrayList<>();
        grupsCall.enqueue(new Callback<List<Grup>>() {
            @Override
            public void onResponse(Call<List<Grup>> call, Response<List<Grup>> response) {
                switch (response.code()) {

                    case 200:
                        grupos = response.body();
                        break;


                    case 400:
                        Toast.makeText(LoginActivity.this, "400", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(LoginActivity.this, "oops", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onFailure(Call<List<Grup>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }



    public void obtenerUsuario() {

        AbpService abpService = Api.getApi().create(AbpService.class);
        String correo = usuario.getText().toString();
        Call<List<Usuaris>> userCall = abpService.getNom(correo + "/");


        userCall.enqueue(new Callback<List<Usuaris>>() {
            @Override
            public void onResponse(Call<List<Usuaris>> call, Response<List<Usuaris>> response) {
                switch (response.code()) {
                    case 200:
                        userLogeando = response.body();
                        String correo = usuario.getText().toString();
                        String pass = contra.getText().toString();

                        if (userLogeando.size() != 0) {
                            for (Usuaris item : userLogeando) {
                                try {
                                    if ((username.equals(item.getCorreu())) && BCrypt.checkpw(password, item.getContrasenia())) {
                                        usuari = item;
                                        todoCorrecto = true;
                                        Toast.makeText(LoginActivity.this, "Log Exitoso", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, "No es correcto", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        if (todoCorrecto) {
                            gha =  usuari.getGrups_has_alumnes();
                            ghd =  usuari.getGrups_has_docents();

                            if(usuari.getRols_id() == 4){
                                for (Grups_has_alumnes item2: gha) {
                                    for (Grup item: grupos) {
                                        if (item2.getGrups_id() == item.getId()){
                                            pasoGrupo.add(item);
                                        }
                                    }
                                }
                            }else if(usuari.getRols_id() == 2 || usuari.getRols_id() == 3){
                                for (Grups_has_docents item2: ghd) {
                                    for (Grup item: grupos) {
                                        if (item2.getGrups_id() == item.getId()){
                                            pasoGrupo.add(item);
                                        }
                                    }
                                }
                            }

                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            intent.putExtra("user", usuari);
                            intent.putExtra("grupsUser", (Serializable) pasoGrupo);
                            intent.putExtra("cursos",(Serializable) cursoFiltrado);
                            intent.putExtra("configContent", bundleConfig);
                            startActivity(intent);

                        } else
                        {

                            Toast.makeText(LoginActivity.this, "No existe", Toast.LENGTH_LONG).show();
                        }


                        break;
                    case 400:
                        Toast.makeText(LoginActivity.this, "400", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(LoginActivity.this, "oops", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }


            }

            @Override
            public void onFailure(Call<List<Usuaris>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }


        });
    }

    public void obtener() {

        AbpService abpService = Api.getApi().create(AbpService.class);
        String correo = usuario.getText().toString();
        Call<List<Usuaris>> userCall = abpService.getNom(correo + "/");


        userCall.enqueue(new Callback<List<Usuaris>>() {
            @Override
            public void onResponse(Call<List<Usuaris>> call, Response<List<Usuaris>> response) {
                switch (response.code()) {
                    case 200:
                        userLogeando = response.body();
                        String correo = usuario.getText().toString();
                        String pass = contra.getText().toString();

                        if (userLogeando.size() != 0) {
                            for (Usuaris item : userLogeando) {
                                try {
                                   if ((correo.equals(item.getCorreu())) && BCrypt.checkpw(pass, item.getContrasenia())) {
                                        usuari = item;
                                        todoCorrecto = true;
                                        Toast.makeText(LoginActivity.this, "Log Exitoso", Toast.LENGTH_SHORT).show();
                                        System.out.println(userLogeando.get(0).getNom());
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username, correo);
                                        editor.putString(password, pass);
                                        editor.commit();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, "No es correcto", Toast.LENGTH_SHORT).show();
                                }
                            }



                        }

                        if (todoCorrecto) {
                            gha =  usuari.getGrups_has_alumnes();
                            ghd =  usuari.getGrups_has_docents();

                            if(gha!=null){

                                if(usuari.getRols_id() == 4){
                                    for (Grups_has_alumnes item2: gha) {
                                        for (Grup item: grupos) {
                                            if (item2.getGrups_id() == item.getId()){
                                                pasoGrupo.add(item);
                                            }
                                        }
                                    }
                                }

                            }

                            if(ghd != null){
                                if(usuari.getRols_id() == 2 || usuari.getRols_id() == 3){
                                    for (Grups_has_docents item2: ghd) {
                                        for (Grup item: grupos) {
                                            if (item2.getGrups_id() == item.getId()){
                                                pasoGrupo.add(item);
                                            }
                                        }
                                    }
                                }
                            }




                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            intent.putExtra("configContent", bundleConfig);
                            intent.putExtra("user", usuari);
                            intent.putExtra("grupsUser", (Serializable) pasoGrupo);
                            intent.putExtra("cursos",(Serializable) cursoFiltrado);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Login Incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    break;
                    case 400:
                        Toast.makeText(LoginActivity.this, "Error de connexion", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(LoginActivity.this, "No existe", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }


            }

            @Override
            public void onFailure(Call<List<Usuaris>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }


        });
    }
    private void obtenerAllCursos()
    {
        AbpService abpService = Api.getApi().create(AbpService.class);

        Call<List<Cursos>> userCall = abpService.getAllCursos();


        userCall.enqueue(new Callback<List<Cursos>>()
        {
            @Override
            public void onResponse(Call<List<Cursos>> call, Response<List<Cursos>> response)
            {
                switch(response.code())
                {
                    case 200:
                        allCursos = response.body();

                        if(allCursos.size()!=0)
                        {
                            for(Cursos curs : allCursos)
                            {
                                if(curs.isActiu() != false)
                                {
                                    cursoFiltrado.add(curs);
                                }
                            }
                        }
                        break;
                    case 400:
                        Toast.makeText(LoginActivity.this, "400", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(LoginActivity.this, "oops", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Cursos>> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}





