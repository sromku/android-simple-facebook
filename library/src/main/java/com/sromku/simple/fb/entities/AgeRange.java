package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Age range (min-max) of the user.
 *
 * @author sromku
 */
public class AgeRange {

    @SerializedName("min")
    private String mMin;

    @SerializedName("max")
    private String mMax;

    public String getMin() {
        return mMin;
    }

    public String getMax() {
        return mMax;
    }
}
