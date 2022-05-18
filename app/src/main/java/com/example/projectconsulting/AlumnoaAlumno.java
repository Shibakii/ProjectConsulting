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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectconsulting.api.Api;
import com.example.projectconsulting.api.Services.AbpService;
import com.example.projectconsulting.databinding.FragmentLayoutAlumnoAlumnoBinding;
import com.example.projectconsulting.databinding.VistaListalumnosBinding;
import com.example.projectconsulting.models.Grup;
import com.example.projectconsulting.models.Grups_has_alumnes;
import com.example.projectconsulting.models.Usuaris;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoaAlumno extends Fragment {

    private FragmentLayoutAlumnoAlumnoBinding binding;
    private VistaListalumnosBinding vista;
    private List<Usuaris> usuarios1 = new ArrayList<>();
    private List<Usuaris> usuarios2 = new ArrayList<>();
    private List<Usuaris> usuarios3 = new ArrayList<>();
    private List<Grups_has_alumnes> grpX = new ArrayList<>();
    Grup grupo;
    ArrayList <Usuaris> todosUsuarios;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //todosLosUsuarios();
        container.removeAllViews();
        binding = FragmentLayoutAlumnoAlumnoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle configContent = ((MenuActivity) getActivity()).getBundleConfig();

        if (configContent.getString("BackgroundColor") != null)
        {
            LinearLayout ll = binding.backgroundAlumnoAlumno;
            ll.setBackgroundColor(Color.parseColor(configContent.getString("BackgroundColor")));
        }

        Bundle bundle = this.getArguments();
        Usuaris user = (Usuaris) bundle.getSerializable("user");
        usuarios3.add(user);
        List<Grup> grupos = (List<Grup>) bundle.getSerializable("grupsUser");
        todosUsuarios = (ArrayList<Usuaris>) bundle.getSerializable("listaUsuarios");

        ArrayList<Usuaris> usuarios = new ArrayList<>();
        usuarios.add(user);

        final RecyclerView listaAlumnos = binding.listaAlumno;
        Spinner spinner = binding.spinnerGrupo;

        if(grupos.size() != 0 ){

            ArrayAdapter<Grup> adapter = new ArrayAdapter<Grup>(getContext(), android.R.layout.simple_spinner_item, grupos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            // Lo de abajo se copia en un metodo y asi iniciamos la pantalla con una lista cargada
            grupo = (Grup) grupos.get(0);
            usuarios2 = new ArrayList<>();
            grpX = new ArrayList<>();

            CargarList(grupo, container, user, listaAlumnos);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    grupo = (Grup) spinner.getSelectedItem();
                    usuarios2 = new ArrayList<>();
                    grpX = new ArrayList<>();

                    //todosLosUsuarios();
                    CargarList(grupo, container, user, listaAlumnos);
                    /*if(usuarios1 != null){

                        for (Usuaris item: usuarios1) {
                            Usuaris a = item;
                            grpX = null;
                            grpX = item.getGrups_has_alumnes();
                            for (Grups_has_alumnes item2: grpX) {
                                if (item2.getGrups_id() == grupo.getId()){
                                    usuarios2.add(a);
                                }
                            }
                        }
                        if(usuarios2.size() != 0) {
                            UsuarisAdapter adaptador = new UsuarisAdapter((ArrayList<Usuaris>) usuarios2, container.getContext(), grupo, user);

                            listaAlumnos.setHasFixedSize(true);
                            listaAlumnos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

                            listaAlumnos.setAdapter(adaptador);
                        }else{
                            UsuarisAdapter adaptador = new UsuarisAdapter((ArrayList<Usuaris>) usuarios3, container.getContext(), grupo, user);

                            listaAlumnos.setHasFixedSize(true);
                            listaAlumnos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

                            listaAlumnos.setAdapter(adaptador);
                        }
                    }*/



                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });




        }else{
            spinner.setActivated(false);
            listaAlumnos.setNestedScrollingEnabled(false);
            listaAlumnos.setVisibility(View.GONE);
            listaAlumnos.setClickable(false);
            listaAlumnos.setMinimumHeight(0);
            listaAlumnos.setMinimumWidth(0);
            Toast.makeText(getContext(), "NO HAY GRUPOS", Toast.LENGTH_SHORT).show();

        }

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getContext(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(getContext(), "on Swiped ", Toast.LENGTH_SHORT).show();

                //Remove swiped item from list and notify the RecyclerView
//                int position = viewHolder.getAdapterPosition();
//                arrayList.remove(position);
//                adapter.notifyDataSetChanged();

                Usuaris usuarioAPuntuar ;
                usuarioAPuntuar= usuarios2.get(viewHolder.getAdapterPosition());


                Bundle b = new Bundle();
                b.putSerializable("user", user);
                b.putSerializable("usuarioAPuntuar", usuarioAPuntuar);
                b.putSerializable("grupo", grupo);

                PrevioEvaluarAlumnoAlumno peaa = new PrevioEvaluarAlumnoAlumno();
                FragmentManager fmr = getFragmentManager();
                peaa.setArguments(b);
                fmr.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fmr.beginTransaction().replace(R.id.nav_host_fragment_content_main, peaa).commit();


            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listaAlumnos);


//                        Toast.makeText(container.getContext(), "HOLAAAA", Toast.LENGTH_SHORT).show();
//                        Bundle b = new Bundle();
//                        b.putSerializable("user", user);
//                        FragmentManager fmr = getFragmentManager();
//                        fmr.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                        fmr.beginTransaction().replace(R.id.nav_host_fragment_content_main, fragmentObservaciones).commit();


        return root;
    }

    private void CargarList(Grup grupo, ViewGroup container, Usuaris user, RecyclerView listaAlumnos) {
        if(/*usuarios1*/todosUsuarios != null){

            for (Usuaris item: todosUsuarios) {
                Usuaris a = item;
                grpX = null;
                grpX = item.getGrups_has_alumnes();
                for (Grups_has_alumnes item2: grpX) {
                    if (item2.getGrups_id() == grupo.getId()){
                        if (item.getRols_id() == 4) {
                            usuarios2.add(a);
                        }
                    }
                }
            }
            if(usuarios2.size() != 0) {
                UsuarisAdapter adaptador = new UsuarisAdapter((ArrayList<Usuaris>) usuarios2, container.getContext(), grupo, user);

                listaAlumnos.setHasFixedSize(true);
                listaAlumnos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

                listaAlumnos.setAdapter(adaptador);
            }else{
                UsuarisAdapter adaptador = new UsuarisAdapter((ArrayList<Usuaris>) usuarios3, container.getContext(), grupo, user);

                listaAlumnos.setHasFixedSize(true);
                listaAlumnos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

                listaAlumnos.setAdapter(adaptador);
            }
        }
    }


    private ArrayList<Usuaris> getUsuaris() {
        ArrayList<Usuaris> x = null;
        return x;

    }



    public void todosLosUsuarios(){

        AbpService abpService = Api.getApi().create(AbpService.class);

        Call<List<Usuaris>> userCall = abpService.getUsers();

        usuarios1 = new ArrayList<>();

        userCall.enqueue(new Callback<List<Usuaris>>() {
            @Override
            public void onResponse(Call<List<Usuaris>> call, Response<List<Usuaris>> response) {
                switch(response.code()){

                    case 200:
                        usuarios1 = response.body();
                        break;

                }
            }

            @Override
            public void onFailure(Call<List<Usuaris>> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}