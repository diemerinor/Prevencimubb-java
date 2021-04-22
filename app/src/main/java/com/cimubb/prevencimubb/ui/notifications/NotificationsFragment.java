package com.cimubb.prevencimubb.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cimubb.prevencimubb.Denuncias;
import com.cimubb.prevencimubb.Estadisticas;
import com.cimubb.prevencimubb.Perfil;
import com.cimubb.prevencimubb.R;

public class NotificationsFragment extends Fragment {
    private View v;


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_notifications, container, false);
        LinearLayout principal =(LinearLayout)v.findViewById(R.id.principal);
        final LinearLayout usuario = (LinearLayout)v.findViewById(R.id.usuario);
        final LinearLayout perfil = (LinearLayout)v.findViewById(R.id.perfil);
        final LinearLayout denuncias = (LinearLayout)v.findViewById(R.id.denuncias);
        final LinearLayout estadisticas = (LinearLayout)v.findViewById(R.id.estadisticas);

        principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(v.getContext(), "presionó en usuario", Toast.LENGTH_SHORT).show();
                    }
                });
                perfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(v.getContext(), "presionó en perfil", Toast.LENGTH_SHORT).show();
                        Intent perfil2 = new Intent(v.getContext(), Perfil.class);
                        startActivity(perfil2);
                    }
                });
                denuncias.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(v.getContext(), "presionó en denuncias", Toast.LENGTH_SHORT).show();
                        Intent denuncias = new Intent(v.getContext(), Denuncias.class);
                        startActivity(denuncias);
                    }
                });
                estadisticas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(v.getContext(), "presionó en estadisticas", Toast.LENGTH_SHORT).show();
                        Intent estadisticas = new Intent(v.getContext(), Estadisticas.class);
                        startActivity(estadisticas);
                    }
                });

            }
        });

        return v;
    }
}