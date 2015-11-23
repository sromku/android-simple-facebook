package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

public class Work {

    //	private static final String NAME = "name";
    private static final String EMPLOYER = "employer";
    private static final String LOCATION = "location";
    private static final String POSITION = "position";
    private static final String DESCRIPTION = "description";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";

    @SerializedName(EMPLOYER)
    private User mEmployer;

    @SerializedName(LOCATION)
    private Location mLocation;

    @SerializedName(POSITION)
    private IdName mPosition;

    @SerializedName(DESCRIPTION)
    private String mDescription;

    @SerializedName(START_DATE)
    private String mStartDate;

    @SerializedName(END_DATE)
    private String mEndDate;

    public User getEmployer() {
        return mEmployer;
    }

    public Location getLocation() {
        return mLocation;
    }

    public IdName getPosition() {
        return mPosition;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getEndDate() {
        return mEndDate;
    }
}
