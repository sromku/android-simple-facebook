package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sromku on 6/9/15.
 */
public class Cover {

    private static final String ID = "id";
    private static final String SOURCE = "source";
    private static final String OFFSET_X = "offset_x";
    private static final String OFFSET_Y = "offset_y";
    private static final String COVER_ID = "cover_id";

    @SerializedName(ID)
    private String mId = null;

    @SerializedName(SOURCE)
    private String mSource = null;

    @SerializedName(OFFSET_X)
    private String mOffsetX = null;

    @SerializedName(OFFSET_Y)
    private String mOffsetY = null;

    @SerializedName(COVER_ID)
    private String mCoverId = null;
}
