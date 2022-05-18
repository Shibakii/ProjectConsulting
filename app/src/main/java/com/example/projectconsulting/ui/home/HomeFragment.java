package com.example.projectconsulting.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.projectconsulting.AdapterPageHome;
import com.example.projectconsulting.LoginActivity;
import com.example.projectconsulting.MenuActivity;
import com.example.projectconsulting.R;
import com.example.projectconsulting.api.Api;
import com.example.projectconsulting.api.Services.AbpService;
import com.example.projectconsulting.databinding.ActivityMenuBinding;
import com.example.projectconsulting.databinding.FragmentHomeBinding;
import com.example.projectconsulting.models.Cursos;
import com.example.projectconsulting.models.Dias;
import com.example.projectconsulting.models.Grup;
import com.example.projectconsulting.models.Grups_has_horaris;
import com.example.projectconsulting.models.Horaris;
import com.example.projectconsulting.models.Usuaris;
import com.example.projectconsulting.ui.perfil.PerfilFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private List<Horaris> horaris;
    private List<Grups_has_horaris> grupsHasHoraris;
    private List<Dias> diasTotal;

    private ArrayList<Dias> diasMostrar;

    private Grup grupo;
    private ViewPager viewPager;

    private Spinner spinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        container.removeAllViews();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundleConfig = ((MenuActivity) getActivity()).getBundleConfig();

        if (bundleConfig.getString("BackgroundColor") != null)
        {
            LinearLayout ll = root.findViewById(R.id.homefragment);
            ll.setBackgroundColor(Color.parseColor(bundleConfig.getString("BackgroundColor")));
        }

        // ========= SETEAR ENCABEZADO DE FRAGMENT =========
        final TextView nombreUser = root.findViewById(R.id.nombreUser_home);
        nombreUser.setText(((MenuActivity) getActivity()).getUser().getNom());
        final TextView nombreCurso = root.findViewById(R.id.nombreCurso_home);
        nombreCurso.setText("2021-2022");

        spinner = root.findViewById(R.id.spinnerHome);
        spinner.setEnabled(true);

        if(((MenuActivity) getActivity()).getGrupos().size() != 0){

            ArrayAdapter<Grup> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ((MenuActivity) getActivity()).getGrupos());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            obtenerHorarios();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                grupo = (Grup) spinner.getSelectedItem();
                if (horaris != null && grupsHasHoraris != null && diasTotal != null)
                {
                    cambiarViewPager();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return root;
    }

    public void obtenerHorarios() {
        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<List<Horaris>> horarisCall = abpService.getHoraris();

        horarisCall.enqueue(new Callback<List<Horaris>>() {
            @Override
            public void onResponse(Call<List<Horaris>> call, Response<List<Horaris>> response) {
                switch (response.code()) {

                    case 200:

                        List<Horaris> horarios = response.body();
                        horaris = horarios;
                        obtenerGrupsHasHoraris();

                        break;
                    case 400:
                        Snackbar.make(getView(), "ERROR: 400", Snackbar.LENGTH_LONG).show();
                        break;
                    case 404:
                        Snackbar.make(getView(), "ERROR: 404", Snackbar.LENGTH_LONG).show();
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onFailure(Call<List<Horaris>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void obtenerGrupsHasHoraris() {
        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<List<Grups_has_horaris>> grupsHasHorarisCall = abpService.getGrupsHasHoraris();

        grupsHasHorarisCall.enqueue(new Callback<List<Grups_has_horaris>>() {
            @Override
            public void onResponse(Call<List<Grups_has_horaris>> call, Response<List<Grups_has_horaris>> response) {
                switch (response.code()) {
                    case 200:

                        List<Grups_has_horaris> grupsHorarios = response.body();
                        grupsHasHoraris = grupsHorarios;
                        obtenerDias();

                        break;
                    case 400:
                        Snackbar.make(getView(), "ERROR: 400", Snackbar.LENGTH_LONG).show();
                        break;
                    case 404:
                        Snackbar.make(getView(), "ERROR: 404", Snackbar.LENGTH_LONG).show();
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onFailure(Call<List<Grups_has_horaris>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void obtenerDias() {
        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<List<Dias>> diasCall = abpService.getDias();

        diasCall.enqueue(new Callback<List<Dias>>() {
            @Override
            public void onResponse(Call<List<Dias>> call, Response<List<Dias>> response) {
                switch (response.code()) {
                    case 200:

                        List<Dias> dias = response.body();
                        diasTotal = dias;

                        cambiarViewPager();

                        break;
                    case 400:
                        Snackbar.make(getView(), "ERROR: 400", Snackbar.LENGTH_LONG).show();
                        break;
                    case 404:
                        Snackbar.make(getView(), "ERROR: 404", Snackbar.LENGTH_LONG).show();
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onFailure(Call<List<Dias>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

//    @Override
//    public void onPause() {
//
//        super.onPause();
//    }

    public void cambiarViewPager()
    {
        diasMostrar = new ArrayList<>();

        viewPager = binding.horarios;
        viewPager.setSaveFromParentEnabled(false);
        viewPager.removeAllViews();
        viewPager.setBackgroundColor(Color.parseColor(((MenuActivity) getActivity()).getBundleConfig().getString("BackgroundColor")));

        Boolean lockOn = false;

        Dias Lunes = new Dias();
        Dias Martes = new Dias();
        Dias Miercoles = new Dias();
        Dias Jueves = new Dias();
        Dias Viernes = new Dias();

        Lunes.setDia("Lunes");
        Martes.setDia("Martes");
        Miercoles.setDia("Miércoles");
        Jueves.setDia("Jueves");
        Viernes.setDia("Viernes");

        float idGruphhH = 0;
        float idGruphhG = 0;

        for (Horaris horari : horaris)
        {
            for (Grups_has_horaris gruphh : grupsHasHoraris)
            {
                if (horari.getId() == gruphh.getId_horari() && grupo.getId() == gruphh.getId_grups())
                {
                    if (!lockOn)
                    {
                        idGruphhH = gruphh.getId_horari();
                        idGruphhG = gruphh.getId_grups();
                        lockOn = true;
                    }

                    for (Dias dia : diasTotal)
                    {
                        if (dia.getDia().equalsIgnoreCase("Lunes") && gruphh.getId_dias() == dia.getId() && horari.getId() == idGruphhH && grupo.getId() == idGruphhG)
                        {
                            Lunes.setFi(Lunes.getFi() + dia.getFi() + "\n");
                            Lunes.setInici(Lunes.getInici() + dia.getInici() + "\n");
                            Lunes.setTasca(Lunes.getTasca() + dia.getTasca() + "\n");
                            break;
                        }
                        else if (dia.getDia().equalsIgnoreCase("Martes") && gruphh.getId_dias() == dia.getId() && horari.getId() == idGruphhH && grupo.getId() == idGruphhG)
                        {
                            Martes.setFi(Martes.getFi() + dia.getFi() + "\n");
                            Martes.setInici(Martes.getInici() + dia.getInici() + "\n");
                            Martes.setTasca(Martes.getTasca() + dia.getTasca() + "\n");
                            break;
                        }
                        else if (dia.getDia().equalsIgnoreCase("Miércoles") && gruphh.getId_dias() == dia.getId() && horari.getId() == idGruphhH && grupo.getId() == idGruphhG)
                        {
                            Miercoles.setFi(Miercoles.getFi() + dia.getFi() + "\n");
                            Miercoles.setInici(Miercoles.getInici() + dia.getInici() + "\n");
                            Miercoles.setTasca(Miercoles.getTasca() + dia.getTasca() + "\n");
                            break;
                        }
                        else if (dia.getDia().equalsIgnoreCase("Jueves") && gruphh.getId_dias() == dia.getId() && horari.getId() == idGruphhH && grupo.getId() == idGruphhG)
                        {
                            Jueves.setFi(Jueves.getFi() + dia.getFi() + "\n");
                            Jueves.setInici(Jueves.getInici() + dia.getInici() + "\n");
                            Jueves.setTasca(Jueves.getTasca() + dia.getTasca() + "\n");
                            break;
                        }
                        else if (dia.getDia().equalsIgnoreCase("Viernes") && gruphh.getId_dias() == dia.getId() && horari.getId() == idGruphhH && grupo.getId() == idGruphhG)
                        {
                            Viernes.setFi(Viernes.getFi() + dia.getFi() + "\n");
                            Viernes.setInici(Viernes.getInici() + dia.getInici() + "\n");
                            Viernes.setTasca(Viernes.getTasca() + dia.getTasca() + "\n");
                            break;
                        }
                    }
                }
            }
        }

        diasMostrar.add(Lunes);
        diasMostrar.add(Martes);
        diasMostrar.add(Miercoles);
        diasMostrar.add(Jueves);
        diasMostrar.add(Viernes);

        AdapterPageHome adaptador = new AdapterPageHome(getContext(), diasMostrar);
        viewPager.setAdapter(adaptador);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void disableSpinner()
    {
        spinner.setEnabled(false);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }
}