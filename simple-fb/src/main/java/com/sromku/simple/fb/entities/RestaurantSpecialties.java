package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

/**
 * @author sromku
 * // @see https://developers.facebook.com/docs/graph-api/reference/page
 */
public class RestaurantSpecialties {

    private static final String COFFEE = "coffee";
    private static final String DRINKS = "drinks";
    private static final String BREAKFAST = "breakfast";
    private static final String DINNER = "dinner";
    private static final String LUNCH = "lunch";

    @SerializedName(COFFEE)
    private Integer mCoffee;

    @SerializedName(DRINKS)
    private Integer mDrinks;

    @SerializedName(BREAKFAST)
    private Integer mBreakfast;

    @SerializedName(DINNER)
    private Integer mDinner;

    @SerializedName(LUNCH)
    private Integer mLunch;

//	private final GraphObject mGraphObject;
//
//	private RestaurantSpecialties(GraphObject graphObject) {
//		mGraphObject = graphObject;
//
//		// coffee
//		mCoffee = Utils.getPropertyInteger(graphObject, COFFEE);
//
//		// drinks
//		mDrinks = Utils.getPropertyInteger(graphObject, DRINKS);
//
//		// breakfast
//		mBreakfast = Utils.getPropertyInteger(graphObject, BREAKFAST);
//
//		// dinner
//		mDinner = Utils.getPropertyInteger(graphObject, DINNER);
//
//		// lunch
//		mLunch = Utils.getPropertyInteger(graphObject, LUNCH);
//	}
//
//	public static RestaurantSpecialties create(GraphObject graphObject) {
//		return new RestaurantSpecialties(graphObject);
//	}
//
//	public GraphObject getGraphObject() {
//		return mGraphObject;
//	}

    /**
     * Whether the restaurant serves coffee
     */
    public Integer getCoffee() {
        return mCoffee;
    }

    /**
     * Whether the restaurant serves drinks
     */
    public Integer getDrinks() {
        return mDrinks;
    }

    /**
     * Whether the restaurant serves breakfast
     *
     * @return
     */
    public Integer getBreakfast() {
        return mBreakfast;
    }

    /**
     * Whether the restaurant serves dinner
     */
    public Integer getDinner() {
        return mDinner;
    }

    /**
     * Whether the restaurant serves lunch
     */
    public Integer getLunch() {
        return mLunch;
    }
}
