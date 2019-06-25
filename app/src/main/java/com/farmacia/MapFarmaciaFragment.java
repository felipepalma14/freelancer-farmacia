package com.farmacia;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.NETWORK_PROVIDER;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFarmaciaFragment extends Fragment {


    GoogleMap mGoogleMap;
    LocationManager locationManager;
    boolean isGpsEnable = false;
    Location location;
    boolean isNetworkEnable = false;
    CadastraFarmaciaActivity mCadastraFarmaciaActivity;

    public MapFarmaciaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map_farmacia, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;

                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear(); //clear old markers

                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mGoogleMap.clear();
                        mGoogleMap.addMarker(new MarkerOptions().position(latLng).snippet("Coordinates: " + latLng.latitude + " " + latLng.longitude));

                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                            Address address = addresses.get(0);
                            address.getPostalCode();
                            Log.d("ADDRESS", address.toString());

                            mCadastraFarmaciaActivity.mFarmacia.setGeoLocalizacao(latLng);
                            mCadastraFarmaciaActivity.mFarmacia.setEndereco(address.getAddressLine(0));
                            mCadastraFarmaciaActivity.mFarmacia.setCep(address.getPostalCode());
                            mCadastraFarmaciaActivity.setValues();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });


            }

        });




        final LocationListener myLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {

                mGoogleMap.clear();
                final double lat = location.getLatitude();
                final double lon = location.getLongitude();
                //db.insertMyLocation(lat,lon);
                System.out.println("LOCATIONNNN::::::::::::::::::::::::::" + lat + "," + lon + "\n" + location.getProvider());
                //System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::"+lat.getlocation().getCount());
                final LatLng latLng = new LatLng(lat, lon);

                Toast.makeText(getActivity(), "" + latLng + "\n" + location.getProvider(), Toast.LENGTH_SHORT).show();

                //mGoogleMap.addMarker(new MarkerOptions().position(latLng).snippet("Coordinates: " + lat + " " + lon));
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.2f));
                CameraPosition googlePlex = CameraPosition.builder()
                        .target(latLng)
                        .zoom(12)
                        .bearing(0)
                        .tilt(45)
                        .build();

                //mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);


                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.2f));
                mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        LatLng loc = marker.getPosition();
                        double lat1 = loc.latitude;
                        double lng1 = loc.longitude;
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(lat1, lng1, 1);
                            Address address = addresses.get(0);
                            System.out.println(":::::::::::::::::123" + address);
                            Toast.makeText(getContext(), "Address:" + address.getAddressLine(0), Toast.LENGTH_LONG).show();


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

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(NETWORK_PROVIDER);

        if (isGpsEnable) {
            if (location == null) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    getActivity().finish();
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




        return rootView;
    }


    public CadastraFarmaciaActivity getmCadastraFarmaciaActivity() {
        return mCadastraFarmaciaActivity;
    }

    public void setmCadastraFarmaciaActivity(CadastraFarmaciaActivity mCadastraFarmaciaActivity) {
        this.mCadastraFarmaciaActivity = mCadastraFarmaciaActivity;
    }


}
