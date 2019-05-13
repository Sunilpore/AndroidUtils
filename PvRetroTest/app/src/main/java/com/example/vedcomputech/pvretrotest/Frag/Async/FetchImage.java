package com.example.vedcomputech.pvretrotest.Frag.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.example.vedcomputech.pvretrotest.Details;
import com.example.vedcomputech.pvretrotest.Frag.DispalyDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchImage extends AsyncTask<String, Void, String> {


    private String imageJSONdata;

    private static final String JSON_ARRAY = "result";
    private static final String IMAGE_URL = "url";

    private JSONArray arrayImage=null;
    private int traceID;

    Details dModel;
    Context mContext;
    ProgressDialog loading;
    DispalyDetails D;

    public FetchImage(Details d, Context context, int urlID) {
        this.dModel = d;
        this.mContext = context;
        this.traceID = urlID;
        D = new DispalyDetails();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(mContext,"Fetching data... ","Please wait...!",true,true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        loading.dismiss();
        imageJSONdata = s;
        extractJSON();
        showImage();
        Toast.makeText(mContext, "URL:"+s, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected String doInBackground(String... par) {
        String uri = par[0];
        StringBuilder sb=null;
        BufferedReader br = null;

        if(uri==null){
            Log.d("StarkPower","");
        } else
            Log.d("StarkPower","else: "+uri);

        try {
            URL url =new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            sb = new StringBuilder();

            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;
            while( (json= br.readLine() )!=null){
                 sb.append(json).append("\n");
            }

            Log.d("HULK",""+sb);
            return sb.toString().trim();
        } catch (IOException e) {
            Log.d("HULK","CATCH: "+e);
            e.printStackTrace();
        }


        return null;
    }


    private void extractJSON(){

        try {
            JSONObject jsonObject = new JSONObject(imageJSONdata);
            arrayImage = jsonObject.getJSONArray(JSON_ARRAY);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void showImage(){

        try {
            JSONObject jsonObject = arrayImage.getJSONObject(0);
            getBitmapImage(jsonObject.getString(IMAGE_URL));
            // getBitmapImage(arrayImage.getJSONObject(0).getString(IMAGE_URL));
        } catch (JSONException e) {
            Log.d("MyMeth","getBitmapImage: "+e);
            e.printStackTrace();
        }
    }

    private void getBitmapImage(String url){
        //Here we call Another Async Task to write a bitmap Image via JSON data

        D.setSetURL(url);
        Log.d("HULK","getBitmapImage: "+url);
        BmapImage bMapAsync = new BmapImage(mContext);
        bMapAsync.execute(url);
    }

}
