package com.christiandeveloper.icefree;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class fragment_detalle extends Fragment{

    private Spinner spinner;
    private TextView nombre, precio;
    private ImageView imagen;
    private String extra;
    private boolean loggedIn = false;

    private static final String EXTRA_ID = "IDMETA";

    public fragment_detalle() {
    }

    public static fragment_detalle createInstance(String id) {
        fragment_detalle detailFragment = new fragment_detalle();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, id);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detalle, container, false);

        extra = getArguments().getString(EXTRA_ID);

        spinner = (Spinner)v.findViewById(R.id.spinner);
        nombre = (TextView)v.findViewById(R.id.txtnomb);
        precio = (TextView)v.findViewById(R.id.txtprecio);
        imagen = (ImageView)v.findViewById(R.id.imgviewfoto);

        final ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.cantidad,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        Button btt = (Button)v.findViewById(R.id.buttonspiner);
        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                //Fetching the boolean value form sharedpreferences
                loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                if (loggedIn){
                    //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
                    String S_producto = nombre.getText().toString();
                    int Sub_precio = Integer.parseInt(precio.getText().toString());
                    String subprecio = precio.getText().toString();
                    String cantidad = spinner.getSelectedItem().toString();
                    int sub_total = Sub_precio * Integer.parseInt(cantidad);
                    String subtotal = String.valueOf(sub_total);

                    addEmployee(email,S_producto,cantidad,subprecio,subtotal);
                }else{
                    Toast.makeText(getActivity(), "Ingrese con alguna Cuenta, antes de realizar algun pedido", Toast.LENGTH_SHORT).show();
                }



                //Toast.makeText(getActivity(), subtotal, Toast.LENGTH_SHORT).show();


            }
        });

        getEmployee();
        return v;
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(ViewEmployee.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.DETALLE,extra);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name = c.getString(Config.TAG_NAME);
            String desg = c.getString(Config.TAG_PRE);
            String img = c.getString(Config.TAG_IMG);

            nombre.setText(name);
            precio.setText(desg);


           Glide.with(getActivity())
                    .load(img)
                    .crossFade()
                    .centerCrop()
                    .into(imagen);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addEmployee(final String email, final String producto, final String cantidad, final String precio, final String subtotal){



        class AddEmployee extends AsyncTask<Void,Void,String>{

            //ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            //    loading = ProgressDialog.show(getActivity(),"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
             //   loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_CLI,email);
                params.put(Config.KEY_EMP_PROD,producto);
                params.put(Config.KEY_EMP_CANT,cantidad);
                params.put(Config.KEY_EMP_PREC,precio);
                params.put(Config.KEY_EMP_SUB,subtotal);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.PEDIDOS, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }


}
