package com.sromku.simple.fb.entities;

import android.os.Bundle;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class Like implements Publishable {

	private User mUser;

	private Like() {
	}
	
	private Like(GraphObject graphObject) {
		mUser = Utils.createUser(graphObject);
	}
	
	public static Like create(GraphObject graphObject) {
		return new Like(graphObject);
	}

	public User getUser() {
		return mUser;
	}

	@Override
	public Bundle getBundle() {
		return new Bundle();
	}

	@Override
	public String getPath() {
		return GraphPath.LIKES;
	}

	@Override
	public Permission getPermission() {
		return Permission.PUBLISH_ACTION;
	}
	
	/**
	 * Builder for preparing the Like object to be published.
	 */
	public static class Builder {

		public Builder() {
		}
		
		public Like build() {
			return new Like();
		}
	}
}
