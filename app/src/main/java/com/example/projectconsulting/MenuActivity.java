package com.example.projectconsulting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;

import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.projectconsulting.api.Api;
import com.example.projectconsulting.api.Services.AbpService;
import com.example.projectconsulting.databinding.AppBarMainBinding;
import com.example.projectconsulting.databinding.FragmentHomeBinding;
import com.example.projectconsulting.databinding.FragmentPerfilBinding;
import com.example.projectconsulting.databinding.GraficoEvaluadoOtrosBinding;
import com.example.projectconsulting.databinding.NavHeaderMainBinding;
import com.example.projectconsulting.models.AdaptadorGrupo;
import com.example.projectconsulting.models.Cursos;
import com.example.projectconsulting.models.Grup;
import com.example.projectconsulting.models.Usuaris;

import com.example.projectconsulting.ui.Report;
import com.example.projectconsulting.ui.autoevaluar.Autoevaluar;
import com.example.projectconsulting.ui.home.HomeFragment;
import com.example.projectconsulting.ui.perfil.PerfilFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.contextaware.OnContextAvailableListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ReportFragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectconsulting.databinding.ActivityMenuBinding;


import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    private NavHeaderMainBinding binding1;
    private AppBarMainBinding bindings;
    private AppBarMainBinding f;
    private FragmentPerfilBinding b;
    Usuaris usuarioPuntuar;
    Usuaris user;
    ArrayList<Grup> grupos = new ArrayList<>();
    ArrayList<Cursos> cursos = new ArrayList<>();
    private HomeFragment selectedFragment;
    private ArrayList<Grup> grupsUsuari;
    List<Usuaris> listaUsers;
    DrawerLayout mDrawer;
//     Toolbar toolbar;
//     NavigationView navigationView;
    Bundle bundleConfig;
    Toolbar toolbar;

    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        usuarioPuntuar = null;
        bundleConfig = intent.getBundleExtra("configContent");
        user = (Usuaris) intent.getSerializableExtra("user");
        grupos = (ArrayList<Grup>) intent.getSerializableExtra("grupsUser");
        cursos = (ArrayList<Cursos>) intent.getSerializableExtra("allCursos");
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(binding.appBarM.toolbar);
        binding.appBarM.toolbar.setBackgroundColor(Color.parseColor(bundleConfig.getString("MenuColor")));
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(bundleConfig.getString("MenuColor"))));
        binding.appBarM.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bb = new Bundle();
                bb.putSerializable("user", user);
                Report personalizarFragment = new Report();
                pasarFragmentos(personalizarFragment, bb);
            }
        });
        obtenerAlumnos();
        mDrawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_graficos, R.id.nav_blank_fragment)
                .setDrawerLayout(mDrawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View trrr = navigationView.getHeaderView(0);
        TextView textoNombre = trrr.findViewById(R.id.TextoNombreUser);
        textoNombre.setText(user.getNom());
        TextView correo = trrr.findViewById(R.id.textViewCorreo);
        correo.setText(user.getCorreu());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle bb = new Bundle();
                bb.putSerializable("user", user);
                PersonalizarFragment personalizarFragment = new PersonalizarFragment();
                pasarFragmentos(personalizarFragment, bb);
                return false;
            }
        });

