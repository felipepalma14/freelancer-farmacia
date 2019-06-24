package com.farmacia.location;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Felipe Palma on 24/06/2019.
 */
public class LocationService {

    private FusedLocationProviderClient fusedLocationClient;
    private Context mContext;


    public LocationService(Context mContext) {
        this.mContext = mContext;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.mContext);
    }


    public FusedLocationProviderClient getFusedLocationClient() {
        return fusedLocationClient;
    }
}
