package com.sromku.simple.fb.entities;

import java.util.List;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permission.Role;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.StringConverter;

public class Account {

	private static final String CATEGORY = "category";
	private static final String NAME = "name";
	private static final String ACCESS_TOKEN = "access_token";
	private static final String PERMS = "perms";
	private static final String ID = "id";

	private String mCategory;
	private String mName;
	private String mAccessToken;
	private List<Role> mRoles;
	private String mId;

	private Account(GraphObject graphObject) {

		// category
		mCategory = Utils.getPropertyString(graphObject, CATEGORY);

		// name
		mName = Utils.getPropertyString(graphObject, NAME);

		// access token
		mAccessToken = Utils.getPropertyString(graphObject, ACCESS_TOKEN);

		// roles
		mRoles = Utils.convert(Utils.getPropertyJsonArray(graphObject, PERMS), new StringConverter<Role>() {
			@Override
			public Role convert(String str) {
				return Role.valueOf(str);
			}
		});

		// id
		mId = Utils.getPropertyString(graphObject, ID);
	}

	public static Account create(GraphObject graphObject) {
		return new Account(graphObject);
	}

	public String getCategory() {
		return mCategory;
	}

	public String getName() {
		return mName;
	}

	public String getAccessToken() {
		return mAccessToken;
	}

	public List<Role> getRoles() {
		return mRoles;
	}

	public String getId() {
		return mId;
	}
}
