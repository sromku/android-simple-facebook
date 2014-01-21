package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;

public class Parking {

private final GraphObject mGraphObject;
    
    private Parking(GraphObject graphObject) {
	mGraphObject = graphObject;
    }
    
    public static Parking create(GraphObject graphObject) {
	return new Parking(graphObject);
    }
    
    /**
     * Indicates street parking is available.
     * @return
     */
    public Integer getStreet() {
	Object property = mGraphObject.getProperty("street");
	if (property != null) {
	    return Integer.valueOf(String.valueOf(property));
	}
	return null;
    }
    
    /**
     * Indicates a parking lot is available.
     * @return
     */
    public Integer getLot() {
	Object property = mGraphObject.getProperty("lot");
	if (property != null) {
	    return Integer.valueOf(String.valueOf(property));
	}
	return null;
    }
    
    /**
     * Indicates a parking lot is available.
     * @return
     */
    public Integer getValet() {
	Object property = mGraphObject.getProperty("valet");
	if (property != null) {
	    return Integer.valueOf(String.valueOf(property));
	}
	return null;
    }
}
