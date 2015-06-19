package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

public class Parking {

    private static final String STREET = "street";
    private static final String LOT = "lot";
    private static final String VALET = "valet";

    @SerializedName(STREET)
    private Integer mStreet;

    @SerializedName(LOT)
    private Integer mLot;

    @SerializedName(VALET)
    private Integer mValet;

    /**
     * Indicates street parking is available.
     *
     * @return
     */
    public Integer getStreet() {
        return mStreet;
    }

    /**
     * Indicates a parking lot is available.
     *
     * @return
     */
    public Integer getLot() {
        return mLot;
    }

    /**
     * Indicates a parking lot is available.
     *
     * @return
     */
    public Integer getValet() {
        return mValet;
    }
}
