package com.example.theworld;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Bin implements ClusterItem {
    private final LatLng mPosition;
    private String facilityId;
    private String title;
    private String containerType;
    private String rating;

    public Bin(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public Bin(double lat, double lng, String facilityId, String title, String containerType, String snippet) {
        mPosition = new LatLng(lat, lng);
        this.facilityId = facilityId;
        this.title = title;
        this.containerType = containerType;
        rating = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public String getContainerType() { return containerType; }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String s) { rating = s; }
}
