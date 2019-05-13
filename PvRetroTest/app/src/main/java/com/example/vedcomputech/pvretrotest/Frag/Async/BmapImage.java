package com.example.vedcomputech.pvretrotest.Frag.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.vedcomputech.pvretrotest.Frag.DispalyDetails;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BmapImage extends AsyncTask <String, Void, Bitmap> {

    Context bMapContext;
    ProgressDialog pg;
    DispalyDetails image;

    public BmapImage(Context mContext) {
        image = new DispalyDetails();
        this.bMapContext = mContext;
        Log.d("MyMeth","Constructor: ");
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pg = ProgressDialog.show(bMapContext,"Downloading Image","Wait for a moment",true,true);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        pg.dismiss();

    }

    @Override
    protected Bitmap doInBackground(String... params) {

        String urlBmap = params[0];
        Bitmap bitmap = null;

        try {
            URL url = new URL(urlBmap);
            /*HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.connect();
            InputStream inputStream = con.getInputStream();*/

            //InputStream inputStream = new java.net.URL(urlBmap).openStream();

          //  bitmap = BitmapFactory.decodeStream(inputStream);
          //  Log.d("STRANGE","Bitmap ERROR: "+inputStream);
            //bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (IOException e) {
            Log.d("STRANGE","Bitmap ERROR: "+e);
            e.printStackTrace();
        }
        image.setbMap(bitmap);
        return bitmap;
    }



}
