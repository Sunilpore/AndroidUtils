package com.example.apinotification.utils.location;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.apinotification.utils.exception.ExceptionUtils;
import com.example.apinotification.utils.helper.HelperMethods;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created on 26-10-2018.
 */
public class LocationUtils {

    /**
     * Get locations accurate address
     *
     * @param latitude  -> input latitude of current location
     * @param longitude -> input longitude of current location
     * @return -> returns current location accurate address
     */
    public static String getAddress(@NonNull Context context, double latitude, double longitude) throws IOException {
        String address = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = getLocationDetailObj(context, latitude, longitude);
        if (addresses.size() > 0) {
            address = addresses.get(0).getAddressLine(0);
        } else {
            //Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            address = geocoder.getFromLocationName(latitude + "," + longitude, 1).get(0).getAddressLine(0);
            //return "no address found";
        }
        if (!TextUtils.isEmpty(address)) {
            return address;
        } else {
            return latitude + "," + longitude;
        }
        //return null;
    }

    /**
     * Get locations accurate PostalCode
     *
     * @param latitude  -> input latitude of current location
     * @param longitude -> input longitude of current location
     * @return -> returns current location accurate PostalCode
     */
    public static String getPostalCode(@NonNull Context context, double latitude, double longitude) throws IOException {

        List<Address> addresses = getLocationDetailObj(context, latitude, longitude);
        if (addresses.size() > 0) {
            return addresses.get(0).getPostalCode();
        }

        return null;
    }

    /**
     * Get locations accurate CountryName
     *
     * @param latitude  -> input latitude of current location
     * @param longitude -> input longitude of current location
     * @return -> returns current location accurate CountryName
     */
    public static String getCountryName(@NonNull Context context, double latitude, double longitude) throws IOException {

        List<Address> addresses = getLocationDetailObj(context, latitude, longitude);
        if (addresses.size() > 0) {
            return addresses.get(0).getCountryName();
        }
        return null;
    }


    public static boolean isGpsOnn(@NonNull Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return false;
    }

    /**
     * @param latitude  -> input latitude of current location
     * @param longitude -> input longitude of current location
     */
    private static List<Address> getLocationDetailObj(@NonNull Context context, double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        return geocoder.getFromLocation(latitude, longitude, 1);
    }


    //Show default alert dialog from google to turn onn GPS
    public static void turnOnGps(Activity activity, Context context, int REQUEST_CHECK_SETTINGS) {
        //turnOnHere
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        builder.setAlwaysShow(true); // this is the key ingredient

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(context).checkLocationSettings(builder.build());
        result.addOnCompleteListener(task -> {
            try {
                task.getResult(ApiException.class);
            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            resolvable.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            HelperMethods.showLogError("IntentSender.SendIntentException: " + e.getMessage());
                            ExceptionUtils.writeCrashLogToFile(context, e);
                        } catch (ClassCastException e) {
                            HelperMethods.showLogError("ClassCastException: " + e.getMessage());
                            ExceptionUtils.writeCrashLogToFile(context, e);
                        }
                        break;
                }
            }
        });
    }


}
