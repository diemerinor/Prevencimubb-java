package com.cimubb.prevencimubb.ui.dashboard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cimubb.prevencimubb.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DashboardFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {
    private View v;
    //private DashboardViewModel dashboardViewModel;
    double longitud,latitud;
    String c,d;
    GoogleMap mMap;
    EditText tipodenuncia,descripcion;
    private Button ingresar;
    private Marker marcador;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tipodenuncia = v.findViewById(R.id.tipodenuncia);
        descripcion = v.findViewById(R.id.descripcion);

        //SE INICIALIZA EL BOTÓN INGRESAR
        ingresar = v.findViewById(R.id.button);
        //SE LE DA LA FUNCION EJECUTAR AL MOMENTO DE PRESIONAR EL BOTON INGRESAR
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutar("http://192.168.0.17:80/cimubb/insertar.php");
            }
        });

        return v;
    }

    private void ejecutar(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(v.getContext(), "Operacion Exitosa", Toast.LENGTH_SHORT).show();
                System.out.println("salio bien");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(v.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("salio mal");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                Map<String,Double> parametros2 = new HashMap<>();
                parametros.put("tipodenuncia",tipodenuncia.getText().toString());
                parametros.put("descripcion",descripcion.getText().toString());
                //parametros.put("descripcion",c.getText().toDouble());
                System.out.println("lalalaes"+latitud);
                parametros.put("latitud", String.valueOf(c));
                parametros.put("longitud", String.valueOf(d));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng punto1 = new LatLng(-36.8270776, -73.0502683);
        //MarkerOptions option = new MarkerOptions();
        //option.position((punto1)).title("wena");
        //mMap.addMarker(option);
        marcador = googleMap.addMarker(new MarkerOptions()
                .position(punto1)
                .draggable(true)
                .title("Arrastre el marcador")
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto1,15));
        if(ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        mMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if(marker.equals(marcador)){
            Toast.makeText(v.getContext(), "Start", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }


    @Override
    public void onMarkerDragEnd(Marker marker) {
        if(marker.equals(marcador)){
            String newTitle = String.format(Locale.getDefault(),
                    "%1$.2f,%2$,2f",
                    latitud= marker.getPosition().latitude,
                    longitud = marker.getPosition().longitude);
            Coor(latitud,longitud);
        }
    }

    public void Coor(double a,double b){
        d = Double.toString(a);
        c = Double.toString(b);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


}