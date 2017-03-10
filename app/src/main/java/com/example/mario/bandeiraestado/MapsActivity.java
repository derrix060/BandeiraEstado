package com.example.mario.bandeiraestado;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.method.Touch;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    List<LatLng> states = new ArrayList<>();
    List<String> statesName = new ArrayList<>();

    int actualStateIndex = 0;
    TextView txtView;
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        txtView = (TextView) this.findViewById(R.id.textView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        //initialize seek bar
        initializeSeekBar();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        populateMap();
    }

    private void populateMap(){
        // String IMAGE_PATH = "./icons/";

        Map<String, LatLng> estados = new HashMap<>();

        estados.put("AC", new LatLng(-67.851562,-9.968851));
        estados.put("AL", new LatLng(-35.859375,-9.75237));
        estados.put("AM", new LatLng(-60.029297,-3.250209));
        estados.put("BA", new LatLng(-38.496094,-13.025966));
        estados.put("CE", new LatLng(-38.583984,-3.732708));
        estados.put("ES", new LatLng(-40.517578,-18.646245));
        estados.put("GO", new LatLng(-49.306641,-16.720385));
        estados.put("MA", new LatLng(-44.384766,-2.547988));
        estados.put("MT", new LatLng(-56.118164,-15.665354));
          /*
        estados.put("MS", new LatLng(-54.667969,-20.509355));
        estados.put("MG", new LatLng(-43.857422,-19.973349));
        estados.put("PA", new LatLng(-48.691406,-1.669686));
        estados.put("PE", new LatLng(-34.936523,-8.05923));
        estados.put("PR", new LatLng(-49.21875,-25.482951));
        estados.put("PI", new LatLng(-42.802734,-5.090944));
        estados.put("RN", new LatLng(-35.200195,-5.790897));
        estados.put("RS", new LatLng(-51.328125,-30.031055));
        estados.put("RJ", new LatLng(-43.242187,-22.917923));
        estados.put("RO", new LatLng(-62.753906,-11.178402));
        estados.put("RR", new LatLng(-60.732422,2.767478));
        estados.put("SC", new LatLng(-48.603516,-27.605671));
        estados.put("SP", new LatLng(-46.669922,-23.563987));
        estados.put("SE", new LatLng(-37.133789,-10.919618));
        estados.put("TO", new LatLng(-48.251953,-10.746969));
    */

        for (Map.Entry<String, LatLng> state : estados.entrySet()){
            statesName.add(state.getKey());
            states.add(state.getValue());

            mMap.addMarker(new MarkerOptions()
                    .position(state.getValue())
                    .title(state.getKey()));
                    //.icon(BitmapDescriptorFactory
                    //        .fromPath(IMAGE_PATH + state.getKey() + ".png")));


        }

        // Move Camera
        nextState();
    }

    public void nextStateBtn(View v) {
        nextState();
    }

    private void nextState(){
        actualStateIndex = (++actualStateIndex)%states.size();
        changeMap();
    }

    public void backState(View v){
        if (actualStateIndex == 0)
            actualStateIndex = states.size()-1;
        else
            actualStateIndex --;

        changeMap();
    }

    private void changeMap (){
        if (states.size()!= 0){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(states.get(actualStateIndex), seekBar.getProgress() + 1));
            txtView.setText(statesName.get(actualStateIndex));
        }
        else{
            Toast.makeText(this, "Don't have any state!", Toast.LENGTH_LONG);
        }
    }


    private void initializeSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(i + 1), 100, null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
