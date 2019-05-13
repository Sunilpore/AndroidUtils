package com.example.vedcomputech.pvretrotest.Frag;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vedcomputech.pvretrotest.ApiClient;
import com.example.vedcomputech.pvretrotest.ApiInterface;
import com.example.vedcomputech.pvretrotest.Details;
import com.example.vedcomputech.pvretrotest.Frag.Async.FetchImage;
import com.example.vedcomputech.pvretrotest.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ved Computech on 02-04-2018.
 */

public class DispalyDetails extends Fragment {

    ImageView image;
    TextView pathName;
    Button show;
    Details dmodel;
    Bitmap bMap;
    int urlID;
    public String setURL;

    private static final String IMAGE_URL = "http://192.168.29.35/marvel/uploads/getImage.php";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.get_details, container, false);

        urlID=2;
        image = view.findViewById(R.id.image_view);
        pathName = view.findViewById(R.id.path_name);
        show = view.findViewById(R.id.show_button);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Details> call = apiInterface.getDetails(urlID);

        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(@NonNull Call<Details> call, @NonNull Response<Details> response) {

                dmodel = response.body();

                Toast.makeText(getContext(), "Mission accomplished", Toast.LENGTH_SHORT).show();
                Log.d("testApi", "Mission successful");
            }

            @Override
            public void onFailure(@NonNull Call<Details> call, @NonNull Throwable t) {

                Log.d("testApi", "Mission fail");
                Toast.makeText(getContext(), "Mission failed", Toast.LENGTH_SHORT).show();
            }
        });


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dmodel!=null) {
                    getImageURL();
                    //image.setImageBitmap(getbMap());
                    pathName.setText(String.valueOf(dmodel.getPhoto_path()));
                    Picasso.get().load(getSetURL()).into(image);
                }
            }
        });


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void getImageURL(){

        FetchImage async = new FetchImage(dmodel,getContext(),urlID);
        async.execute(IMAGE_URL);
    }

    public Bitmap getbMap() {
        return bMap;
    }

    public void setbMap(Bitmap bMap) {
        Log.d("LOKI","Display: "+bMap);
        this.bMap = bMap;
    }

    public String getSetURL() {
        return setURL;
    }

    public void setSetURL(String setURL) {
        this.setURL = setURL;
    }

}
