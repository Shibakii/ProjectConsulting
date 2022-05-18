package com.example.projectconsulting.ui.autoevaluar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectconsulting.GraficoAlumno;
import com.example.projectconsulting.R;
import com.example.projectconsulting.api.Api;
import com.example.projectconsulting.api.Services.AbpService;
import com.example.projectconsulting.databinding.FragmentAutoevaluarBinding;
import com.example.projectconsulting.models.Grup;
import com.example.projectconsulting.models.Grups_has_llistes_skills;
import com.example.projectconsulting.models.Llistes_skills;
import com.example.projectconsulting.models.Skills;
import com.example.projectconsulting.models.Usuaris;
import com.example.projectconsulting.models.Valoracions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Autoevaluar extends Fragment {

    private FragmentAutoevaluarBinding binding;
    List<Skills> skillsfiltradas;
    Valoracions valoracionafiltrar;
    List<Valoracions> valoraciones;
    DrawerLayout mDrawer;
    Usuaris userValuar;
    Usuaris user;
    Llistes_skills listaSkillsObjeto;
    ArrayList<Grup> grupos;
    Grup grupo;
    List<Llistes_skills> llistes_skills = new ArrayList<>();
    List<Grups_has_llistes_skills> grups_has_llistes_skills;
    Grups_has_llistes_skills listaSkill;
    boolean bien = false;
    int i;
    int id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        container.removeAllViews();
        binding = FragmentAutoevaluarBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        Bundle bundle = this.getArguments();

        grupos = null;
        user = (Usuaris) bundle.getSerializable("user");
        grupos = (ArrayList<Grup>) bundle.getSerializable("grupsUser");

        ImageButton consultarStat = binding.consultarStats;
        if (grupos.size() != 0) {
            Spinner spinner = binding.spinnerGrupos;
            ArrayAdapter<Grup> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, grupos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    grupo = (Grup) spinner.getSelectedItem();
                    getGrupsHasLlistesSkills(grupo);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


//            getSkills();
        }


//
//
//
//
//        consultarStat.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction fragmentManager = getParentFragmentManager().beginTransaction();
//                //Fragment frag = getParentFragmentManager().findFragmentById(R.id.graficos);
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                fragmentManager.replace(R.id.nav_host_fragment_content_main, new GraficoAlumno());
//                fragmentManager.addToBackStack(null);
//                fragmentManager.commit();
////                onDestroyView();
//
//            }
//        });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    CountDownLatch countDownLatch;

    private void getGrupsHasLlistesSkills(Grup grupo) {
        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<List<Grups_has_llistes_skills>> userCall = abpService.getGrupHasLlistesSkills(grupo.getId());

        userCall.enqueue(new Callback<List<Grups_has_llistes_skills>>() {
            @Override
            public void onResponse(Call<List<Grups_has_llistes_skills>> call, Response<List<Grups_has_llistes_skills>> response) {
                switch (response.code()) {
                    case 200:
                        grups_has_llistes_skills = response.body();
                        countDownLatch = new CountDownLatch(grups_has_llistes_skills.size());
                        getLlistesSkillsEnteras();

//                        if(countDownLatch.getCount() != 0){
//
//                            for (i = 0; i < grups_has_llistes_skills.size(); i++) {
//                                id = grups_has_llistes_skills.get(i).getLlistes_skills_id();
//                                getLlistesSkills(id);
//                            }
//
//                        }


//                        while(countDownLatch.getCount() != 0  ){
//                            id = grups_has_llistes_skills.get(i).getLlistes_skills_id();
//
//                        }

//
//
//                                id = grups_has_llistes_skills.get(0).getLlistes_skills_id();
//                                getLlistesSkills(id);
//                                id = grups_has_llistes_skills.get(1).getLlistes_skills_id();
//                                getLlistesSkills(id);


//                        if(grups_has_llistes_skills != null){
//                            int i=0;
//                            while(grups_has_llistes_skills.size() != llistes_skills.size() || llistes_skills == null ){
//                                int id = grups_has_llistes_skills.get(i).getLlistes_skills_id();
//                                getLlistesSkills(id);
//                                i++;
//                            }
//                        }
//                        if(grups_has_llistes_skills!=null){
//                            getLlistesSkills(grups_has_llistes_skills.get(0).getLlistes_skills_id());
//                        }

                        break;

                    case 400:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Grups_has_llistes_skills>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    List<Llistes_skills> listasFiltradas;

    private void getLlistesSkillsEnteras() {

        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<List<Llistes_skills>> userCall = abpService.getLlistesSkills();
        llistes_skills = null;
        listasFiltradas = new ArrayList<>();

        userCall.enqueue(new Callback<List<Llistes_skills>>() {
            @Override
            public void onResponse(Call<List<Llistes_skills>> call, Response<List<Llistes_skills>> response) {

                switch (response.code()) {

                    case 200:
                        llistes_skills = response.body();

                        for(int i=0;i<grups_has_llistes_skills.size();i++){

                            for(int j=0;j<llistes_skills.size();j++){

                                if(grups_has_llistes_skills.get(i).getLlistes_skills_id() == llistes_skills.get(j).getId()){
                                    listasFiltradas.add(llistes_skills.get(j));
                                }
                            }

                        }

                        if (llistes_skills.size() != 0) {
                            Spinner spinnerListaSkills = binding.spinnersListaSkills;
                            ArrayAdapter<Llistes_skills> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listasFiltradas);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerListaSkills.setAdapter(adapter);

                            spinnerListaSkills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    listaSkillsObjeto = (Llistes_skills) spinnerListaSkills.getSelectedItem();
                                    int id1 = (int) listaSkillsObjeto.getId();
                                    getSkills(id1);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        }


                        break;
                    case 400:
                        Toast.makeText(getContext(), "400", Toast.LENGTH_LONG).show();
                        break;
                    case 401:
                        Toast.makeText(getContext(), "401", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(getContext(), "oops", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }


            }

            @Override
            public void onFailure(Call<List<Llistes_skills>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    private void getLlistesSkills(int id) {
        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<List<Llistes_skills>> userCall = abpService.getLlistesSkillsPorId(id);
        bien = false;
        userCall.enqueue(new Callback<List<Llistes_skills>>() {
            @Override
            public void onResponse(Call<List<Llistes_skills>> call, Response<List<Llistes_skills>> response) {
                switch (response.code()) {
                    case 200:
                        List<Llistes_skills> e = response.body();
                        llistes_skills.add(e.get(0));
//                        llistes_skills.add(kk.get(0));

                        if (llistes_skills.size() != 0) {
                            countDownLatch.countDown();
                        } else {
                            bien = true;
                        }


                        if (llistes_skills.size() != 0) {
                            ArrayList<Llistes_skills> listaSkills = new ArrayList<>();

                            for (int j = 0; j < grups_has_llistes_skills.size(); j++) {
                                for (int i = 0; i < llistes_skills.size(); i++) {

                                    if (grups_has_llistes_skills.get(j).getLlistes_skills_id() == llistes_skills.get(i).getGrups_has_llistes_skills().get(j).getLlistes_skills_id()) {
                                        listaSkills.add(llistes_skills.get(i));
                                    }
                                }
                            }


                            Spinner spinnerListaSkills = binding.spinnersListaSkills;
                            ArrayAdapter<Llistes_skills> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaSkills);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerListaSkills.setAdapter(adapter);

                            spinnerListaSkills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    listaSkillsObjeto = (Llistes_skills) spinnerListaSkills.getSelectedItem();
                                    int id1 = (int) listaSkillsObjeto.getId();
                                    getSkills(id1);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        }
                        break;
                    case 400:
                        Toast.makeText(getContext(), "400", Toast.LENGTH_LONG).show();
                        break;
                    case 401:
                        Toast.makeText(getContext(), "401", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(getContext(), "oops", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;


                }
            }

            @Override
            public void onFailure(Call<List<Llistes_skills>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        try {
            countDownLatch.await(1L * grups_has_llistes_skills.size(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    ArrayList<Skills> listaSkillsBuena;
    private void getSkills(int idListaSkill) {
        AbpService abpService = Api.getApi().create(AbpService.class);

        Call<List<Skills>> userCall = abpService.getSkills();

        skillsfiltradas = new ArrayList<>();

        listaSkillsBuena = new ArrayList<>();

        userCall.enqueue(new Callback<List<Skills>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Skills>> call, Response<List<Skills>> response) {
                switch (response.code()) {
                    case 200:
                        skillsfiltradas = response.body();

                        if (skillsfiltradas.size() != 0) {



                            for (int i = 0; i < skillsfiltradas.size(); i++) {
                                if (skillsfiltradas.get(i).getLlistes_skills_id() == idListaSkill) {
                                    listaSkillsBuena.add(skillsfiltradas.get(i));


                                }

                            }


                            LinearLayout layout = binding.LinearBotones;
                            layout.removeAllViews();

                            for (int i = 0; i < listaSkillsBuena.size(); i++) {

                                //set the properties for button
                                Button btnTag = new Button(getContext());
                                btnTag.setLayoutParams(new LinearLayout.LayoutParams
                                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                btnTag.setText(listaSkillsBuena.get(i).getNom());
                                Random rand = new Random();
                                int n = rand.nextInt(400) + 200;
                                btnTag.setWidth(n);
                                btnTag.setId((int) listaSkillsBuena.get(i).getId());
                                btnTag.setClickable(true);

//                                getValoracio();

                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Bundle bb = new Bundle();

                                        bb.putSerializable("valoraciones", valoracionafiltrar);
                                        bb.putSerializable("user", user);
                                        bb.putSerializable("skills", (Serializable) listaSkillsBuena);
                                        bb.putSerializable("idSkill", btnTag.getId());

                                        Skills skillPasar = null;
                                        for (int i = 0; i < skillsfiltradas.size(); i++) {
                                            if (btnTag.getId() == skillsfiltradas.get(i).getId()) {
                                                skillPasar = skillsfiltradas.get(i);
                                            }
                                        }
                                        bb.putSerializable("skill", (Serializable) skillPasar);


                                        EvaluarProfeAlumno autoEvaluarActivity = new EvaluarProfeAlumno();

                                        pasarFragmentos(autoEvaluarActivity, bb);

                                    }
                                });
                                layout.addView(btnTag);
                            }
                        }


                        break;
                    case 400:
                        Toast.makeText(getContext(), "400", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(getContext(), "oops", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Skills>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }


//    private void getValoracio() {
//        AbpService abpService = Api.getApi().create(AbpService.class);
//
//        retrofit2.Call<List<Valoracions>> userCall = abpService.getValoracions();
//
//
//        userCall.enqueue(new Callback<List<Valoracions>>() {
//
//            @Override
//            public void onResponse(retrofit2.Call<List<Valoracions>> call, Response<List<Valoracions>> response) {
//                switch (response.code()) {
//                    case 200:
//
//                        valoraciones = response.body();
//
//                        if (valoraciones.size() != 0) {
//                            for (Valoracions item : valoraciones) {
//
//                                valoracionafiltrar = item;
//
//                                System.out.println(valoracionafiltrar.getUsuaris().toString());
//
//                            }
//                        }
//                        break;
//                    case 400:
//                        Toast.makeText(getContext(), "400", Toast.LENGTH_LONG).show();
//                        break;
//                    case 404:
//                        Toast.makeText(getContext(), "oops", Toast.LENGTH_LONG).show();
//                        break;
//                    default:
//                        break;
//                }
//
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<List<Valoracions>> call, Throwable t) {
//
//            }
//
//        });
//
//
//    }


    public void pasarFragmentos(Fragment fragment, Bundle bundle) {

        fragment.setArguments(bundle);

        FragmentManager fmr = getActivity().getSupportFragmentManager();

        fmr.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fmr.beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment).commit();

    }
}
