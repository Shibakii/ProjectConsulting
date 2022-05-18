package com.example.projectconsulting;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.projectconsulting.api.Api;
import com.example.projectconsulting.api.Services.AbpService;
import com.example.projectconsulting.databinding.FragmentResetPasswordBinding;
import com.example.projectconsulting.models.Usuaris;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurarContraSimple extends Fragment {
private FragmentResetPasswordBinding binding;
Usuaris user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        container.removeAllViews();
        Bundle bundle = this.getArguments();
        user = (Usuaris) bundle.getSerializable("user");



        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle configContent = ((MenuActivity) getActivity()).getBundleConfig();

        if (configContent.getString("BackgroundColor") != null)
        {
            LinearLayout ll = root.findViewById(R.id.resetByPasswdBackground);
            ll.setBackgroundColor(Color.parseColor(configContent.getString("BackgroundColor")));
        }


        TextView noRecuerdo = binding.textViewEnviarSMS;
        if(ContextCompat.checkSelfPermission(container.getContext(),
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.SEND_SMS
                    },
                    100);
        }

        FirebaseApp.initializeApp(getActivity().getApplicationContext());
        noRecuerdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                FirebaseApp.initializeApp(getContext());
//                FirebaseAuth.getInstance().sendPasswordResetEmail(user.getCorreu())
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(getContext(), "tttttt", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });


                Random r = new Random();
                int low = 0001;
                int high = 9999;
                int codigo1 = r.nextInt(high-low) + low;
//                String codigo = String.valueOf(codigo1) ;
                String codigo = "123";


//                sendSMS(user.getTelefono(), codigo );

                RestaurarContraCode restaurarContraCode = new RestaurarContraCode();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                bundle.putString("codigo", codigo);
                restaurarContraCode.setArguments(bundle);

                FragmentManager fmr = getFragmentManager();

                fmr.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                fmr.beginTransaction().replace(R.id.nav_host_fragment_content_main, restaurarContraCode).commit();

            }
        });




        binding.buttonRestaurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BCrypt.checkpw(binding.editTextcontraActual.getText().toString(), user.getContrasenia())){
                    user.setContrasenia(BCrypt.hashpw(binding.editTextcontraActual.getText().toString(), BCrypt.gensalt(12)));
                    updateContra();
                }
            }
        });


        return root;
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, "Codigo restaurar", msg, null, null);
            Toast.makeText(getContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    private void updateContra(){
        AbpService abpService = Api.getApi().create(AbpService.class);
        Call<Usuaris> updateUsuari = abpService.updateUsuari((int) user.getId(), user);

        updateUsuari.enqueue(new Callback<Usuaris>() {
            @Override
            public void onResponse(Call<Usuaris> call, Response<Usuaris> response) {
                switch (response.code()){

                    case 204:
                        Toast.makeText(getContext(), "Se ha cambiado la contraseña correctamente!", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Toast.makeText(getContext(), "No se han realizado los cambios.", Toast.LENGTH_SHORT).show();

                        break;


                    case 400:
                        Toast.makeText(getContext(), "ERROR: 400", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(getContext(), "ERROR: 404", Toast.LENGTH_LONG).show();

                        break;

                }
            }

            @Override
            public void onFailure(Call<Usuaris> call, Throwable t) {
                Toast.makeText(getContext(), t.getCause() + " - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }




}
