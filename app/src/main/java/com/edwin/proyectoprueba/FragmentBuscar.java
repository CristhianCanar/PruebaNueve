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
public class FragmentBuscar extends Fragment {
    private Button tomFoto,reg,busca,actu,elimi;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    ManagerHelper managerHelper;
    GestorHelper gestorHelper;
    private ImageView foto;
    private byte[] bytes;
    private EditText nom,des,ubi,bus;
    SQLiteDatabase db;
    Datos datos;



    public FragmentBuscar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_buscar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        managerHelper=new ManagerHelper(getContext());

        nom=view.findViewById(R.id.nombre);
        des=view.findViewById(R.id.descripcion);
        ubi=view.findViewById(R.id.ubicacion);
        bus=view.findViewById(R.id.buscando);
        foto=view.findViewById(R.id.capturarFoto);
        tomFoto=view.findViewById(R.id.tomarFotoo);
        tomFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
        /*reg=view.findViewById(R.id.registrar2);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDatos();
            }
        });*/
        actu=view.findViewById(R.id.actualizar);
        actu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarProducto();
            }
        });
        elimi=view.findViewById(R.id.eliminar);
        elimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarProducto();
            }
        });
        busca=view.findViewById(R.id.buscar);
        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarProducto();
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

    private void actualizarProducto() {
        managerHelper=new ManagerHelper(getContext());
        db=gestorHelper.getReadableDatabase();

        String[] parametros={bus.getText().toString()};
        ContentValues values=new ContentValues();

        values.put(Constantes.CAMPO1, nom.getText().toString());
        values.put(Constantes.CAMPO2, des.getText().toString());
        //values.put(Constantes.CAMPO3, foto.getDrawable().);
        values.put(Constantes.CAMPO4, ubi.getText().toString());

        db.update(Constantes.NAME_TABLE,values,Constantes.CAMPO1+"=?",parametros);
        Toast.makeText(getContext(), "SE ACTUALIZO EL PRODUCTO", Toast.LENGTH_SHORT).show();
        limpiar();
        db.close();
    }

    private void limpiar() {
        nom.setText(" ");
        des.setText(" ");
        ubi.setText(" ");
        bus.setText(" ");
    }

    private void eliminarProducto() {
        managerHelper=new ManagerHelper(getContext());
        db=gestorHelper.getReadableDatabase();

        String[] parametros={bus.getText().toString()};

        db.delete(Constantes.NAME_TABLE,Constantes.CAMPO1+"=?",parametros);
        Toast.makeText(getContext(), "SE ELIMINO EL PRODUCTO", Toast.LENGTH_SHORT).show();
        limpiar();
        db.close();
    }

   /* public Bitmap setImage(byte[] foto) {
        return BitmapFactory.decodeByteArray(foto, 0, foto.length);
    }
    Bitmap f=setImage(datos.getFoto());
            foto.setImageBitmap(f);*/


    private void buscarProducto() {
        managerHelper=new ManagerHelper(getContext());
        gestorHelper = new GestorHelper(getContext());
        db=gestorHelper.getReadableDatabase();


        String[] parametros={bus.getText().toString()};
        String[] campos={Constantes.CAMPO1,Constantes.CAMPO2,Constantes.CAMPO3,Constantes.CAMPO4};

        Cursor cursor = db.query(Constantes.NAME_TABLE,campos,Constantes.CAMPO1 + " =? ", parametros ,
                null,null,null);



        if (cursor.moveToFirst()){
            nom.setText(cursor.getString(0));
            des.setText(cursor.getString(1));
            //foto.setImageResource(cursor.getBlob(2));//posible error
            ubi.setText(cursor.getString(3));

            cursor.close();
        }else{
            Toast.makeText(getContext(), "NO SE ENCONTRO NINGUN PRODUCTO", Toast.LENGTH_SHORT).show();
            db.close();
        }


    }

}
