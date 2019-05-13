package com.example.apinotification.utils.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.example.apinotification.R;
import com.squareup.picasso.Picasso;


public class ImageUtils {

    public static void loadImage(Context context, Uri uri, ImageView imageView, int width, int height) {
        Picasso.get().load(uri).placeholder(R.mipmap.ic_launcher).resize(width, height).into(imageView);
    }

}
