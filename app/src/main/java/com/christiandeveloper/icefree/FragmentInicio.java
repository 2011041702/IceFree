package com.christiandeveloper.icefree;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Christian on 26/09/2016.
 */

public class FragmentInicio extends Fragment implements View.OnClickListener {


    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private boolean loggedIn = false;


    public FragmentInicio() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inicio, container, false);

        editTextEmail = (EditText)v.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)v.findViewById(R.id.editTextPassword);
        buttonLogin = (Button)v.findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);

        return v;
    }

    private void login(){
        //Getting values from edit texts
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.equalsIgnoreCase(Config.LOGIN_SUCCESS)){
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, email);

                            //Saving values to editor
                            editor.apply();

                            //Starting profile activity
                            //Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            //startActivity(intent);

                            fragment_sesion frag = new fragment_sesion();

                            FragmentManager fragmentManager = getFragmentManager();
                            //FragmentTransaction transaction = fragmentManager.beginTransaction();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_main, frag)
                                    .commit();
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_EMAIL, email);
                params.put(Config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            //Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            //startActivity(intent);
            fragment_sesion frag = new fragment_sesion();

            FragmentManager fragmentManager = getFragmentManager();
            //FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, frag)
                    .commit();
        }
    }

    @Override
    public void onClick(View view) {
        login();
    }
}
