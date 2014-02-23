package com.sromku.simple.fb.entities;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;

/**
 * A class that helps control the audience on Facebook that can see a post made
 * by an app on behalf of a user.
 * 
 * @author ronlut
 * @see https://developers.facebook.com/docs/reference/api/privacy-parameter/
 */
public class Privacy {
	// private static final String DESCRIPTION = "description";
	private static final String PRIVACY = "value";
	private static final String ALLOW = "allow";
	private static final String DENY = "deny";

	// private String mDescription;
	private PrivacySettings mPrivacySetting = null;
	private ArrayList<String> mAllowedUsers = new ArrayList<String>();
	private ArrayList<String> mDeniedUsers = new ArrayList<String>();

	public static enum PrivacySettings {
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

	private Privacy(GraphObject graphObject) {
		// not supported currently. It is used as output in 'Post' entity
	}

	public static Privacy create(GraphObject graphObject) {
		return new Privacy(graphObject);
	}

	public static class Builder {
		private PrivacySettings mPrivacySetting = null;
		private ArrayList<String> mAllowedUsers = new ArrayList<String>();
		private ArrayList<String> mDeniedUsers = new ArrayList<String>();

		public Builder() {
		}

		/**
		 * The privacy parameter only applies for posts to the user's own
		 * timeline<br/>
		 * and is ultimately governed by the privacy ceiling a user has
		 * configured for an app.<br/>
		 * <b>Important:</b> If you choose "CUSTOM", you must call either<br/>
		 * setAllowedUserOrListID(s) or setDeniedUserOrListID(s).
		 * 
		 * @param privacySettings
		 *            The wanted privacy settings
		 */
		public Builder setPrivacySettings(PrivacySettings privacySettings) {
			mPrivacySetting = privacySettings;
			return this;
		}

		/**
		 * Add a user or a friend list to the allowed list
		 * 
		 * @param userOrListID
		 *            user ID or friend list ID that "can" see the post
		 */
		public Builder addAllowedUserOrListID(String userOrListID) {
			validateListsAccessRequest();
			mAllowedUsers.add(userOrListID);
			return this;
		}

		/**
		 * Add a predefined friend list to the allowed list This can <b>only</b>
		 * be ALL_FRIENDS or FRIENDS_OF_FRIENDS to include all members of those
		 * sets.
		 * 
		 * @param friendList
		 *            ALL_FRIENDS or FRIENDS_OF_FRIENDS to include all members
		 *            of those sets
		 */
		public Builder addAllowedUserOrListID(PrivacySettings friendList) {
			validateListsAccessRequest();
			if (friendList != PrivacySettings.ALL_FRIENDS || friendList != PrivacySettings.FRIENDS_OF_FRIENDS) {
				UnsupportedOperationException exception = new UnsupportedOperationException("Can't add this predefined friend list. Only allowed are: ALL_FRIENDS or FRIENDS_OF_FRIENDS");
				Logger.logError(Privacy.class, "failed to add allowed users", exception);
				throw exception;
			}

			mAllowedUsers.add(friendList.name());
			return this;
		}

		/**
		 * Add users and/or friend lists to the allowed list.
		 * 
		 * @param userOrListIDs
		 *            mixed user IDs and friend list IDs that "can" see the post
		 */
		public Builder addAllowedUserOrListIDs(Collection<? extends String> userOrListIDs) {
			validateListsAccessRequest();
			mAllowedUsers.addAll(userOrListIDs);
			return this;
		}

		/**
		 * Add a user or a friend list to the denied list
		 * 
		 * @param userOrListID
		 *            user ID or friend list ID that "cannot" see the post
		 */
		public Builder addDeniedUserOrListID(String userOrListID) {
			validateListsAccessRequest();
			mDeniedUsers.add(userOrListID);
			return this;
		}

		/**
		 * Add users and/or friend lists to the denied list
		 * 
		 * @param userOrListIDs
		 *            mixed user IDs and friend list IDs that "can" see the post
		 */
		public Builder addDeniedUserOrListIDs(Collection<? extends String> userOrListIDs) {
			validateListsAccessRequest();
			mDeniedUsers.addAll(userOrListIDs);
			return this;
		}

		public Privacy build() {
			return new Privacy(this);
		}

		/**
		 * Validates that the allowed / denied lists can be accessed.<br/>
		 * In case you use a predefined privacy setting different than
		 * {@code CUSTOM}, you <b>must not</b> use the custom lists.
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
					jsonRepresentation.put(ALLOW, Utils.join(mAllowedUsers.iterator(), ','));
				}

				if (!mDeniedUsers.isEmpty()) {
					jsonRepresentation.put(DENY, Utils.join(mDeniedUsers.iterator(), ','));
				}
			}
		}
		catch (JSONException e) {
			Logger.logError(Privacy.class, "Failed to get json string from properties", e);
		}

		return jsonRepresentation.toString();
	}
}