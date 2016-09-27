package com.christiandeveloper.icefree;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class fragment_productos extends Fragment implements AdapterInicio.EscuchaEventosClick {

    private List<productos> productosList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private int requestCount = 1;
    private ProgressBar progressBar;

    public fragment_productos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_productos, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.reciclador_inicio);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        productosList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());

        //Calling method to get data to fetch data
        ObtenerDatos();
        //initializing our adapter
        adapter = new AdapterInicio(productosList, getActivity(), this);

        progressBar = (ProgressBar)v.findViewById(R.id.progressBar1);
        recyclerView.setAdapter(adapter);


        return v;
    }

    private JsonArrayRequest ObtenerDatosServidor(int requestCount) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.PRODUCTOS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        AnalaizarDatos(response);
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "No se ha podido entablar conexión", Toast.LENGTH_SHORT).show();
                    }
                });
        return jsonArrayRequest;
    }
    private void ObtenerDatos() {
        requestQueue.add(ObtenerDatosServidor(requestCount));
        requestCount++;
    }
    private void AnalaizarDatos(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            productos prom = new productos();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                prom.setId(json.getInt(Config.TAG_ID));
                prom.setNombre(json.getString(Config.TAG_NOMBRE));
                prom.setImagen(json.getString(Config.TAG_IMG));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            productosList.add(prom);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterInicio.ViewHolder holder, int posicion) {

        String id = String.valueOf(productosList.get(posicion).getId());
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment_detalle.createInstance(id), "fragment_detalle").addToBackStack("tag").commit();
    }
}
