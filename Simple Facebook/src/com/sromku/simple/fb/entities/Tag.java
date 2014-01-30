package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;

public class Tag {

    private User mUser;
    
    private Tag(GraphObject graphObject) {
	
	final String id = String.valueOf(graphObject.getProperty("id"));
	final String name = String.valueOf(graphObject.getProperty("name"));
	
	mUser = new User() {
	    @Override
	    public String getName() {
		return name;
	    }

	    @Override
	    public String getId() {
		return id;
	    }
	};
    }
    
    public static Tag create(GraphObject graphObject) {
	return new Tag(graphObject);
    }
    
    public User getUser() {
	return mUser;
    }
}
