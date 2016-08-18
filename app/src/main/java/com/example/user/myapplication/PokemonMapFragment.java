package com.example.user.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by user on 2016/8/18.
 */
public class PokemonMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public static PokemonMapFragment newInstance() {

        Bundle args = new Bundle();

        PokemonMapFragment fragment = new PokemonMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_map, container, false);
            setHasOptionsMenu(true);
            setMenuVisibility(true);
        }

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(this);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.childFragmentContainer, mapFragment)
                .commit();
    }

    GoogleMap map;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);

        UiSettings mapSettings = googleMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true); //controlled by UI widgets
        mapSettings.setZoomGesturesEnabled(true);

    }
    
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
