package com.kotu.astroweather.data;

import io.realm.RealmObject;


public class FavouriteLocation extends RealmObject {
    private String location;

    public FavouriteLocation(String location){
        this.location = location;
    }

    public FavouriteLocation(){}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString(){
        return location;
    }

}
