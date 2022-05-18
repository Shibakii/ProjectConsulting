package com.example.projectconsulting;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectconsulting.databinding.FragmentPersonalizarBinding;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class PersonalizarFragment extends Fragment {

    private FragmentPersonalizarBinding binding;
    public static int currentBackgroundColor = 0xffffffff;
    public static int currentMenuColor = 0xffffffff;

    private String menuColor = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();

        binding = FragmentPersonalizarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle configContent = ((MenuActivity) getActivity()).getBundleConfig();

        if (configContent.getString("BackgroundColor") != null)
        {
            LinearLayout ll = root.findViewById(R.id.personalizar_background);
            ll.setBackgroundColor(Color.parseColor(configContent.getString("BackgroundColor")));
        }

//        SwitchCompat modoClaro = root.findViewById(R.id.SwtClaro);
//        SwitchCompat modoOscuro = root.findViewById(R.id.SwtOscuro);
//        SwitchCompat modoPredeterminado = root.findViewById(R.id.SwtPredet);

        Button guardarCambios = root.findViewById(R.id.saveChanges);
        Button BtnSetColorBack = root.findViewById(R.id.BtnSetColorBack);
        Button BtnSetColorMenu = root.findViewById(R.id.BtnSetColorMenu);

//        modoClaro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked) {
//                    modoClaro.setText("Si");
//                    // ==========================
//                    modoOscuro.setChecked(false);
//                    modoOscuro.setText("No");
//                    modoPredeterminado.setChecked(false);
//                    modoPredeterminado.setText("No");
//                    // ==========================
//                }
//
//            }
//        });
//
//        modoOscuro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked) {
//                    modoOscuro.setText("Si");
//                    // ==========================
//                    modoClaro.setChecked(false);
//                    modoClaro.setText("No");
//                    modoPredeterminado.setChecked(false);
//                    modoPredeterminado.setText("No");
//                    // ==========================
//                }
//
//            }
//        });
//
//        modoPredeterminado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked) {
//                    modoPredeterminado.setText("Si");
//                    // ==========================
//                    modoOscuro.setChecked(false);
//                    modoOscuro.setText("No");
//                    modoClaro.setChecked(false);
//                    modoClaro.setText("No");
//                    // ==========================
//                }
//            }
//        });

        guardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean DarkMode = false;
                String BackgroundColor;
                String MenuColor;
                int TextSize = 20;

//                if (modoOscuro.isChecked())
//                {
//                    DarkMode = true;
//                }
//                else
//                {
//                    DarkMode = configContent.getBoolean("DarkMode");
//                }

                if (currentBackgroundColor != 0xffffffff)
                {
                    BackgroundColor = "#" + Integer.toHexString(currentBackgroundColor).toUpperCase();
                }
                else
                {
                    BackgroundColor = configContent.getString("BackgroundColor");
                }

                if (menuColor != null)
                {
                    MenuColor = "#" + Integer.toHexString(currentMenuColor).toUpperCase();
                }
                else
                {
                    MenuColor = configContent.getString("MenuColor");
                }

                writeChangesOnFile(DarkMode, BackgroundColor, MenuColor, TextSize);

            }
        });

//        String[] colores = getTag(R.array.arrayColores).toString();
//        Spinner spinner = binding.spinnerColores;

