package com.example.theworld;

import com.google.android.gms.maps.model.LatLng;

public class PlaceOfInterest {
    private String location;
    private String name;
    private String facilityType;
    private String weblink;
    private String facilitySubType;
    private long facilityID;
    private LatLng latLng;

    public PlaceOfInterest() { }

    public PlaceOfInterest(String location, String name, String facilityType, String weblink, String facilitySubType, long facilityID, LatLng latLng) {
        this.location = location;
        this.name = name;
        this.facilityType = facilityType;
        this.weblink = weblink;
        this.facilitySubType = facilitySubType;
        this.facilityID = facilityID;
        this.latLng = latLng;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public String getWeblink() {
        return weblink;
    }

    public String getFacilitySubType() {
        return facilitySubType;
    }

    public long getFacilityID() {
        return facilityID;
    }

    public LatLng getLatLng() {
        return latLng;
    }

}
