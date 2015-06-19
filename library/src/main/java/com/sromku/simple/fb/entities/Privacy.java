package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;
import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class that helps control the audience on Facebook that can see a post made
 * by an app on behalf of a user.
 *
 * @author ronlut
 * // @see https://developers.facebook.com/docs/reference/api/privacy-parameter/
 */
public class Privacy {

    private static final String PRIVACY = "value";
    private static final String ALLOW = "allow";
    private static final String DENY = "deny";
    private static final String DESCRIPTION = "description";
    // private static final String FRIENDS = "friends";

    @SerializedName(PRIVACY)
    private PrivacySettings mPrivacySetting = null;

    @SerializedName(ALLOW)
    private ArrayList<String> mAllowedUsers = new ArrayList<String>();

    @SerializedName(DENY)
    private ArrayList<String> mDeniedUsers = new ArrayList<String>();

    @SerializedName(DESCRIPTION)
    private String mDescription;

    public enum PrivacySettings {
        EVERYONE,
        ALL_FRIENDS,
        FRIENDS_OF_FRIENDS,
        SELF,
        CUSTOM
    }

    private Privacy(Builder builder) {
        mPrivacySetting = builder.mPrivacySetting;
        mAllowedUsers = builder.mAllowedUsers;
        mDeniedUsers = builder.mDeniedUsers;
    }

    public static class Builder {
        private PrivacySettings mPrivacySetting = null;
        private ArrayList<String> mAllowedUsers = new ArrayList<String>();
        private ArrayList<String> mDeniedUsers = new ArrayList<String>();

        public Builder() {
        }

        /**
         * The privacy parameter only applies for posts to the user's own
         * timeline and is ultimately governed by the privacy ceiling a user has
         * configured for an app.<br>
         * <br>
         * <b>Important:</b> If you choose {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM}
         * , you must call either <code>addAllowed()</code> or
         * <code>addDenied()</code>.
         *
         * @param privacySettings
         *            The wanted privacy settings
         */
        public Builder setPrivacySettings(PrivacySettings privacySettings) {
            mPrivacySetting = privacySettings;
            return this;
        }

        /**
         * Add a user or a friend list to the allowed list.<br>
         * <b>Only applicable in case of selected {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM
         * CUSTOM} in {@link #setPrivacySettings(com.sromku.simple.fb.entities.Privacy.PrivacySettings)} method.</b> <br>
         * <br>
         *
         * <b>Note:</b><br>
         * If you defined other privacy setting than
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM} and still decided to use this
         * method, then the privacy will be changed with warning to
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM}.
         *
         * @param userOrListId
         *            user Id or friend list Id that <b>can</b> see the post.
         */
        public Builder addAllowed(String userOrListId) {
            validateListsAccessRequest();
            mAllowedUsers.add(userOrListId);
            return this;
        }

        /**
         * Add a predefined friend list to the allowed list This can <b>only</b>
         * be {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#ALL_FRIENDS ALL_FRIENDS} or
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#FRIENDS_OF_FRIENDS FRIENDS_OF_FRIENDS} to
         * include all members of those sets. <br>
         * <b>Only applicable in case of selected {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM
         * CUSTOM} in {@link #setPrivacySettings(com.sromku.simple.fb.entities.Privacy.PrivacySettings)} method.</b> <br>
         * <br>
         *
         * <b>Note:</b><br>
         * If you defined other privacy setting than
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM} and still decided to use this
         * method, then the privacy will be changed with warning to
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM}.
         *
         * @param privacySettings
         *            {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#ALL_FRIENDS ALL_FRIENDS} or
         *            {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#FRIENDS_OF_FRIENDS
         *            FRIENDS_OF_FRIENDS} to include all members of those sets.
         * @throws UnsupportedOperationException
         *             in case of using other values than
         *             {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#ALL_FRIENDS ALL_FRIENDS} or
         *             {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#FRIENDS_OF_FRIENDS
         *             FRIENDS_OF_FRIENDS}
         */
        public Builder addAllowed(PrivacySettings privacySettings) {
            validateListsAccessRequest();
            if (privacySettings != PrivacySettings.ALL_FRIENDS || privacySettings != PrivacySettings.FRIENDS_OF_FRIENDS) {
                UnsupportedOperationException exception = new UnsupportedOperationException("Can't add this predefined friend list. Only allowed are: ALL_FRIENDS or FRIENDS_OF_FRIENDS");
                Logger.logError(Privacy.class, "failed to add allowed users", exception);
                throw exception;
            }

            mAllowedUsers.add(privacySettings.name());
            return this;
        }

