package com.example.projectconsulting.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectconsulting.MenuActivity;
import com.example.projectconsulting.R;
import com.example.projectconsulting.api.Api;
import com.example.projectconsulting.api.Services.AbpService;
import com.example.projectconsulting.databinding.FragmentGraficoalumnoBinding;
import com.example.projectconsulting.databinding.FragmentReportLayoutBinding;
import com.example.projectconsulting.databinding.ItembotBinding;
import com.example.projectconsulting.models.Botchats;
import com.example.projectconsulting.models.Usuaris;
import com.example.projectconsulting.models.Valoracions;
import com.example.projectconsulting.ui.autoevaluar.Autoevaluar;
import com.example.projectconsulting.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Report extends Fragment {
    private FragmentReportLayoutBinding binding;
    private ItembotBinding binding1;
    String codigo = "";
    Button envia;

    int casoSwitch = 0;
    int controlador = 0;
    ArrayList<String> a = new ArrayList<>();
    ArrayList<String> b = new ArrayList<>();
    ArrayList<String> c = new ArrayList<>();
    ArrayList<String> d = new ArrayList<>();
    ArrayList<String> e = new ArrayList<>();
    RecyclerView bot;
    DrawerLayout mDrawer;
    Botchats botchats;
    Usuaris user;
    RecyclerView listaAlumnos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        super.onCreate(savedInstanceState);
        binding = FragmentReportLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        LinearLayout ll = root.findViewById(R.id.evaluar_background);
        Bundle bundle = this.getArguments();
        user = (Usuaris) bundle.getSerializable("user");
        a = getArrayA();
        b = getArrayB();
        c = getArrayC();
        d = getArrayD();
        e = getArrayE();

        //bot = new RecyclerView(getContext());
        envia = binding.BotonRequest;
        EditText rescod = binding.Edtxtvalor;
        listaAlumnos = binding.RclvwA;

        AdaptadorBot adaptador = new AdaptadorBot(a, getContext());

        listaAlumnos.setHasFixedSize(true);
        listaAlumnos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listaAlumnos.setAdapter(adaptador);


        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                casoSwitch = 0;
                casoSwitch = listaAlumnos.getChildAdapterPosition(view) + 1 ;
                // listaAlumnos.item

                nuevasOpciones(casoSwitch);


            }
        });


        envia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println("Fuera");
                System.out.println("Codigo --> " + codigo);


                controlador++;

                if ((!botchats.getMensaje().equals("") || !botchats.getMensaje().equals(null) ) && botchats.getRequest() != 0) {
                    PostMessage(botchats);

                } else {
                    Toast.makeText(getContext(), "Falta algo", Toast.LENGTH_SHORT).show();

                }


            }
        });
        return root;
    }


    int caso = 0;
    int caso2 = 0;
    int caso3 = 0;
    int codigoRequest = 0;

    private void nuevasOpciones(int casoSwitch) {
        switch (casoSwitch) {
            case 1:
                AdaptadorBot adaptadorb = new AdaptadorBot(b, getContext());
                listaAlumnos.setHasFixedSize(true);
                listaAlumnos.setAdapter(adaptadorb);


                adaptadorb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        caso = 0;
                        caso = listaAlumnos.getChildAdapterPosition(v)+1;
                        listaAlumnos.getChildViewHolder(v).itemView.setBackgroundColor(Color.CYAN);
                        switch (caso) {

                            case 1:
                                listaAlumnos.getChildViewHolder(v).itemView.setBackgroundColor(Color.CYAN);

                                codigoRequest = 10;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;

                            case 2:
                                listaAlumnos.getChildViewHolder(v).itemView.setBackgroundColor(Color.CYAN);

                                codigoRequest = 11;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;


                            case 3:
                                listaAlumnos.getChildViewHolder(v).itemView.setBackgroundColor(Color.CYAN);


                                codigoRequest = 100;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;


                        }


                    }
                });


                break;
            case 2:
                AdaptadorBot adaptadorc = new AdaptadorBot(c, getContext());
                listaAlumnos.setHasFixedSize(true);
                listaAlumnos.setAdapter(adaptadorc);
                adaptadorc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        caso2 = 0;
                        caso2 = listaAlumnos.getChildAdapterPosition(v)+1;
                        listaAlumnos.getChildViewHolder(v).itemView.setBackgroundColor(Color.CYAN);
                        switch (caso2) {

                            case 1:
                                listaAlumnos.getChildViewHolder(v).itemView.setBackgroundColor(Color.CYAN);

                                codigoRequest = 20;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;


                            case 2:
                                listaAlumnos.getChildViewHolder(v).itemView.setBackgroundColor(Color.CYAN);

                                codigoRequest = 21;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;


                            case 3:
                                listaAlumnos.getChildViewHolder(v).itemView.setBackgroundColor(Color.CYAN);

                                codigoRequest = 101;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;


                        }


                    }
                });


                break;

            case 3:
                AdaptadorBot adaptadord = new AdaptadorBot(d, getContext());
                listaAlumnos.setHasFixedSize(true);
                listaAlumnos.setAdapter(adaptadord);
                adaptadord.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {


                        caso3 = 0;
                        caso3 = listaAlumnos.getChildAdapterPosition(v);






                        switch (caso3) {

                            case 1:
                                listaAlumnos.getChildViewHolder(v).itemView.setBackgroundColor(Color.CYAN);

                                codigoRequest = 30;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;


                            case 2:
                                listaAlumnos.getChildViewHolder(v).itemView.setBackgroundColor(Color.CYAN);

                                codigoRequest = 102;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;
                        }


                    }
                });
                break;


            case 4:
                AdaptadorBot adaptadore = new AdaptadorBot(e, getContext());
                listaAlumnos.setHasFixedSize(true);
                listaAlumnos.setAdapter(adaptadore);
                adaptadore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int caso4 = 0;
                        caso4 = listaAlumnos.getChildAdapterPosition(v);

                        switch (caso4) {

                            case 1:
                                codigoRequest = 40;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;


                            case 2:
                                codigoRequest = 41;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;


                            case 3:
                                codigoRequest = 103;
                                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);
                                break;
                        }
                    }
                });

                break;

            case 5:
                codigoRequest = 104;
                botchats = new Botchats(codigoRequest, user.getId(), binding.Edtxtvalor.getText().toString(), true);

                break;


            default:
                break;
        }
    }

    private ArrayList<String> getArrayA() {
        ArrayList<String> a = new ArrayList<>();
        a.add("1 - Usuario");
        a.add("2 - Valoraciones");
        a.add("3 - Graficos");
        a.add("4 - Lista de alumnos");
        a.add("5 - Otro");
        return a;
    }


    private ArrayList<String> getArrayE() {
        ArrayList<String> e = new ArrayList<>();
        e.add("1 - No puedo ver la Lista de alumnos");
        e.add("2 - No me deja evaluar otros alumnos");
        e.add("3 - Otro");
        return e;
    }

    private ArrayList<String> getArrayB() {
        ArrayList<String> b = new ArrayList<>();
        b.add("1 - No puedo cambiar mi contraseña");
        b.add("2 - Hay algún dato erroneo");
        b.add("3 - Otro");
        return b;
    }

    private ArrayList<String> getArrayC() {
        ArrayList<String> c = new ArrayList<>();
        c.add("1 - No puedo insertar valoraciones");
        c.add("2 - La skill no tiene kpis");
        c.add("3 - Otro");

        return c;
    }


    private ArrayList<String> getArrayD() {
        ArrayList<String> d = new ArrayList<>();
        d.add("1 - No puedo ver mis graficos");
        d.add("2 - Otro");

        return d;
    }


    private void PostMessage(Botchats botchats) {
        AbpService abpService = Api.getApi().create(AbpService.class);
        Botchats mensajeUsuario;
        Call<Botchats> callBot = abpService.PostMessage(botchats);

        callBot.enqueue(new Callback<Botchats>() {
            @Override
            public void onResponse(Call<Botchats> call, Response<Botchats> response) {
                int codigo = response.code();
                System.out.println(codigo);
                switch (response.code()) {
                    case 201:
                        Bundle bb = new Bundle();

                        bb.putSerializable("mensajeusuario", botchats);

                        Autoevaluar autoEvaluarActivity = new Autoevaluar();

                        HomeFragment hf = new HomeFragment();

                        pasarFragmentos(hf);

                        Toast.makeText(getContext(), "Insertado", Toast.LENGTH_SHORT).show();
                        

                        break;


                    default:
                        System.out.println(codigo);
                        try {

                            System.out.println(response.errorBody().string());
                            HomeFragment homeFragment = new HomeFragment();

                            pasarFragmentos(homeFragment);
                            Toast.makeText(getContext(), String.valueOf(codigo) + "NO INSERTADO", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;


                }
            }

            @Override
            public void onFailure(Call<Botchats> call, Throwable t) {
                t.printStackTrace();
            }

        });


    }

    public void pasarFragmentos(Fragment fragment) {


        FragmentManager fmr = getActivity().getSupportFragmentManager();

        fmr.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fmr.beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment).commit();

//        mDrawer.closeDrawers();


    }
}