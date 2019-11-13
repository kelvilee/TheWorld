package com.example.theworld;

public class TrashCanRating {
    private String id;
    private int rating;
    private String location;

    public TrashCanRating() {}

    public TrashCanRating(String id, String location, int rating) {
        this.id = id;
        this.location = location;
        this.rating = rating;
    }

    public String getId() { return id; }

    public String getLocation() {
        return location;
    }

    public int getRating() {
        return rating;
    }
}
