package com.edwin.proyectoprueba;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edwin.proyectoprueba.datos.ManagerHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHoteles extends Fragment {
    private RecyclerView recycler;
    ManagerHelper managerHelper;


    public FragmentHoteles() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_hoteles, container, false);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler =view.findViewById(R.id.recyclerView);
        managerHelper= new ManagerHelper(getContext());

        Recycler r=new Recycler(managerHelper.listar());

        recycler.setAdapter(r);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }
}
