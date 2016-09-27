package com.christiandeveloper.icefree;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Christian on 26/09/2016.
 */

public class fragment_mis_pedidos extends Fragment {

    public fragment_mis_pedidos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_mis_pedidos, container, false);
        return v;
    }
}
