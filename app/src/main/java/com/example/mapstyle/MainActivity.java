package com.example.mapstyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button button;
    String location;


    FusedLocationProviderClient client;

    SupportMapFragment mapFragment;
    //    MapFragment smf;
//    FusedLocationProviderClient client;

    int[] rawArray = {R.raw.logisticmap1,R.raw.assasin_creed, R.raw.uber_style, R.raw.game_style, R.raw.vintage_style};
//    int[] rawArray = { R.raw.logisticmap1 ,R.raw.uber_style,};

    int currentIndex = 0;

    LatLng thane = new LatLng(19.2246981, 72.9779441);

    // creating array list for adding all our locations.
    private ArrayList<LatLng> locationArrayList;

    TextView tvAddress;
    double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btnChangeStyle);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setMapStyle();
            }
        });


         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        // in below line we are initializing our array list.
        locationArrayList = new ArrayList<>();


        locationArrayList.add(thane);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng thane = new LatLng(19.2246981, 72.9779441);
//        mMap.addMarker(new MarkerOptions().position(thane).title("Marker in Thane"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thane, 18.0f));



        for (int i = 0; i < locationArrayList.size(); i++) {

            if(i==0){
                location="Orion business park";
            }else if(i==1){
                location="Dombivli Railway Station";
            }else if(i==2){
                location="Thane Railway station";
            }else if(i==3){
                location="Diva Railway Station";
            }

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(location));

            // below lin is use to zoom our camera on map.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thane, 18.0f));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            // below line is use to move our camera to the specific location.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));


            //Marker mulund
            MarkerOptions markerOpt1 = new MarkerOptions();
            markerOpt1.position(new LatLng(Double.valueOf(19.2246981), Double.valueOf(72.9779441)))
                    .title(location)
                    .snippet("6XCG+QWC, Orion Business Park, Ghodbunder Rd, Kapurbawdi, Thane West, Thane, Maharashtra 400607");
            /*.icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder));*/


            //Marker dombivli
            MarkerOptions markerOpt2 = new MarkerOptions();
            markerOpt2.position(new LatLng(Double.valueOf(19.218194), Double.valueOf(73.086785)))
                    .title(location)
                    .snippet(" CST Side E-W FOB, Vishnu Nagar, Dombivli West, Dombivli, Maharashtra 421201");
            /*.icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder));*/



            //Marker thane
            MarkerOptions markerOpt3 = new MarkerOptions();
            markerOpt3.position(new LatLng(Double.valueOf(19.1860408), Double.valueOf(72.9758837)))
                    .title(location)
                    .snippet("Thane, Maharashtra");
            /*.icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder));*/


            //Marker diva
            MarkerOptions markerOpt4 = new MarkerOptions();
            markerOpt4.position(new LatLng(Double.valueOf(19.188885), Double.valueOf(73.0431215)))
                    .title(location)
                    .snippet("Sadguru Nagar, Diva, Thane, Maharashtra 400612");
            /*.icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder));*/

            //Set Custom InfoWindow Adapter
            CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MainActivity.this);
            mMap.setInfoWindowAdapter(adapter);

            mMap.addMarker(markerOpt1).showInfoWindow();
//            mMap.addMarker(markerOpt2).showInfoWindow();
//            mMap.addMarker(markerOpt3).showInfoWindow();
//            mMap.addMarker(markerOpt4).showInfoWindow();
        }

        setMapStyle();


    }

    private void setMapStyle() {


        if (currentIndex == rawArray.length && rawArray.length != 0) {
            currentIndex = 0;
        }

        try {
            mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, rawArray[currentIndex++]));

        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Cannot find style.", e);
        }
    }


    public void getmylocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                        lat=latLng.latitude;
                        lng=latLng.longitude;
                        MarkerOptions markerOptions=new MarkerOptions().position(latLng).title("You are here...!!");
                        getAddress();
                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                    }
                });
            }
        });

    }


    public void getAddress()
    {

        Address locationAddress=getAddress(lat,lng);

        if(locationAddress!=null)
        {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();

            String currentLocation;

            if(!TextUtils.isEmpty(address))
            {
                currentLocation=address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation+="\n"+address1;

                if (!TextUtils.isEmpty(city))
                {
                    currentLocation+="\n"+city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+=" - "+postalCode;
                }
                else
                {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation+="\n"+postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation+="\n"+state;

                if (!TextUtils.isEmpty(country))
                    currentLocation+="\n"+country;

//                tvEmpty.setVisibility(View.GONE);
                tvAddress.setText(currentLocation);
//                tvAddress.setVisibility(View.GONE);

               /* Thread t=new Thread(){


                    @Override
                    public void run(){

                        while(!isInterrupted()){

                            try {
                                Thread.sleep(10000);  //1000ms = 1 sec

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        tvAddress.setText("");
                                    }
                                });

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                };

                t.start();
*/
            }

        }

    }

    public Address getAddress(double latitude, double longitude)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}