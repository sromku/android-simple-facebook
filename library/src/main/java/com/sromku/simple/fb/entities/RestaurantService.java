package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

/**
 * @author sromku
 * // @see https://developers.facebook.com/docs/graph-api/reference/page
 */
public class RestaurantService {

    private static final String KIDS = "kids";
    private static final String DELIVERY = "delivery";
    private static final String WALKINS = "walkins";
    private static final String CATERING = "catering";
    private static final String RESERVE = "reserve";
    private static final String GROUPS = "groups";
    private static final String WAITER = "waiter";
    private static final String OUTDOOR = "outdoor";
    private static final String TAKEOUT = "takeout";

    @SerializedName(KIDS)
    private Integer mKids;

    @SerializedName(DELIVERY)
    private Integer mDelivery;

    @SerializedName(WALKINS)
    private Integer mWalkins;

    @SerializedName(CATERING)
    private Integer mCatering;

    @SerializedName(RESERVE)
    private Integer mReserve;

    @SerializedName(GROUPS)
    private Integer mGroups;

    @SerializedName(WAITER)
    private Integer mWaiter;

    @SerializedName(OUTDOOR)
    private Integer mOutdoor;

    @SerializedName(TAKEOUT)
    private Integer mTakeout;

    /**
     * Whether the restaurant is kids-friendly
     */
    public Integer getKids() {
        return mKids;
    }

    /**
     * Whether the restaurant has delivery service
     *
     * @return
     */
    public Integer getDelivery() {
        return mDelivery;
    }

    /**
     * Whether the restaurant welcomes walkins
     */
    public Integer getWalkins() {
        return mWalkins;
    }

    /**
     * Whether the restaurant has catering service
     */
    public Integer getCatering() {
        return mCatering;
    }

    /**
     * Whether the restaurant takes reservations
     */
    public Integer getReserve() {
        return mReserve;
    }

    /**
     * Whether the restaurant is group-friendly
     */
    public Integer getGroups() {
        return mGroups;
    }

    /**
     * Whether the restaurant has waiters
     */
    public Integer getWaiter() {
        return mWaiter;
    }

    /**
     * Whether the restaurant has outdoor seating
     */
    public Integer getOutdoor() {
        return mOutdoor;
    }

    /**
     * Whether the restaurant has takeout service
     */
    public Integer getTakeout() {
        return mTakeout;
    }
}
