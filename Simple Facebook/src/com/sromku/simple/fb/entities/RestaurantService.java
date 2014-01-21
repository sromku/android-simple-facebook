package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;

public class RestaurantService {

    private final GraphObject mGraphObject;

    private RestaurantService(GraphObject graphObject) {
	mGraphObject = graphObject;
    }

    public static RestaurantService create(GraphObject graphObject) {
	return new RestaurantService(graphObject);
    }

    /**
     * Whether the restaurant is kids-friendly
     * 
     * @return
     */
    public Integer getKids() {
	return null;
    }

    /**
     * Whether the restaurant has delivery service
     * 
     * @return
     */
    public Integer getDelivery() {
	return null;
    }

    /**
     * Whether the restaurant welcomes walkins
     * 
     * @return
     */
    public Integer getWalkins() {
	return null;
    }

    /**
     * Whether the restaurant has catering service
     * 
     * @return
     */
    public Integer getCatering() {
	return null;
    }

    /**
     * Whether the restaurant takes reservations
     * 
     * @return
     */
    public Integer getReserve() {
	return null;
    }

    /**
     * Whether the restaurant is group-friendly
     * 
     * @return
     */
    public Integer getGroups() {
	return null;
    }

    /**
     * Whether the restaurant has waiters
     * 
     * @return
     */
    public Integer getWaiter() {
	return null;
    }

    /**
     * Whether the restaurant has outdoor seating
     * 
     * @return
     */
    public Integer getOutdoor() {
	return null;
    }

    /**
     * Whether the restaurant has takeout service
     * 
     * @return
     */
    public Integer getTakeout() {
	return null;
    }
}
