package com.example.apinotification.utils.google_map_utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.apinotification.R;
import com.example.apinotification.utils.constants.Constants;
import com.example.apinotification.utils.helper.HelperMethods;
import com.example.apinotification.utils.permission.PermissionUtils;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeoFenceMethod {

    public static void setRadius(TextView displayRadius, int radius) {
        if (radius > 0) {
            String rad = String.valueOf(radius) + TextUtils.concat("m");
            displayRadius.setText(rad);
        } else {
            displayRadius.setText("");
        }
    }


/*    //Initialize Map
    public static void intializeMap(Geofencing geofencing, SupportMapFragment mapFragment) {

        mapFragment = (SupportMapFragment) geofencing.getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(geofencing);
    }*/

   /* //Initialize Places Search view fragment.
    public static PlaceAutocompleteFragment initPlaceSearchInstance(Geofencing geofencing, PlaceAutocompleteFragment autocompleteFragment) {

        autocompleteFragment = (PlaceAutocompleteFragment)
                geofencing.getFragmentManager().findFragmentById(R.id.place_search_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();

        // autocompleteFragment.setFilter(typeFilter);

        return autocompleteFragment;
    }*/


    // Update current Location Button on Map ready
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


    // startGeoFences
    public static Geofence startGeoFences(Context context, Marker geofenceMarker, String requestId, float circularRegionParam, long expDuration, int transitionTypes, PendingIntent geoPendingIntent) {

        //circularRegionParam = 400f;
        if (geofenceMarker != null) {
            Geofence geofence = createGeoFence(geofenceMarker.getPosition(), circularRegionParam, requestId, expDuration, transitionTypes);
            GeofencingRequest geoReq = createGeoRequest(geofence);
            addGeoFence(context, geoReq, geoPendingIntent);
        }


        return null;
    }

    public static Geofence createGeoFence(LatLng position, float v, String requestId, long expDuration, int transitionTypes) {

        return new Geofence.Builder()
                .setRequestId(requestId)
                .setCircularRegion(position.latitude, position.longitude, v)
                .setExpirationDuration(expDuration)
                .setTransitionTypes(transitionTypes)
                .build();
    }

    public static GeofencingRequest createGeoRequest(Geofence geofence) {

        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();
    }

    @SuppressLint("MissingPermission")
    public static void addGeoFence(Context context, GeofencingRequest geoReq, PendingIntent geoPendingIntent) {

        if (PermissionUtils.checkPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            /*LocationServices.GeofencingApi.addGeofences(client, geoReq, createGeofencingPendingIntent())
                    .setResultCallback(this);*/

            GeofencingClient geofencingClient = new GeofencingClient(context);
           // geofencingClient.addGeofences(geoReq, createGeofencingPendingIntent(context, geoPendingIntent));

        } else {
            HelperMethods.showLogData("location permission is not granted");
        }
    }

    /*private static PendingIntent createGeofencingPendingIntent(Context context, PendingIntent geoPendingIntent) {

        if (geoPendingIntent != null) {
            return geoPendingIntent;
        }

        Intent i = new Intent(context, GeoTransitionService.class);
        return PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }*/



    //Marker for Geo fence
    public static void markerForGeoFence(Context context, GoogleMap gMap, Marker geofenceMarker, LatLng latLng, SeekBar geofenceSeekBar, int resourceID ) {

        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("GeofenceMarker")
                .icon(GoogleMapUtils.bitmapDescriptorFromVectorTrans(context,resourceID));
        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        if (gMap != null && geofenceMarker != null) {
            geofenceMarker.remove();
        }

       /* float MARKER_ZOOM = 15f;
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, MARKER_ZOOM));*/
        geofenceMarker = gMap.addMarker(markerOptions);


        int circleRadius = geofenceSeekBar.getProgress();
        //drawGeoFence(circleRadius);

    }

    // Draw a geo fence circle
    public static void drawGeoFence(TextView displayGeoFenceRadius, GoogleMap gMap, Circle geoFenceLimit, Marker geofenceMarker, int progress) {

        if (geoFenceLimit != null) {
            geoFenceLimit.remove();
        }

        if (geofenceMarker != null) {
            CircleOptions circleOptions = new CircleOptions()
                    .center(geofenceMarker.getPosition())
                    .strokeColor(Color.argb(50, 70, 70, 70))
                    .fillColor(Color.argb(100, 150, 150, 150))
                    .radius(progress)
                    .center(geofenceMarker.getPosition());

            //.radius(400f);

            geoFenceLimit = gMap.addCircle(circleOptions);
            //setRadius(progress);
            GeoFenceMethod.setRadius(displayGeoFenceRadius, progress);
        }

    }


    //Adjust the zoom level of geo fencing circle according to Map view
    public static void adjustGeoFenceCamera(GoogleMap gMap, Circle geoFenceLimit, LatLng latLng) {
        float currentZoomLevel = getZoomLevel(geoFenceLimit);
        float animateZomm = currentZoomLevel - 5;

        HelperMethods.showLogData("Zoom level pos: " + animateZomm);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, animateZomm));
    }

    //Calculate the current zoom level of camera return to adjustGeoFenceCamera() to adjust geo fence circle view properly
    private static float getZoomLevel(Circle circle) {
        float zoomLevel = 0;
        if (circle != null) {

            double radius = circle.getRadius();

            if(radius<20){
                radius = 20;
            }
            double scale = radius / 3500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel + .5f;
    }


    //toggleVisibility using Transition
    public static void toggleVisibility(View... views) {
        for(View view: views){
            boolean isVisble = view.getVisibility() == View.VISIBLE;
            view.setVisibility(isVisble ? View.GONE : View.VISIBLE);
        }
    }


}
