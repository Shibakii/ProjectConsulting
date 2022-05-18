package com.example.projectconsulting.ui.perfil;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import com.example.projectconsulting.GraficoAlumno;
import com.example.projectconsulting.MenuActivity;
import com.example.projectconsulting.R;
import com.example.projectconsulting.RestaurarContraSimple;
import com.example.projectconsulting.databinding.ActivityMenuBinding;
import com.example.projectconsulting.databinding.FragmentPerfilBinding;
import com.example.projectconsulting.models.Usuaris;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;


import java.io.IOException;

public class PerfilFragment extends Fragment
{

    private FragmentPerfilBinding binding;
    private ActivityMenuBinding binderMenu;
    ImageView imagen;
    Button botnCamera;
    String rutaImagen;
    ImageButton img;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

//                GalleryViewModel galleryViewModel =
//                new ViewModelProvider(this).get(GalleryViewModel.class);
        container.removeAllViews();
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle configContent = ((MenuActivity) getActivity()).getBundleConfig();

        if (configContent.getString("BackgroundColor") != null)
        {
            LinearLayout ll = root.findViewById(R.id.perfil_background);
            ll.setBackgroundColor(Color.parseColor(configContent.getString("BackgroundColor")));
        }

        Bundle bundle = this.getArguments();
        Usuaris user = (Usuaris) bundle.getSerializable("user");

        //TextView idperfil = binding.textId;
        TextView nombreperfil = binding.textNombre;
        TextView correoperfil = binding.textCorreu;
        TextView nacimientoperfil = binding.textNacimiento;
        TextView telefonoperfil = binding.textTelefono;
        TextView direccionperfil = binding.textDireccion;
        img = binding.imagenUsuario;

        //idperfil.setText(String.valueOf(user.getId()));
        nombreperfil.setText(user.getNom());
        correoperfil.setText(user.getCorreu());
        nacimientoperfil.setText(String.valueOf(user.getAny_naixement()));
        telefonoperfil.setText(user.getDni());
        direccionperfil.setText(user.getDireccion());
        Picasso.get().load("https://abp-politecnics.com/2022/dam02/imagenesusuario/" + user.getDni() +".jpg").into(img);

        if(ContextCompat.checkSelfPermission(container.getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }


//        NavController navController = Navigation.findNavController(getActivity().getParent(), R.id.nav_host_fragment_content_main);
//
//        DrawerLayout drawerLayout = binderMenu.getRoot();
//        Toolbar toolbar = binderMenu.appBarMain.toolbar;
//        actualizarToolbar(navController, toolbar);



        TextView cambiarContra = binding.textViewCambiarContra;
        cambiarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RestaurarContraSimple restaurarContraSimple  =new RestaurarContraSimple();

                Bundle bundle = new Bundle();

                bundle.putSerializable("user", user);
                restaurarContraSimple.setArguments(bundle);

                FragmentManager fmr = getFragmentManager();

                fmr.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                fmr.beginTransaction().replace(R.id.nav_host_fragment_content_main, restaurarContraSimple).commit();


            }
        });


        binding.imagenUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 100);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //if (intent.resolveActivity(getPackageManager()) != null){
                File imagenArchivo = null;

                try {
                    imagenArchivo = crearImage();
                } catch (IOException e) {
                    Log.e("Error", e.toString());
                }

                if (imagenArchivo != null) {
                    Uri fotoUri = FileProvider.getUriForFile(getContext(), "com.example.projectconsulting.fileproveder", imagenArchivo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                    startActivityForResult(intent, 1);
                }
            }

        });
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(getContext().toString());
//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        System.out.println(img.getBackground().toString());
        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        //Comprovamos que la foto se a realizado
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria
            //Bundle extras = data.getExtras();
            Bitmap bMap = BitmapFactory.decodeFile(rutaImagen);
            //AÃ±adimos el bitmap al imageView para
            //mostrarlo por pantalla
            img.setImageBitmap(bMap);
        }
    }


    private File crearImage() throws IOException {

        String name;
        String nombreImagen = "foto_";
        File directorio = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(nombreImagen,".jpg", directorio);
        rutaImagen = image.getAbsolutePath();
        return  image;

    }




    public void actualizarToolbar(NavController navController, Toolbar toolbar){
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                String g = navDestination.getLabel().toString();
//                String gg = navDestination.getNavigatorName();
//                String currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main).getContext().toString();
                toolbar.setTitle(g);


            }

        });

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }








}