        /**
         * Add users and/or friend lists to the allowed list. <br>
         * <b>Only applicable in case of selected {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM
         * CUSTOM} in {@link #setPrivacySettings(com.sromku.simple.fb.entities.Privacy.PrivacySettings)} method.</b> <br>
         * <br>
         *
         * <b>Note:</b><br>
         * If you defined other privacy setting than
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM} and still decided to use this
         * method, then the privacy will be changed with warning to
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM}.
         *
         * @param userOrListIds
         *            mixed user Ids and friend list Ids that <b>can</b> see the
         *            post.
         */
        public Builder addAllowed(Collection<? extends String> userOrListIds) {
            validateListsAccessRequest();
            mAllowedUsers.addAll(userOrListIds);
            return this;
        }

        /**
         * Add a user or a friend list to the denied list. <br>
         * <b>Only applicable in case of selected {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM
         * CUSTOM} in {@link #setPrivacySettings(com.sromku.simple.fb.entities.Privacy.PrivacySettings)} method.</b> <br>
         * <br>
         *
         * <b>Note:</b><br>
         * If you defined other privacy setting than
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM} and still decided to use this
         * method, then the privacy will be changed with warning to
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM}.
         *
         * @param userOrListId
         *            user Id or friend list Id that <b>cannot</b> see the post
         */
        public Builder addDenied(String userOrListId) {
            validateListsAccessRequest();
            mDeniedUsers.add(userOrListId);
            return this;
        }

        /**
         * Add users and/or friend lists to the denied list. <br>
         * <b>Only applicable in case of selected {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM
         * CUSTOM} in {@link #setPrivacySettings(com.sromku.simple.fb.entities.Privacy.PrivacySettings)} method.</b> <br>
         * <br>
         *
         * <b>Note:</b><br>
         * If you defined other privacy setting than
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM} and still decided to use this
         * method, then the privacy will be changed with warning to
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM}.
         *
         * @param userOrListIds
         *            mixed user Ids and friend list Ids that <b>can</b> see the
         *            post.
         */
        public Builder addDenied(Collection<? extends String> userOrListIds) {
            validateListsAccessRequest();
            mDeniedUsers.addAll(userOrListIds);
            return this;
        }

        public Privacy build() {
            return new Privacy(this);
        }

        /**
         * Validates that the allowed / denied lists can be accessed.<br/>
         * In case you use a predefined privacy setting different than
         * {@link com.sromku.simple.fb.entities.Privacy.PrivacySettings#CUSTOM CUSTOM}, you <b>must not</b> use the
         * custom lists.
         */
        private void validateListsAccessRequest() {
            if (mPrivacySetting != PrivacySettings.CUSTOM) {
                Logger.logWarning(Privacy.class, "Can't add / delete from allowed / denied lists when privacy setting is different than \"CUSTOM\"");
                mPrivacySetting = PrivacySettings.CUSTOM;
            }
        }

    }

    /**
     * Returns the {@code JSON} representation that should be used as the value
     * of the privacy parameter<br/>
     * in the entities that have privacy settings.
     *
     * @return A {@code String} representing the value of the privacy parameter
     */
    public String getJSONString() {
        JSONObject jsonRepresentation = new JSONObject();
        try {
            jsonRepresentation.put(PRIVACY, mPrivacySetting.name());
            if (mPrivacySetting == PrivacySettings.CUSTOM) {
                if (!mAllowedUsers.isEmpty()) {
                    jsonRepresentation.put(ALLOW, Utils.join(mAllowedUsers.iterator(), ","));
                }
                if (!mDeniedUsers.isEmpty()) {
                    jsonRepresentation.put(DENY, Utils.join(mDeniedUsers.iterator(), ","));
                }
            }
        }
        catch (JSONException e) {
            Logger.logError(Privacy.class, "Failed to get json string from properties", e);
        }

        return jsonRepresentation.toString();
    }
}