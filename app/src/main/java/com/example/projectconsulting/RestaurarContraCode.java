package com.example.projectconsulting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projectconsulting.api.Api;
import com.example.projectconsulting.api.Services.AbpService;
import com.example.projectconsulting.databinding.FragmentHomeBinding;
import com.example.projectconsulting.databinding.FragmentResetPasswordBycodeBinding;
import com.example.projectconsulting.models.Usuaris;

import org.mindrot.jbcrypt.BCrypt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurarContraCode extends Fragment {

    Usuaris user;
    EditText editTextCode ;
    EditText editTextContranueva  ;
    EditText editTextContraConfirm  ;
    Button botonRestaurar ;
    String codigo;

    private FragmentResetPasswordBycodeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        container.removeAllViews();
        binding = FragmentResetPasswordBycodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle configContent = ((MenuActivity) getActivity()).getBundleConfig();

        if (configContent.getString("BackgroundColor") != null)
        {
            LinearLayout ll = root.findViewById(R.id.resetByCodeBackground);
            ll.setBackgroundColor(Color.parseColor(configContent.getString("BackgroundColor")));
        }

        Bundle bundle = getArguments();
        codigo = bundle.getString("codigo");
        user = (Usuaris) bundle.getSerializable("user");


        editTextCode = binding.editTextCode;
        editTextContranueva = binding.editTextcontraNueva;
        editTextContraConfirm = binding.editTextConfirmContra;
        botonRestaurar = binding.buttonRestaurar;



        botonRestaurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contraNueva1 = editTextContranueva.getText().toString();
                String contraNuevaConfirm = editTextContraConfirm.getText().toString();
                String code = editTextCode.getText().toString();

                if(code.equals(codigo) && contraNueva1.equals(contraNuevaConfirm)  ) {

                    String contraNueva = BCrypt.hashpw(editTextContranueva.getText().toString(), BCrypt.gensalt(12));
                    user.setContrasenia(contraNueva);
                    updateContra();
                }else{
                    Toast.makeText(getContext(), "No se han introducido los campos correctos.", Toast.LENGTH_SHORT).show();
                }

            }
        });



        return root;
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
