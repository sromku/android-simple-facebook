package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;

public class RestaurantSpecialties {

private final GraphObject mGraphObject;
    
    private RestaurantSpecialties(GraphObject graphObject) {
	mGraphObject = graphObject;
    }
    
    public static RestaurantSpecialties create(GraphObject graphObject) {
	return new RestaurantSpecialties(graphObject);
    }
    
    /**
     * Whether the restaurant serves coffee
     * @return
     */
    public Integer getCoffee() {
	return null;
    }
    
    /**
     * Whether the restaurant serves drinks
     * @return
     */
    public Integer getDrinks() {
	return null;
    }
    
    /**
     * Whether the restaurant serves breakfast
     * @return
     */
    public Integer getBreakfast() {
	return null;
    }
    
    /**
     * Whether the restaurant serves dinner
     * @return
     */
    public Integer getDinner() {
	return null;
    }
    
    /**
     * Whether the restaurant serves lunch
     * @return
     */
    public Integer getLunch() {
	return null;
    }
}