//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.nav_host_fragment_content_main);
//        if (fragment == null) {
//            FragmentTransaction ft = fm.beginTransaction();
//
//            ft.add(android.R.id.content,new HomeFragment());
//            ft.commit();
//        }
        TextView textoRol = trrr.findViewById(R.id.textViewRol);
        if (user.getRols_id() == 2 || user.getRols_id() == 3) {
            textoRol.setText("Profesor");
            binding.navView.getMenu().setGroupVisible(R.id.grupoProfes, true);
            binding.navView.getMenu().setGroupVisible(R.id.grupoAlumnos, false);
        } else if (user.getRols_id() == 4) {
            textoRol.setText("Alumno");
            binding.navView.getMenu().setGroupVisible(R.id.grupoProfes, false);
            binding.navView.getMenu().setGroupVisible(R.id.grupoAlumnos, true);
        }

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                Bundle bundle;
                switch (item.getItemId()) {

                    case R.id.nav_blank_fragment:
                        //Toast.makeText(MenuActivity.this, "BLANQUISIMO LOL", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_cerrar_sesion:
                        Intent intentLogout = new Intent(MenuActivity.this, LoginActivity.class);
                        intentLogout.putExtra("configContent", bundleConfig);
                        startActivity(intentLogout);
                        break;

                    case R.id.nav_home:
                        homeFragment = new HomeFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("user", user);
                        //Toast.makeText(MenuActivity.this, "HOME", Toast.LENGTH_SHORT).show();
                        pasarFragmentos(homeFragment, bundle);
                        //actualizarToolbar(navController, toolbar);
                        return true;

                    case R.id.nav_profile:
                        PerfilFragment perfilFragment = new PerfilFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("user", user);
                        //Toast.makeText(MenuActivity.this, "PERFIL", Toast.LENGTH_SHORT).show();
                        pasarFragmentos(perfilFragment, bundle);
                        //actualizarToolbar(navController, toolbar);
                        return true;

                    case R.id.nav_autoevaluar:

                        AutoevaluarBien autoevaluarBien = new AutoevaluarBien();
                        bundle = new Bundle();
                        bundle.putSerializable("user", user);
                        bundle.putSerializable("grupsUser", grupos);
                        //Toast.makeText(MenuActivity.this, "AUTOEVALUAR", Toast.LENGTH_SHORT).show();
                        pasarFragmentos(autoevaluarBien, bundle);
                        //actualizarToolbar(navController, toolbar);
                        return true;


                    case R.id.nav_stats:

                        GraficoAlumno graficoAlumno = new GraficoAlumno();
                        bundle = new Bundle();
                        bundle.putSerializable("user", user);
                        bundle.putSerializable("grupsUser", grupos);
                        pasarFragmentos(graficoAlumno, bundle);
                        //actualizarToolbar(navController, toolbar);
                        return true;

                    case R.id.nav_listaProfesAlumnos:

                        ListaAlumnos listaAlumnos = new ListaAlumnos();
                        bundle = new Bundle();

                        //Toast.makeText(MenuActivity.this, "LISTA ALUMNOS", Toast.LENGTH_SHORT).show();

                        bundle.putSerializable("user", user);
                        bundle.putSerializable("grupsUser", grupos);
                        bundle.putSerializable("listaUsuarios", (Serializable) listaUsers);

                        pasarFragmentos(listaAlumnos, bundle);
                        break;
                    case R.id.nav_fragmentlistalumnosAlumnos:

                        AlumnoaAlumno evaluarCompi = new AlumnoaAlumno();
                        bundle = new Bundle();
                        bundle.putSerializable("user", user);
                        bundle.putSerializable("grupsUser", grupos);
                        bundle.putSerializable("listaUsuarios", (Serializable) listaUsers);
                        pasarFragmentos(evaluarCompi, bundle);
                        //actualizarToolbar(navController, toolbar);
                        return true;
                }
                return false;
            }
        });

//        FragmentManager mm = getSupportFragmentManager();

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("user", user);
//        Navigation.findNavController(navigationView).navigate(R.id.nav_view, bundle);
    }
    public Usuaris getUser() {
        return user;
    }
    public void actualizarToolbar(NavController navController, Toolbar toolbar) {
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                String g = navDestination.getLabel().toString();
                //binding.appBarMain.nombreUser.setText(g);
//                String gg = navDestination.getNavigatorName();
//                String currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main).getContext().toString();
                //toolbar.setTitle(g);
            }
        });
    }
    public void pasarFragmentos(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        FragmentManager fmr = getSupportFragmentManager();
        //fmr.popBackStack("a", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fmr.beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment).commit();
        mDrawer.closeDrawers();
    }
    public void cambiarColorToolbar(String color) {
        this.toolbar.setBackgroundColor(Color.parseColor(color));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void obtenerAlumnos() {
        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<List<Usuaris>> listasUsers1 = abpService.getUsers();
        listasUsers1.enqueue(new Callback<List<Usuaris>>() {
            @Override
            public void onResponse(Call<List<Usuaris>> call, Response<List<Usuaris>> response) {
                switch (response.code()) {
                    case 200:
                        listaUsers = response.body();
                        break;
                    case 400:
                        Toast.makeText(MenuActivity.this, "Error de connexion", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(MenuActivity.this, "No existe", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed() {
//        int count = getFragmentManager().getBackStackEntryCount();
//        if (count == 0) {
//            super.onBackPressed();
//            getFragmentManager().popBackStack();
//        } else {
//        }
    }
    public ArrayList<Grup> getGrupos() {
        return grupos;
    }
    public Toolbar getToolbar() {
        return toolbar;
    }
    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }
    public Bundle getBundleConfig() {
        return bundleConfig;
    }
    public void setBundleConfig(Bundle bundleConfig) {
        this.bundleConfig = bundleConfig;
    }
}
