package com.edwin.proyectoprueba;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.edwin.proyectoprueba.datos.Constantes;
import com.edwin.proyectoprueba.datos.Datos;
import com.edwin.proyectoprueba.datos.GestorHelper;
import com.edwin.proyectoprueba.datos.ManagerHelper;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSugerir extends Fragment {

    private Button tomFoto,reg;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    ManagerHelper managerHelper;
    GestorHelper gestorHelper;
    private ImageView foto;
    private byte[] bytes;
    private EditText nom,des,ubi;
    private Spinner spi;
    SQLiteDatabase db;

    public FragmentSugerir() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_sugerir, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        managerHelper=new ManagerHelper(getContext());

        spi=view.findViewById(R.id.spinner);
        nom=view.findViewById(R.id.nombre);
        des=view.findViewById(R.id.descripcion);
        ubi=view.findViewById(R.id.ubicacion);
        foto=view.findViewById(R.id.capturarFoto);
        tomFoto=view.findViewById(R.id.tomarFoto);
        tomFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
        reg=view.findViewById(R.id.registrar);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDatos();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            foto.setImageBitmap(bitmap);

            //CONVERTIR DE BITMAP A BYTES
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
            bytes = stream.toByteArray();


        }else{
            Toast.makeText(getContext(), "no funciona", Toast.LENGTH_SHORT).show();
        }

    }


    private void enviarDatos() {
        Datos datos=new Datos(nom.getText().toString(), des.getText().toString(),bytes, ubi.getText().toString());

        managerHelper=new ManagerHelper(getContext());
        long r=managerHelper.insertarInformacion(datos);

        if (r>0){
            Toast.makeText(getContext(), "INFORMACION GUARDADA", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "INFORMACION NO GUARDADA", Toast.LENGTH_SHORT).show();
        }

    }


}
