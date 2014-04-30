package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

public class FamilyUser implements User {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String RELATIONSHIP = "relationship";

	private String mId = null;
	private String mName = null;
	private String mRelationship = null;

	private FamilyUser(GraphObject graphObject) {
		if (graphObject == null) {
			return;
		}

		// id
		mId = Utils.getPropertyString(graphObject, ID);

		// name
		mName = Utils.getPropertyString(graphObject, NAME);

		// relationship
		mRelationship = Utils.getPropertyString(graphObject, RELATIONSHIP);
	}

	public static FamilyUser create(GraphObject graphObject) {
		return new FamilyUser(graphObject);
	}

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
