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

	BASIC_INFO("basic_info", Type.READ),

	USER_ABOUT_ME("user_about_me", Type.READ),
	FRIENDS_ABOUT_ME("friends_about_me", Type.READ),

	USER_ACTIVITIES("user_activities", Type.READ),
	FRIENDS_ACTIVITIES("friends_activities", Type.READ),

	USER_BIRTHDAY("user_birthday", Type.READ),
	FRIENDS_BIRTHDAY("friends_birthday", Type.READ),

	USER_CHECKINS("user_checkins", Type.READ),
	FRIENDS_CHECKINS("friends_checkins", Type.READ),

	USER_EDUCATION_HISTORY("user_education_history", Type.READ),
	FRIENDS_EDUCATION_HISTORY("friends_education_history", Type.READ),

	USER_EVENTS("user_events", Type.READ),
	FRIENDS_EVENTS("friends_events", Type.READ),

	USER_GROUPS("user_groups", Type.READ),
	FRIENDS_GROUPS("friends_groups", Type.READ),

	USER_HOMETOWN("user_hometown", Type.READ),
	FRIENDS_HOMETOWN("friends_hometown", Type.READ),

	USER_INTERESTS("user_interests", Type.READ),
	FRIENDS_INTERESTS("friends_interests", Type.READ),

	USER_PHOTOS("user_photos", Type.READ),
	FRIENDS_PHOTOS("friends_photos", Type.READ),

	USER_LIKES("user_likes", Type.READ),
	FRIENDS_LIKES("friends_likes", Type.READ),

	USER_NOTES("user_notes", Type.READ),
	FRIENDS_NOTES("friends_notes", Type.READ),

	USER_ONLINE_PRESENCE("user_online_presence", Type.READ),
	FRIENDS_ONLINE_PRESENCE("friends_online_presence", Type.READ),

	USER_RELIGION_POLITICS("user_religion_politics", Type.READ),
	FRIENDS_RELIGION_POLITICS("friends_religion_politics", Type.READ),

	USER_RELATIONSHIPS("user_relationships", Type.READ),
	FRIENDS_RELATIONSHIPS("friends_relationships", Type.READ),

	USER_RELATIONSHIP_DETAILS("user_relationship_details", Type.READ),
	FRIENDS_RELATIONSHIP_DETAILS("friends_relationship_details", Type.READ),

	USER_STATUS("user_status", Type.READ),
	FRIENDS_STATUS("friends_status", Type.READ),

	USER_SUBSCRIPTIONS("user_subscriptions", Type.READ),
	FRIENDS_SUBSCRIPTIONS("friends_subscriptions", Type.READ),

	USER_VIDEOS("user_videos", Type.READ),
	FRIENDS_VIDEOS("friends_videos", Type.READ),

	USER_WEBSITE("user_website", Type.READ),
	FRIENDS_WEBSITE("friends_website", Type.READ),

	USER_WORK_HISTORY("user_work_history", Type.READ),
	FRIENDS_WORK_HISTORY("friends_work_history", Type.READ),

	USER_LOCATION("user_location", Type.READ),
	FRIENDS_LOCATION("friends_location", Type.READ),

	USER_PHOTO_VIDEO_TAGS("user_photo_video_tags", Type.READ),
	FRIENDS_PHOTO_VIDEO_TAGS("friends_photo_video_tags", Type.READ),

	READ_FRIENDLISTS("read_friendlists", Type.READ),
	READ_MAILBOX("read_mailbox", Type.READ),
	READ_REQUESTS("read_requests", Type.READ),
	READ_STREAM("read_stream", Type.READ),
	READ_INSIGHTS("read_insights", Type.READ),
	XMPP_LOGIN("xmpp_login", Type.READ),
	EMAIL("email", Type.READ),

	PUBLISH_ACTION("publish_actions", Type.PUBLISH),
	PUBLISH_STREAM("publish_stream", Type.PUBLISH),

	ADS_MANAGMENT("ads_management", Type.PUBLISH),
	CREATE_EVENT("create_event", Type.PUBLISH),
	RSVP_EVENT("rsvp_event", Type.PUBLISH),
	MANAGE_FRIENDLIST("manage_friendlists", Type.PUBLISH),
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

	public static enum Page {
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
