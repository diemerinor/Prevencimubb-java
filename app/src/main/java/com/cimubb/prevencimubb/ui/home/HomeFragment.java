package com.cimubb.prevencimubb.ui.home;



import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cimubb.prevencimubb.MainActivity;
import com.cimubb.prevencimubb.MapsActivity;
import com.cimubb.prevencimubb.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.gson.Gson;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private View v;
    MapView mMapView;
    public GoogleMap mMap;
    private Button ingresar;
    public String estado,tipod;
    EditText tipodenuncia,descripcion;
    public double a;
    public double b;
    public String c,d;
    TextView mensaje1;
    TextView mensaje2;
    RequestQueue requestQueue;
    LocationManager locationManager;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        System.out.println("entro0");
        a= -36.8270776;
        b= -73.0502683;

        return v;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng punto1 = new LatLng(-36.827051, -73.05051);
        MarkerOptions option = new MarkerOptions();
        option.position((punto1)).title("wena");
        //mMap.addMarker(option);

        if(ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto1,15));*/



        buscar("http://192.168.0.17:80/cimubb/buscar.php");
    }




    private void buscar(String URL){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        a = jsonObject.getDouble("latitud");
                        b = jsonObject.getDouble("longitud");
                        estado = jsonObject.getString("estado");
                        tipod = jsonObject.getString("tipodenuncia");
                        Coordenadas(b,a,estado, tipod);
                    } catch (JSONException e) {
                        Toast.makeText(v.getContext(), "ocurrio un error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(v.getContext(),"Error de conexion", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(v.getContext());
        requestQueue.add(jsonArrayRequest);

    }

    public void Coordenadas(double longit, double lati, String estado, String tipod){
        GoogleMap googleMap = mMap;
        Antut(googleMap,longit,lati,estado,tipod);
    }

    public void Antut (GoogleMap googleMap, double longit, double lati, String estado,String tipod){

        //MARCADOR, POR LO TANTO AQUI SE PODRIA OBTENER LA LATITUD Y LONGITUD DE LA BASE DE DATOS
        // PARA LUEGO INSERTARLOS COMO MARCADORES
        mMap = googleMap;
        final LatLng punto1 = new LatLng(lati, longit);
        System.out.println("entree4");
        if(estado.equals("Pendiente")){
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(punto1).title(tipod));
        }else if(estado.equals("Aprobado")){
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(punto1).title(tipod));
        }else{
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).position(punto1).title(tipod));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto1,15));
    }



}