package com.example.apinotification.utils.google_map_utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import com.example.apinotification.DeviceAddress;
import com.example.apinotification.utils.constants.Constants;
import com.example.apinotification.utils.helper.HelperMethods;
import com.example.apinotification.utils.permission.PermissionUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapUtils {

    //Create Drawable Vector for marker
    public static BitmapDescriptor bitmapDescriptorFromVectorTrans(Context context, int resource_id) {
        Drawable background = ContextCompat.getDrawable(context, resource_id);
        if (background != null) {
            background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());

            Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            background.draw(canvas);

            return BitmapDescriptorFactory.fromBitmap(bitmap);
        } else
            return null;
    }

    //Initialize Map
    public static void intializeMap(AppCompatActivity navigation, SupportMapFragment mapFragment, View view) {

        mapFragment = (SupportMapFragment) navigation.getSupportFragmentManager()
                .findFragmentById(view.getId());
        mapFragment.getMapAsync((OnMapReadyCallback) navigation);
    }

    //Convert LatLong into aadress
    public static List<DeviceAddress> convertLatlongToAddress(Context context, List<DeviceAddress> devices) {

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = new ArrayList<>();
        List<Address> addresses2 = new ArrayList<>();
        // double lat = 19.0714699, lon = 73.0284822;


        HelperMethods.showLogData("geocoder: "+ Geocoder.isPresent());

        try {

            double lat,lon;
            for(DeviceAddress dev:devices) {
                //for(int i=0;i<devices.size();i++) {

                HelperMethods.showLogData("geocoder address: "+dev.getAddress());
                if(dev.getAddress().trim().length()<=0){

                    lat = Double.parseDouble(dev.getLatitude());
                    lon = Double.parseDouble(dev.getLongitude());

                    addresses = geocoder.getFromLocation(lat, lon, 1);
                    addresses2 = geocoder.getFromLocationName(lat + "," + lon, 1);

                    HelperMethods.showLogData("add1 size:"+addresses.size());
                    HelperMethods.showLogData("add2 size:"+addresses2.size());
                    if (addresses.size() > 0) {
                        dev.setAddress(addresses.get(0).getAddressLine(0));
                        HelperMethods.showLogData("Single Address: " + addresses.get(0).getAddressLine(0));
                    }else if(addresses2.size() > 0){
                        dev.setAddress(addresses2.get(0).getAddressLine(0));
                        HelperMethods.showLogData("Single Address2: " + addresses2.get(0).getAddressLine(0));
                    }
                }

            }


        } catch (Exception e) {
            HelperMethods.showLogData("Geo coder error: "+e.getMessage());
        }

        return devices;
    }

    // Update current Location Button on Map ready
    //Check location permission before it call
    @SuppressLint("MissingPermission")
    public static void updateLocationUI(Context context, GoogleMap mMap) {

        if (mMap == null) {
            return;
        }

        // LatLng loc = new LatLng(mMap.getCameraPosition().target);

        try {
            if (PermissionUtils.checkPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.setOnMyLocationClickListener(location -> {
                    //markerLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                });
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                // mLastKnownLocation = null;
                PermissionUtils.requestPermissions(context, Constants.RequestPermissionTAG.GPS_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);
            }


        } catch (SecurityException e) {
            Log.e("ExceptionGeo:", e.getMessage());
        }
    }



}
