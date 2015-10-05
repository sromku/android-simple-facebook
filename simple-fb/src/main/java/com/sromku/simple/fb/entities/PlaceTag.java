package com.sromku.simple.fb.entities;


import com.google.gson.annotations.SerializedName;

public class PlaceTag {

    private static final String ID = "id";
    private static final String CREATED_TIME = "created_time";
    private static final String PLACE = "place";

    @SerializedName(ID)
    private String mId;

    @SerializedName(CREATED_TIME)
    private String mCreatedTime;

    @SerializedName(PLACE)
    private Place mPlace;

    public Place getPlace() {
        return mPlace;
    }

    public String getCreatedTime() {
        return mCreatedTime;
    }

    public String getId() {
        return mId;
    }

}
