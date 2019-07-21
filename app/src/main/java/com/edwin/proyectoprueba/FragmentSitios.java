package com.edwin.proyectoprueba;


import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.edwin.proyectoprueba.datos.Datos;
import com.edwin.proyectoprueba.datos.ManagerHelper;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static androidx.core.content.ContextCompat.checkSelfPermission;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSitios extends Fragment {
    File file;
    private ListView listView;

    Datos datos;
    ManagerHelper mh;


    public FragmentSitios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_sitios, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission.READ_EXTERNAL_STORAGE}, 111);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView=view.findViewById(R.id.listSitios);
        mh= new ManagerHelper(getContext());



    }

    public void importFile(View view) {

        File importDir = new File(Environment.getExternalStorageDirectory(), "/Download");
        file = new File(importDir, "Sitios.csv");//PersonCSV.csv es el nombre del archivo Csv
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String [] nextLine;
            try {

                while ((nextLine = reader.readNext()) != null) {
                    String[]array= nextLine[0].split(";");

                    String nombre=array[0];
                    String descricionCo=array[1];
                    String ubicacion=array[2];
                    String descripcionLa=array[3];

                    mh.insertarInformacion(new Datos(nombre, descricionCo, ubicacion,descripcionLa));
                    Toast.makeText(getContext(), "Dato insertado",
                            Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listar();
    }

    private void listar() {
        List<Datos>datos= mh.listar();
        String[]datosString=toStringGrupos(datos);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, datosString);

        listView.setAdapter(adapter);
    }
    public String[] toStringGrupos(List<Datos> datos) {
//se carga la lista de los datos de la bd
        datos=mh.listar();
        String [] stringGrupos=new String[datos.size()];

        for (int i=0; i<datos.size();i++){
            stringGrupos[i]=datos.get(i).getNombre()+ "\n" +datos.get(i).getFoto()+ "\n"
                    +datos.get(i).getUbicacion();
        }
        return stringGrupos;
    }

}
