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


    public class Place extends IdName{

        private static final String LOCATION = "location";

        @SerializedName(LOCATION)
        private Location mLocation;

        public Location getLocation() {
            return mLocation;
        }
    }


    public class Location {

        private static final String STREET = "street";
        private static final String CITY = "city";
        private static final String STATE = "state";
        private static final String COUNTRY = "country";
        private static final String ZIP = "zip";
        private static final String LATITUDE = "latitude";
        private static final String LONGITUDE = "longitude";

        @SerializedName(STREET)
        private String mStreet;

        @SerializedName(CITY)
        private String mCity;

        @SerializedName(STATE)
        private String mState;

        @SerializedName(COUNTRY)
        private String mCountry;

        @SerializedName(ZIP)
        private Integer mZip;

        @SerializedName(LATITUDE)
        private Double mLatitude;

        @SerializedName(LONGITUDE)
        private Double mLongitude;

        public String getStreet() {
            return mStreet;
        }

        public String getCity() {
            return mCity;
        }

        public String getState() {
            return mState;
        }

        public String getCountry() {
            return mCountry;
        }

        public Integer getZip() {
            return mZip;
        }

        public Double getLatitude() {
            return mLatitude;
        }

        public Double getLongitude() {
            return mLongitude;
        }

    }
}
