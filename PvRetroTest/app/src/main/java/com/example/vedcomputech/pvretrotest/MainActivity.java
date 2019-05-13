package com.example.vedcomputech.pvretrotest;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vedcomputech.pvretrotest.Frag.AddDetails;
import com.example.vedcomputech.pvretrotest.Frag.DeleteDetails;
import com.example.vedcomputech.pvretrotest.Frag.DispalyDetails;
import com.example.vedcomputech.pvretrotest.Frag.UploadDetails;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

     Fragment get,add,upload,delete;
     Button mFetch, mPost, mPut, mDelete;
     Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFetch=findViewById(R.id.get_details);
        mPost=findViewById(R.id.post_details);
        mPut=findViewById(R.id.put_details);
        mDelete=findViewById(R.id.delete_details);

        get = new DispalyDetails();
        add = new AddDetails();
        upload = new UploadDetails();
        delete = new DeleteDetails();

        mContext=this;

        mFetch.setOnClickListener(this);mPost.setOnClickListener(this);
        mPut.setOnClickListener(this);mFetch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Fragment fragment=null;
        switch (view.getId()){

            case R.id.get_details :
                fragment=get;
                break;
            case R.id.post_details :
                fragment=add;
                break;
            case R.id.put_details :
                fragment=upload;
                break;
            case R.id.delete_details :
                fragment=delete;
                break;
        }

        FragmentManager mgr=getSupportFragmentManager();
        FragmentTransaction trn=mgr.beginTransaction();
        trn.replace(R.id.content_view,fragment);
        trn.addToBackStack("");
        trn.commit();
    }

}
