package com.farmacia;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.farmacia.databases.Database;
import com.farmacia.location.LocationService;
import com.farmacia.models.Farmacia;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.NETWORK_PROVIDER;

public class FarmaciaMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationService mLocationService;

    private static final String TAG = FarmaciaMapsActivity.class.getSimpleName();
    private static Database mDatabase;
    private ArrayList<Farmacia> farmacias;

    boolean isGpsEnable = false;
    boolean isNetworkEnable = false;
    Context mContext;
    Location location;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmacia_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        mDatabase = new Database(this);
        mDatabase.open();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        mContext = this;
        mLocationService = new LocationService(mContext);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(NETWORK_PROVIDER);
/*
        if (isGpsEnable) {
            if (location == null) {
                if (ActivityCompat.checkSelfPermission(FarmaciaMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, myLocationListener);
                if (locationManager != null) {
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }

        }

        if (isNetworkEnable) {
            if (location == null) {
                locationManager.requestLocationUpdates(NETWORK_PROVIDER, 1000, 5, myLocationListener);
                if (locationManager != null) {
                    locationManager.getLastKnownLocation(NETWORK_PROVIDER);
                }
            }

        }
        if (location != null) {
            locationManager.requestLocationUpdates(NETWORK_PROVIDER, 1000, 5, myLocationListener);
            if (locationManager != null) {
                locationManager.getLastKnownLocation(NETWORK_PROVIDER);
            }

        }
        */
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissoes = {Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissoes, 1);
            }
        }

        farmacias = mDatabase.mfarmaciaDAO.getTodasFarmacias();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(farmacias.get(0).getGeoLocalizacao(), 10.2f));

        for (Farmacia farmacia: farmacias) {
            mMap.addMarker(new MarkerOptions().position(farmacia.getGeoLocalizacao()).title("Farmacia " + farmacia.getNome()).snippet(farmacia.getEndereco()));

        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                    Toast.makeText(mapTracking.this, ""+latLng, Toast.LENGTH_SHORT).show();
                LatLng loc = marker.getPosition();
                double lat1 = loc.latitude;
                double lng1 = loc.longitude;
                Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat1, lng1, 1);
                    Address address = addresses.get(0);
                    for (Farmacia farm:farmacias) {
                        if(marker.getPosition().equals(farm.getGeoLocalizacao())){
                            Intent intent = new Intent(getApplicationContext(), ListaProdutosActivity.class);
                            intent.putExtra("FARMACIA",farm.getId());
                            startActivity(intent);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });



    }


    /*
    final LocationListener myLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {

            mMap.clear();
            final double lat = location.getLatitude();
            final double lon = location.getLongitude();
            //db.insertMyLocation(lat,lon);
            System.out.println("LOCATIONNNN::::::::::::::::::::::::::" + lat + "," + lon + "\n" + location.getProvider());
            //System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::"+lat.getlocation().getCount());
            final LatLng latLng = new LatLng(lat, lon);

            Toast.makeText(mContext, "" + latLng + "\n" + location.getProvider(), Toast.LENGTH_SHORT).show();

            mMap.addMarker(new MarkerOptions().position(latLng).snippet("Coordinates: " + lat + " " + lon));
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.2f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.2f));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
//                    Toast.makeText(mapTracking.this, ""+latLng, Toast.LENGTH_SHORT).show();
                    LatLng loc = marker.getPosition();
                    double lat1 = loc.latitude;
                    double lng1 = loc.longitude;
                    Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(lat1, lng1, 1);
                        Address address = addresses.get(0);
                        System.out.println(":::::::::::::::::123" + address);
                        Toast.makeText(mContext, "Address:" + address.getAddressLine(0), Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return false;
                }
            });

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
*/

}
