package com.example.theworld;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Bin implements ClusterItem {
    private final LatLng mPosition;
    private String mFacilityId;
    private String mTitle;
    private String mContainerType;
    private String mSnippet;

    public Bin(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public Bin(double lat, double lng, String facilityId, String title, String containerType, String snippet) {
        mPosition = new LatLng(lat, lng);
        mFacilityId = facilityId;
        mTitle = title;
        mContainerType = containerType;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getFacilityId() {
        return mFacilityId;
    }

    public String getContainerType() { return mContainerType; }

    public String getTitle() {
        return mTitle;
    }

    public String getSnippet() {
        return mSnippet;
    }

    public void setmSnippet(String s) { mSnippet = s; }
}
