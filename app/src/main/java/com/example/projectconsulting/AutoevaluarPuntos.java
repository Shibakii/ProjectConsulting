package com.example.projectconsulting;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.projectconsulting.api.Api;
import com.example.projectconsulting.api.Services.AbpService;
import com.example.projectconsulting.databinding.FragmentAutevaluarpuntosBinding;
import com.example.projectconsulting.databinding.FragmentAutevaluarpuntosBinding;
import com.example.projectconsulting.models.Kpis;
import com.example.projectconsulting.models.Skills;
import com.example.projectconsulting.models.Usuaris;
import com.example.projectconsulting.models.Valoracions;
import com.example.projectconsulting.ui.autoevaluar.AdaptadorView;
import com.example.projectconsulting.ui.autoevaluar.Autoevaluar;
import com.example.projectconsulting.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class

AutoevaluarPuntos extends Fragment {

    int currentProgress = 50;
    ProgressBar progresso;
    SeekBar seek;
    TextView texto1;
    TextView textoAlumno;
//    TextView pantallaNombres;
//    TextView pantallaNotas;

    private ActionBar actionBar;
    ViewPager viewPager;
    //    private ArrayList<Kpis> kpisObtenidas;
    private AdaptadorView adaptador;

    private int posicionKpi = 0;
    List<Kpis> kpisfiltradas;
    ArrayList<Kpis> kpis = new ArrayList<>();
    List<Valoracions> valoracions = new ArrayList<>();
    List<Valoracions> valoracionsadd = new ArrayList<>();
    Valoracions valoracion;
    List<Kpis> kpi;
    List<Skills> skills = new ArrayList<>();
    Skills skill;
    private ArrayList<Kpis> kpisPuntuadas = new ArrayList<>();
    private ArrayList<Integer> notas = new ArrayList<>();
    ArrayList<String> nombres = new ArrayList<>();
    String notasVer = "";
    String nombresVer = "";
    DrawerLayout mDrawer;
    FloatingActionButton fabGuardar;
    Usuaris user;
    int idSkill;
    Skills skillPasada;
    Usuaris usuarioAPuntuar;
    Boolean kpisSinPuntuar = false;
    Boolean sePuntua = false;
    FragmentAutevaluarpuntosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews();

        binding = FragmentAutevaluarpuntosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle configContent = ((MenuActivity) getActivity()).getBundleConfig();

        if (configContent.getString("BackgroundColor") != null) {
            LinearLayout ll = binding.evaluarBackground;
            ll.setBackgroundColor(Color.parseColor(configContent.getString("BackgroundColor")));
        }

        getKpis();
        Bundle bundle = this.getArguments();
        user = (Usuaris) bundle.getSerializable("user");
        skills = (List<Skills>) bundle.getSerializable("skills");
        valoracion = (Valoracions) bundle.getSerializable("valoracion");
        idSkill = (int) bundle.getSerializable("idSkill");
        usuarioAPuntuar = (Usuaris) bundle.getSerializable("usuarioAPuntuar");
        skillPasada = (Skills) bundle.getSerializable("skill");

        ((MenuActivity) getActivity()).usuarioPuntuar = usuarioAPuntuar;

        fabGuardar = binding.FltBtnEvalua;


        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean controlEvaluador;

                if (kpisPuntuadas.size() == kpis.size()) {
                    Snackbar.make(view, "Enviado", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    for (int i = 0; i < kpis.size(); i++) {
                        controlEvaluador = false;
                        int notaInsertar = 0;
                        Kpis kpisInsertar = null;
                        Kpis comparativa = kpis.get(i);


                        for (Kpis item : kpisPuntuadas) {

                            if (item.getNom().equals(comparativa.getNom())) {
                                controlEvaluador = true;
                                notaInsertar = notas.get(i);
                                kpisInsertar = item;
                            }

                        }

                        if (controlEvaluador) {
                            //Añadir valoraciones en grupo - tenemos la kpi y tenemos la nota ordenados por posiciones
                            //Hacemos el insert o put;
                            System.out.println(notaInsertar + "-----" + kpisInsertar.getNom());
                        }

//                    kpisPuntuadas = new ArrayList<>();
//                    notas = new ArrayList<>();
//                    nombres = new ArrayList<>();
//                    pantallaNombres.setText("");
//                    pantallaNotas.setText("");

                    }

                    for (Valoracions item : valoracions) {
                        valoracion = item;
                        valoracion.setData(LocalDateTime.now().toString()); // NO PETA
                        valoracionsadd.add(item);
                        //////////////////////////////////////////////---------------------------

                    }

                    Postvaloracions(valoracionsadd);
                } else {
                    Snackbar.make(view, "No se han evaluado todas las KPIS", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }

        });

        progresso = binding.Pgrs1;
        seek = binding.Seek1;
//        texto1 = binding.TxtPuntos;
        textoAlumno = binding.TxtPuntuacionAlumno;
//        pantallaNotas = binding.TxtEvaluacionFinal;
//        pantallaNombres = binding.TxtEvaluacionFinalNombres;

        seek.setMax(100);
        seek.setProgress(currentProgress);
        //progresso.setMax(50);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        viewPager = binding.ViewPager;


////////////////////////////////////////////////////////////////////////////////////////////////////

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

                String titul = "AutoEvaluar - " + kpis.get(position).getNom();
                actionBar.setTitle(titul);
                posicionKpi = position;
                if(!binding.checkBoxNoContestar.isChecked()){
                    sePuntua=true;
                }else{
                    sePuntua=false;
                }

                      /*Toast.makeText(MainActivity.this, "Puntuado"+currentProgress+" a --> " +
                        kpis.get(posicionKpi).getNom(), Toast.LENGTH_SHORT).show();*/
                if (kpisPuntuadas == null) {
                    kpisPuntuadas.add(kpis.get(posicionKpi));
                    notas.add(currentProgress);
                    nombres.add(kpis.get(posicionKpi).getNom());
                } else {
                    Kpis provisional = null;
                    boolean control = true;
                    for (Kpis item : kpisPuntuadas) {
                        if (item.getNom().equals(kpis.get(posicionKpi).getNom())) {
                            control = false;
                        }
                    }
                    if (!control) {
                        notas.set(posicionKpi, currentProgress);
                    } else {
                        provisional = kpis.get(posicionKpi);

                    }


                    Date date = new Date();


                    if (provisional != null  ) {

                        EditText et = root.findViewById(R.id.txtObersvacion);

                        kpisPuntuadas.add(provisional);
                        notas.add(currentProgress);
                        nombres.add(provisional.getNom());
                        Calendar rightNow = Calendar.getInstance();
                        Valoracions valoracion = new Valoracions(null, null, kpis.get(posicionKpi).getId(), user.getId(),
                                user.getId(), LocalDate.now().toString(), notas.get(posicionKpi), et.getText().toString());

                        valoracion.setEsPuntuado(sePuntua);

                        valoracions.add(valoracion);
                        valoracion = null;


                    }
                }

                binding.checkBoxNoContestar.setChecked(false);

                notasVer = "";
                for (Integer item : notas) {
                    notasVer += item.toString() + "\n";
                }
//                pantallaNotas.setText(notasVer);
                nombresVer = "";
                for (String item : nombres) {
                    nombresVer += item + "\n";
                }
//                pantallaNombres.setText(nombresVer);



            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                texto1.setText(progress + "/" + seekBar.getMax());

                currentProgress = progress;
                progresso.setProgress(currentProgress);

                float nota = progress * 0.1f;
                if (nota >= 0 && nota <= 1) {
                    textoAlumno.setText("NO");
                    textoAlumno.setTextColor(Color.parseColor("#FF0000"));
                } else if (nota > 1 && nota <= 4) {
                    textoAlumno.setText("POCO");
                    textoAlumno.setTextColor(Color.parseColor("#FF8000"));
                } else if (nota > 4 && nota <= 7) {
                    textoAlumno.setText("SI");
                    textoAlumno.setTextColor(Color.parseColor("#6f8f00"));
                } else {
                    textoAlumno.setText("MUCHO");
                    textoAlumno.setTextColor(Color.parseColor("#31a84f"));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {



                Toast.makeText(getContext(), "Puntuando...", Toast.LENGTH_LONG).show();
            }



            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {




            }


        });


        return root;
    }


    private void getKpis() {
        AbpService abpService = Api.getApi().create(AbpService.class);

        retrofit2.Call<List<Kpis>> userCall = abpService.getKpis();


        userCall.enqueue(new Callback<List<Kpis>>() {

            @Override
            public void onResponse(retrofit2.Call<List<Kpis>> call, Response<List<Kpis>> response) {
                switch (response.code()) {
                    case 200:
                        List<Kpis> kpisTodas = response.body();


                        if (kpisTodas.size() != 0) {
                            for (int i = 0; i < kpisTodas.size(); i++) {

                                if (kpisTodas.get(i).getSkills_id() ==
                                        skillPasada.getId()) {
                                    Kpis kpi = kpisTodas.get(i);
                                    kpis.add(kpi);
                                }
                            }
                        }


                        loadCards();
                        break;
                    case 400:
                        Toast.makeText(fabGuardar.getContext(), "400", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(fabGuardar.getContext(), "oops", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onFailure(retrofit2.Call<List<Kpis>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


    }


//    private ArrayList KpisPorSkill()
//    {
//        int i = 0;
//        ArrayList<Skills> reciclable = new ArrayList();
//            for(Skills skillcomparable : skills)
//            {
//                if(skills.get(i).getId() == kpis.get(i).getSkills_id())
//                {
//                    reciclable.add(skillcomparable);
//                    i++;
//
//                }
//            }
//        return reciclable;
//        }

    private void loadCards() {
//        kpisObtenidas = (ArrayList<Kpis>) KpisPorSkill();

        adaptador = new com.example.projectconsulting.ui.autoevaluar.AdaptadorView(fabGuardar.getContext(), (ArrayList<Kpis>) kpis);
        viewPager.setAdapter(adaptador);
        viewPager.setPadding(100, 0, 100, 0);
    }

    int contador = 0;

    private void Postvaloracions(List<Valoracions> valoracions) {
        AbpService abpService = Api.getApi().create(AbpService.class);

//        valoracion.setData(LocalDateTime.parse("2022-04-1303:22:00"));
        Valoracions valoracionInsertar;

        for (Valoracions val : valoracions) {
            if (val.isEsPuntuado() == true) {
                Call<Valoracions> valoracionsCall = abpService.Postvaloracions(val);


                valoracionsCall.enqueue(new Callback<Valoracions>() {
                    @Override
                    public void onResponse(Call<Valoracions> call, Response<Valoracions> response) {
                        int codigo = response.code();
                        System.out.println(codigo);
                        switch (response.code()) {
                            case 201:
                                //Toast.makeText(getContext(), "INSERTADO", Toast.LENGTH_SHORT).show();
                                //Bundle bb = new Bundle();

                                //bb.putSerializable("valoracionpuntuada", valoracion);

//                        Autoevaluar autoEvaluarActivity = new Autoevaluar();
//                        HomeFragment hf = new HomeFragment();
//                        pasarFragmentos(hf, bb);

                                //Toast.makeText(getContext(), "Insertado", Toast.LENGTH_SHORT).show();

//                            HomeFragment homeFragment1 = new HomeFragment();
//                            Bundle bundle2  = new Bundle();
//                            bundle2.putSerializable("user", user);
//                            bundle2.putSerializable("grupos",((MenuActivity) getActivity()).getUser() );
//                            homeFragment1.setArguments(bundle2);
//
//                            pasarFragmentos(homeFragment1, bundle2);


                                break;
                            case 400:
                                System.out.println("400");
                                break;


                            case 404:
                                System.out.println("404");
                                break;

                            case 401:
                                System.out.println("401");
                                break;


                            default:
                                System.out.println(codigo);
                                try {

                                    System.out.println(response.errorBody().string());

//                                HomeFragment homeFragment = new HomeFragment();
//                                Bundle bundle1  = new Bundle();
                                    //bundle1.putSerializable("user", user);
                                    //bundle1.putSerializable("grupos",((MenuActivity) getActivity()).getUser());
                                    //homeFragment.setArguments(bundle1);

                                    //pasarFragmentos(homeFragment, bundle1);
                                    //Toast.makeText(getContext(), "Insertado", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;

                        }
                    }

                    @Override
                    public void onFailure(Call<Valoracions> call, Throwable t) {
                        Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        HomeFragment homeFragment1 = new HomeFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("user", user);
        bundle2.putSerializable("grupos", ((MenuActivity) getActivity()).getUser());
        homeFragment1.setArguments(bundle2);

        FragmentManager fmg = getActivity().getSupportFragmentManager();
        fmg.beginTransaction().replace(R.id.nav_host_fragment_content_main, homeFragment1).commit();

    }

    private void updateValoracion() {
//        Toast.makeText(fabGuardar.getContext(), "UPDATE!!" + valoracion.getKpis(), Toast.LENGTH_LONG).show();
        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<Valoracions> valoracionsCall = abpService.updateValoracion((int) valoracion.getKpis_id(), valoracion);
        valoracionsCall.enqueue(new Callback<Valoracions>() {
            @Override
            public void onResponse(Call<Valoracions> call, Response<Valoracions> response) {
                switch (response.code()) {

                }
            }

            @Override
            public void onFailure(Call<Valoracions> call, Throwable t) {

            }
        });

    }

    public void pasarFragmentos(Fragment fragment, Bundle bundle) {

        fragment.setArguments(bundle);

        FragmentManager fmr = getActivity().getSupportFragmentManager();

        fmr.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fmr.beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment).commit();


    }

}