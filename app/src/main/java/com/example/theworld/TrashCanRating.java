package com.example.theworld;

public class TrashCanRating {
    private String id;
    private String facilityid;
    private int rating;
    private String location;

    public TrashCanRating() {}

    public TrashCanRating(String id, String facilityid, int rating, String location) {
        this.id = id;
        this.facilityid = facilityid;
        this.rating = rating;
        this.location = location;
    }

    public String getId() { return id; }

    public String getFacilityid() { return facilityid; }

    public int getRating() {
        return rating;
    }

    public String getLocation() { return location; }
}