//        String[] nombreColores = getResources().getStringArray(R.array.arrayColores);
//
//        ArrayList <ColorUser> coloresBackground;
//
//

        /*ToggleButton togle = container.findViewById(R.id.SwtClaro);
        togle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {
                    // The toggle is disabled
                }

            }
        });*/

        BtnSetColorBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("Background color")
                        .initialColor(currentBackgroundColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorChangedListener(new OnColorChangedListener() {
                            @Override
                            public void onColorChanged(int selectedColor) {
                                // Handle on color change
                                Log.d("ColorPicker", "onColorChanged: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                //Toast.makeText("onColorSelected: 0x" + Integer.toHexString(selectedColor)).show();
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                changeBackgroundColor(selectedColor);

                                if (allColors != null) {
                                    StringBuilder sb = null;

                                    for (Integer color : allColors) {
                                        if (color == null)
                                            continue;
                                        if (sb == null)
                                            currentBackgroundColor = color;
                                        sb = new StringBuilder("Color List:");
                                        sb.append("\r\n#" + Integer.toHexString(color).toUpperCase());
                                    }

                                    if (sb != null)
                                        Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
                                }
                                container.setBackgroundColor(currentBackgroundColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .showColorEdit(true)
                        .setColorEditTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright))
                        .showAlphaSlider(false)
                        .build()
                        .show();


            }
        });

        BtnSetColorMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("Menu color")
                        .initialColor(currentMenuColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorChangedListener(new OnColorChangedListener() {
                            @Override
                            public void onColorChanged(int selectedColor) {
                                // Handle on color change
                                Log.d("ColorPicker", "onColorChanged: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                                if (allColors != null) {
                                    StringBuilder sb = null;

                                    for (Integer color : allColors) {
                                        if (color == null)
                                            continue;
                                        if (sb == null)
                                            currentMenuColor = color;
                                        sb = new StringBuilder("Color List:");
                                        sb.append("\r\n#" + Integer.toHexString(color).toUpperCase());
                                    }
                                    ((MenuActivity) getActivity()).cambiarColorToolbar("#" + Integer.toHexString(currentMenuColor).toUpperCase());
                                    menuColor = "#" + Integer.toHexString(currentMenuColor).toUpperCase();

                                    if (sb != null)
                                        Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .showColorEdit(true)
                        .setColorEditTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright))
                        .showAlphaSlider(false)
                        .build()
                        .show();
            }
        });

        return root;
    }


    File file;
    Bundle configContent;

    private void writeChangesOnFile(Boolean DarkMode, String BackgroundColor, String MenuColor, int TextSize)
    {
        file = new File(Environment.getDataDirectory().getAbsolutePath() + "/data/com.example.projectconsulting/config.txt");
        configContent = new Bundle();

        if (file.exists())
        {

            configContent = changeConfigFile(DarkMode, BackgroundColor, MenuColor, TextSize);
            ((MenuActivity) getActivity()).setBundleConfig(configContent);

        }
    }

    // REALIZAR CAMBIOS EN EL FICHERO
    private Bundle changeConfigFile(Boolean DarkMode, String BackgroundColor, String MenuColor, int TextSize)
    {
        Bundle configContent = new Bundle();
        File fileAux = new File(Environment.getDataDirectory().getAbsolutePath() + "/data/com.example.projectconsulting/configAux.txt");

        try {

            FileWriter fwAux = new FileWriter(fileAux, false);
            fileAux.createNewFile();

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String texto = br.readLine();

            // Se crea el fichero auxiliar con los cambios.

            while (texto != null)
            {
                switch (texto.split(":")[0])
                {
                    case "DarkMode":
                        fwAux.write("DarkMode:" + DarkMode + "\n");
                        configContent.putBoolean("DarkMode", DarkMode);
                        break;
                    case "BackgroundColor":
                        fwAux.write("BackgroundColor:" + BackgroundColor + "\n");
                        configContent.putString("BackgroundColor", BackgroundColor);
                        break;
                    case "TextSize":
                        fwAux.write("TextSize:" + TextSize + "\n");
                        configContent.putInt("TextSize", TextSize);
                        break;
                    case "MenuColor":
                        fwAux.write("MenuColor:" + menuColor);
                        configContent.putString("MenuColor", MenuColor);
                        break;
                    default:
                        fwAux.write(texto + "\n");
                        break;
                }

                texto = br.readLine();
            }

            fr.close();
            br.close();
            fwAux.close();

            FileReader frAux = new FileReader(fileAux);
            BufferedReader brAux = new BufferedReader(frAux);
            FileWriter fw = new FileWriter(file, false);
            String textoAux = brAux.readLine();

            // Modificamos el fichero original y añadimos el texto añadido en el auxiliar.

            String str = "";
            while (textoAux != null)
            {
                str = str + textoAux + "\n";
                textoAux = brAux.readLine();
            }

            fw.write(str);

            fw.close();
            brAux.close();
            frAux.close();

            // Borramos el fichero auxiliar.

            fileAux.delete();

            Toast.makeText(getActivity().getApplicationContext(), "Se han guardado los cambios", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return configContent;

    }

    private void toast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void changeBackgroundColor(int selectedColor) {
        currentBackgroundColor = selectedColor;
        binding.getRoot().setBackgroundColor(selectedColor);
    }

}
