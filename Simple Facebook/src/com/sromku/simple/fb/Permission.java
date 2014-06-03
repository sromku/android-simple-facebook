package com.sromku.simple.fb;

import com.facebook.internal.SessionAuthorizationType;

/**
 * Hopefully all facebook permissions.
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/login/
 * @see https://developers.facebook.com/docs/reference/fql/permissions/
 */
public enum Permission {

	PUBLIC_PROFILE("public_profile", Type.READ),
	USER_ABOUT_ME("user_about_me", Type.READ),
	USER_ACTIONS_BOOKS("user_actions.books", Type.READ),
	USER_ACTIONS_MUSIC("user_actions.music", Type.READ),
	USER_ACTIONS_NEWS("user_actions.news", Type.READ),
	USER_ACTIONS_VIDEO("user_actions.video", Type.READ),
	USER_ACTIVITIES("user_activities", Type.READ),
	USER_BIRTHDAY("user_birthday", Type.READ),
	USER_EDUCATION_HISTORY("user_education_history", Type.READ),
	USER_EVENTS("user_events", Type.READ),
	USER_FRIENDS("user_friends", Type.READ),
	USER_GAMES_ACTIVITY("user_games_activity", Type.READ),
	USER_GROUPS("user_groups", Type.READ),
	USER_HOMETOWN("user_hometown", Type.READ),
	USER_INTERESTS("user_interests", Type.READ),
	USER_LIKES("user_likes", Type.READ),
	USER_LOCATION("user_location", Type.READ),
	USER_PHOTOS("user_photos", Type.READ),
	USER_RELATIONSHIPS("user_relationships", Type.READ),
	USER_RELATIONSHIP_DETAILS("user_relationship_details", Type.READ),
	USER_RELIGION_POLITICS("user_religion_politics", Type.READ),
	USER_STATUS("user_status", Type.READ),
	USER_TAGGED_PLACES("user_tagged_places", Type.READ),
	USER_VIDEOS("user_videos", Type.READ),
	USER_WEBSITE("user_website", Type.READ),
	USER_WORK_HISTORY("user_work_history", Type.READ),
	READ_FRIENDLISTS("read_friendlists", Type.READ),
	READ_INSIGHTS("read_insights", Type.READ),
	READ_MAILBOX("read_mailbox", Type.READ),
	READ_STREAM("read_stream", Type.READ),
	EMAIL("email", Type.READ),
	
	PUBLISH_ACTION("publish_actions", Type.PUBLISH),
	RSVP_EVENT("rsvp_event", Type.PUBLISH),
	MANAGE_NOTIFICATIONS("manage_notifications", Type.PUBLISH),
	MANAGE_PAGES("manage_pages", Type.PUBLISH);

	/**
	 * Permission type enum: <li>READ</li> <li>PUBLISH</li><br>
	 */
	public static enum Type {
		PUBLISH(SessionAuthorizationType.PUBLISH),
		READ(SessionAuthorizationType.READ);

		private SessionAuthorizationType sessionAuthorizationType;

		private Type(SessionAuthorizationType sessionAuthorizationType) {
			this.sessionAuthorizationType = sessionAuthorizationType;
		}
	};

	public static enum Role {
		/**
		 * Manage admins<br>
		 * Full Admin
		 */
		ADMINISTER,
		/**
		 * Edit the Page and add apps<br>
		 * Full Admin, Content Creator
		 */
		EDIT_PROFILE,
		/**
		 * Create posts as the Page<br>
		 * Full Admin, Content Creator
		 */
		CREATE_CONTENT,
		/**
		 * Respond to and delete comments, send messages as the Page<br>
		 * Full Admin, Content Creator, Moderator
		 */
		MODERATE_CONTENT,
		/**
		 * Create ads and unpublished page posts<br>
		 * Full Admin, Content Creator, Moderator, Ads Creator
		 */
		CREATE_ADS,
		/**
		 * View Insights<br>
		 * Full Admin, Content Creator, Moderator, Ads Creator, Insights Manager
		 */
		BASIC_ADMIN
	}

	private String mValue;
	private SessionAuthorizationType mType;

	private Permission(String value, Type type) {
		mValue = value;
		mType = type.sessionAuthorizationType;
	}

	public String getValue() {
		return mValue;
	}

	public SessionAuthorizationType getType() {
		return mType;
	}

	public static Permission fromValue(String pemissionValue) {
		for (Permission permission : values()) {
			if (permission.mValue.equals(pemissionValue)) {
				return permission;
			}
		}
		return null;
	}

}
