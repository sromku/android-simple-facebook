package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

public class FamilyUser extends User {

    private static final String RELATIONSHIP = "relationship";

    @SerializedName(RELATIONSHIP)
    private String mRelationship = null;

    /**
     * The text description of the relationship between the current user and
     * this person. <i>Like: grandmother, mother, wife, brother, father, ...</i>
     */
    public String getRelationship() {
        return mRelationship;
    }
}
