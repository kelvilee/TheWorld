package com.example.theworld;

public class TrashCanRating {
    private String id;
    private String facilityid;
    private int rating;

    public TrashCanRating() {}

    public TrashCanRating(String id, String facilityid, int rating) {
        this.id = id;
        this.facilityid = facilityid;
        this.rating = rating;
    }

    public String getId() { return id; }

    public String getFacilityid() { return facilityid; }

    public int getRating() {
        return rating;
    }
}
