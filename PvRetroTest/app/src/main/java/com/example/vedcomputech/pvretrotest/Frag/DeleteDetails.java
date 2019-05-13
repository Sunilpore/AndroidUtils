package com.example.vedcomputech.pvretrotest.Frag;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vedcomputech.pvretrotest.R;

/**
 * Created by Ved Computech on 02-04-2018.
 */

public class DeleteDetails extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.delete_details, container,false);
        return view;
    }
}
