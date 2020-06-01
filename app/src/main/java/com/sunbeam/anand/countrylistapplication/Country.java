package com.sunbeam.anand.countrylistapplication;


public class Country {
    String name, imageUrl, capital, borders;

    public Country(String name, String imageUrl, String capital, String borders) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.capital = capital;
        this.borders = borders;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCapital() {
        return capital;
    }

    public String getBorders() {
        return borders;
    }
}
