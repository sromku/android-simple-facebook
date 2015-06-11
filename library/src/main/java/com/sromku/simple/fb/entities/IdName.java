package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * Created by sromku on 6/9/15.
 */
public class IdName {

    private static final String ID = "id";
    private static final String NAME = "name";

    @SerializedName(ID)
    protected String mId;

    @SerializedName(NAME)
    protected String mName;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "id=%s,name=%s", mId, mName);
    }
}
