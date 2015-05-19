package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

public class FamilyUser implements User {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String RELATIONSHIP = "relationship";

    @SerializedName(ID)
	private String mId = null;

    @SerializedName(NAME)
	private String mName = null;

    @SerializedName(RELATIONSHIP)
	private String mRelationship = null;

//	private FamilyUser(GraphObject graphObject) {
//		if (graphObject == null) {
//			return;
//		}
//
//		// id
//		mId = Utils.getPropertyString(graphObject, ID);
//
//		// name
//		mName = Utils.getPropertyString(graphObject, NAME);
//
//		// relationship
//		mRelationship = Utils.getPropertyString(graphObject, RELATIONSHIP);
//	}
//
//	public static FamilyUser create(GraphObject graphObject) {
//		return new FamilyUser(graphObject);
//	}

	@Override
	public String getId() {
		return mId;
	}

	@Override
	public String getName() {
		return mName;
	}

	/**
	 * The text description of the relationship between the current user and
	 * this person. <i>Like: grandmother, mother, wife, brother, father, ...</i>
	 */
	public String getRelationship() {
		return mRelationship;
	}
}
