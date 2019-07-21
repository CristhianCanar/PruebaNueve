package com.edwin.proyectoprueba;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.edwin.proyectoprueba.datos.Datos;

import java.util.List;

public class Recycler extends RecyclerView.Adapter<Recycler.ViewHolder> {
    private Datos datos;

    public  class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre,descripcion,ubicacion;
        private ImageView foto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.nombreSitio);
            descripcion=itemView.findViewById(R.id.descripcionSitio);
            ubicacion=itemView.findViewById(R.id.ubicacionSitio);
            foto=itemView.findViewById(R.id.capFoto);

        }

        public Bitmap setImage(byte[] foto) {
            return BitmapFactory.decodeByteArray(foto, 0, foto.length);
        }

        public  void llenarCampos(Datos datos){
            nombre.setText(datos.getNombre());
            descripcion.setText(datos.getDescripcion());
            ubicacion.setText(datos.getUbicacion());

            Bitmap f=setImage(datos.getFoto());
            foto.setImageBitmap(f);


        }
    }
    public List<Datos> list;

    public Recycler(List<Datos> list) {
        this.list = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.llenarCampos(list.get(i));

        /*viewHolder.llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(android.content.Intent.ACTION_CALL);
                intent.setData( Uri.parse("712653127635"));

            }
        });

        viewHolder.enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent envirEmail =new Intent(Intent.ACTION_SEND , Uri.fromParts("anonimo",datos.getEmail(),null));
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
