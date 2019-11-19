package com.example.theworld;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Bin implements ClusterItem {
    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;

    public Bin(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public Bin(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSnippet() {
        return mSnippet;
    }
}
