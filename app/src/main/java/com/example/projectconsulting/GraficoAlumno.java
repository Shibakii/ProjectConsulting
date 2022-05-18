package com.example.projectconsulting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.projectconsulting.api.Api;
import com.example.projectconsulting.api.Services.AbpService;
import com.example.projectconsulting.databinding.FragmentGraficoalumnoBinding;
import com.example.projectconsulting.models.Grup;
import com.example.projectconsulting.models.Grups_has_llistes_skills;
import com.example.projectconsulting.models.Kpis;
import com.example.projectconsulting.models.Llistes_skills;
import com.example.projectconsulting.models.Skills;
import com.example.projectconsulting.models.Usuaris;
import com.example.projectconsulting.models.Valoracions;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraficoAlumno extends Fragment {

    private FragmentGraficoalumnoBinding binding;
    com.github.mikephil.charting.charts.RadarChart RadarChart;
    RadarData radarData;
    RadarDataSet radarDataSet;
    ArrayList radarEntries;
    Usuaris user;
    List<Grup> grupos;
    List<Valoracions> valoraciones = new ArrayList<>();
    Grup grupo;
    Llistes_skills llistaSeleccionada;

    List<Kpis> kpisConValoraciones = new ArrayList<>();
    List<Grups_has_llistes_skills> grups_has_llistes_skills;
    List<Llistes_skills> llistes_skills = new ArrayList<>();
    List<Llistes_skills> llistes_skills_filtradas ;
    List<Skills> skillsfiltradas = new ArrayList<>();
    ArrayList<Skills> listaSkillsBuena = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews();
        binding = FragmentGraficoalumnoBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        Bundle bundle = getArguments();
        user = (Usuaris) bundle.getSerializable("user");
        grupos = (List<Grup>) bundle.getSerializable("grupsUser");

        TextView nombre = binding.fragmentNombreAlumnoGrafico;
        nombre.setText(user.getNom());
        TextView rol = binding.fragmentCursoGrafico;
        rol.setText(user.getRols());

        LinearLayout ll = binding.graficoBackground;
        ll.setBackgroundColor(Color.parseColor(((MenuActivity) getActivity()).getBundleConfig().getString("BackgroundColor")));

        if (grupos.size() != 0) {
            Spinner spinner = binding.spinnerGrupoGraficos;
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


        }



        return root;
    }

    private void getGrupsHasLlistesSkills(Grup grupo) {
        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<List<Grups_has_llistes_skills>> userCall = abpService.getGrupHasLlistesSkills(grupo.getId());

        userCall.enqueue(new Callback<List<Grups_has_llistes_skills>>() {
            @Override
            public void onResponse(Call<List<Grups_has_llistes_skills>> call, Response<List<Grups_has_llistes_skills>> response) {
                switch (response.code()) {
                    case 200:
                        grups_has_llistes_skills = response.body();
                        getLlistesSkillsEnteras();
                        getValoraciones();

                        break;

                    case 400:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Grups_has_llistes_skills>> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getLlistesSkillsEnteras() {

        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<List<Llistes_skills>> userCall = abpService.getLlistesSkills();
        llistes_skills = null;

        llistes_skills_filtradas = new ArrayList<>();
        userCall.enqueue(new Callback<List<Llistes_skills>>() {
            @Override
            public void onResponse(Call<List<Llistes_skills>> call, Response<List<Llistes_skills>> response) {

                switch (response.code()) {

                    case 200:
                        llistes_skills = response.body();

                        if (llistes_skills.size() != 0) {
                            for (int j = 0; j < grups_has_llistes_skills.size(); j++) {
                                for (int i = 0; i < llistes_skills.size(); i++) {

                                    if (llistes_skills.get(i).getId() == grups_has_llistes_skills.get(j).getLlistes_skills_id()) {
                                        llistes_skills_filtradas.add(llistes_skills.get(i));

                                    }

                                }
                            }

                        }
                        Spinner spinnerListaSkills = binding.spinnerListaSkillsGraficos;
                        ArrayAdapter<Llistes_skills> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, llistes_skills_filtradas);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerListaSkills.setAdapter(adapter2);

                        spinnerListaSkills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Llistes_skills listaSkillsObjeto = (Llistes_skills) spinnerListaSkills.getSelectedItem();
                                llistaSeleccionada = (Llistes_skills)spinnerListaSkills.getSelectedItem();
                                int id1 = (int) listaSkillsObjeto.getId();
                                getSkills(id1);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

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

    public void getValoraciones() {

        AbpService abpService = Api.getApi().create(AbpService.class);

        Call<List<Valoracions>> userCall = abpService.getValoracions();

        valoraciones = new ArrayList<>();
        userCall.enqueue(new Callback<List<Valoracions>>() {
            @Override
            public void onResponse(Call<List<Valoracions>> call, Response<List<Valoracions>> response) {

                switch (response.code()) {
                    case 200:
                        List<Valoracions> valoracions = response.body();

                        if (valoracions.size() != 0) {

                            for (int i = 0; i < valoracions.size(); i++) {

                                if (valoracions.get(i).getUsuari_valorat_id() == user.getId()) {
                                    valoraciones.add(valoracions.get(i));

                                }

                            }
                            getKpis();
                        }
                        break;
                    case 400:
                        break;
                    case 401:
                        break;
                    case 404:
                        break;

                }
            }

            @Override
            public void onFailure(Call<List<Valoracions>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void getKpis() {
        AbpService abpService = Api.getApi().create(AbpService.class);

        Call<List<Kpis>> userCall = abpService.getKpis();

        kpisConValoraciones = new ArrayList<>();
        userCall.enqueue(new Callback<List<Kpis>>() {

            @Override
            public void onResponse(retrofit2.Call<List<Kpis>> call, Response<List<Kpis>> response) {
                switch (response.code()) {
                    case 200:
                        List<Kpis> kpisTodas = response.body();

                        for (int i = 0; i < valoraciones.size(); i++) {
                            for (int j = 0; j < kpisTodas.size(); j++) {
                                if (valoraciones.get(i).getKpis_id() == kpisTodas.get(j).getId()) {
                                    kpisConValoraciones.add(kpisTodas.get(j));
                                }
                            }
                        }
                        for (int i = 0; i < kpisConValoraciones.size(); i++) {

                            for (int j = 0; j < valoraciones.size(); j++) {
                                if (kpisConValoraciones.get(i).getId() == valoraciones.get(j).getKpis_id()) {
                                    kpisConValoraciones.get(i).setValoracionPorValoracion(valoraciones.get(j));
                                }
                            }

                        }
                        break;
                    case 400:

                        break;
                    case 404:
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

    private void getSkills(int idListaSkill) {
        AbpService abpService = Api.getApi().create(AbpService.class);

        Call<List<Skills>> userCall = abpService.getSkills();

        skillsfiltradas = new ArrayList<>();
        listaSkillsBuena = new ArrayList<>();

        userCall.enqueue(new Callback<List<Skills>>() {
            @Override
            public void onResponse(Call<List<Skills>> call, Response<List<Skills>> response) {
                switch (response.code()) {
                    case 200:
                        skillsfiltradas = response.body();

                        for (int i = 0; i < skillsfiltradas.size(); i++) {
                            for (int j = 0; j < llistes_skills_filtradas.size(); j++) {
                                if (skillsfiltradas.get(i).getLlistes_skills_id() == llistes_skills_filtradas.get(j).getId()) {
                                    listaSkillsBuena.add(skillsfiltradas.get(i));
                                }
                            }
                        }

                        for(int i=0;i<listaSkillsBuena.size();i++){
                            for(int j =0;j<kpisConValoraciones.size();j++){

                                if(listaSkillsBuena.get(i).getId() == kpisConValoraciones.get(j).getSkills_id()){
                                    listaSkillsBuena.get(i).addKpiPorKpi(kpisConValoraciones.get(j));
                                }

                            }
                        }
                        getEntries();
                        break;
                    case 400:
                        break;
                    case 401:
                        break;
                    case 404:
                        break;
                }
            }
            @Override
            public void onFailure(Call<List<Skills>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }























    private void getEntries() {


        radarEntries = new ArrayList<>();

        /*List<Valoracionsss> valoracionesParaGrafica = new ArrayList<>();
            for (Valoracionsss item: valoraciones) {
                radarEntries.add(new RadarEntry(item.getNota(), item.getData()));
            }*/
        List<Integer> valoracionsFinals = new ArrayList<>();
        List<Skills> SkillAmostrar = new ArrayList<>();
        for (Skills item: listaSkillsBuena) {
            if (llistaSeleccionada.getId() == item.getLlistes_skills_id()){
                SkillAmostrar.add(item);
            }
        }
        List<Kpis> KpisDeSkill = new ArrayList<>();
        for (Skills item: SkillAmostrar) {
            KpisDeSkill = new ArrayList<>();
            for (Kpis item2: kpisConValoraciones) {
                if (item.getId() == item2.getSkills_id()){
                    KpisDeSkill.add(item2);
                }
            }
            int mitja = 0;
            int suma = 0;
            /*for (Kpis item4: KpisDeSkill) {*/
            List<Valoracions> valoracionsPerMitja = new ArrayList<>();
            mitja = 0;
            suma = 0;
            for (Valoracions item2: valoraciones) {
                if (KpisDeSkill.size() != 0) {
                    if (KpisDeSkill.get(0).getId() == item2.getKpis_id()) {
                        valoracionsPerMitja.add(item2);
                    }
                }
            }

            for (Valoracions item3: valoracionsPerMitja) {
                suma += item3.getNota();
            }
            if (valoracionsPerMitja.size() != 0){
                mitja = suma/valoracionsPerMitja.size();
            }else{
                mitja = 0;
            }


            // }
            valoracionsFinals.add(mitja);


        }
        for (int i = 0; i < SkillAmostrar.size(); ++i){
            float y = valoracionsFinals.get(i);
            String x = SkillAmostrar.get(i).getNom();
            radarEntries.add(new RadarEntry(y, x));
        }
        /*radarEntries.add(new RadarEntry(0));
        radarEntries.add(new RadarEntry(0));
        radarEntries.add(new RadarEntry(0));
        radarEntries.add(new RadarEntry(0));
        radarEntries.add(new RadarEntry(0));
*/
        RadarChart = binding.RadarChart;

        radarDataSet = new RadarDataSet(radarEntries, "Notas valoradas por ti");

        radarData = new RadarData(radarDataSet);

        RadarChart.setData(radarData);

        radarDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        radarDataSet.setValueTextColor(Color.BLACK);

        radarDataSet.setValueTextSize(18f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }





















//    List<Skills> skillsfiltradasBien  =new ArrayList<>();


//        userCall.enqueue(new Callback<List<Skills>>() {
//            @Override
//            public void onResponse(retrofit2.Call<List<Skills>> call, Response<List<Skills>> response) {
//                switch (response.code()) {
//                    case 200:
//                        skillsfiltradas = response.body();
//
//                        if(skillsfiltradas.size() != 0){
//
//
//
//
//                            for(int j=0;j<llistes_skills.size();j++){
//                                for(int i=0;i<skillsfiltradas.size();i++){
//                                    if(skillsfiltradas.get(i).getLlistes_skills_id() == llistes_skills.get(j).getId()){
//                                        listaSkillsBuena.add(skillsfiltradas.get(i));
//
//
//                                    }
//                                }
//                            }
//
//
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
//            }
//            @Override
//            public void onFailure(retrofit2.Call<List<Skills>> call, Throwable t) {
//            }
//
//        });
//
//        }
//    }


}

