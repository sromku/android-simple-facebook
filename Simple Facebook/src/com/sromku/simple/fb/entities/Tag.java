package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

public class Tag {

    private User mUser;
    
    private Tag(GraphObject graphObject) {
	mUser = Utils.createUser(graphObject);
    }
    
    public static Tag create(GraphObject graphObject) {
	return new Tag(graphObject);
    }
    
    public User getUser() {
	return mUser;
    }
}
