package com.brycedai.recipefinder;

public class Recipe {


    private int id;
    private String title;
    private String imageUrl;

    public Recipe(int id, String title, String imageURL) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageURL;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
