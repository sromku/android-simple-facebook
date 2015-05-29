package com.sromku.simple.fb;

import com.facebook.internal.SessionAuthorizationType;
import com.sromku.simple.fb.entities.Profile;

/**
 * All facebook permissions.
 * 
 * @author sromku
 * @version Graph API v2.1
 * @see https 
 *      ://developers.facebook.com/docs/facebook-login/permissions/v2.1#reference
 */
public enum Permission {

	/**
	 * Provides access to a subset of items that are part of a person's public
	 * profile. These {@link Profile} fields can be retrieved by using this
	 * permission:<br>
	 * <ul>
	 * <li>{@link Profile.Properties#ID ID}</li>
	 * <li>{@link Profile.Properties#NAME NAME}</li>
	 * <li>{@link Profile.Properties#FIRST_NAME FIRST_NAME}</li>
	 * <li>{@link Profile.Properties#LAST_NAME LAST_NAME}</li>
	 * <li>{@link Profile.Properties#LINK LINK}</li>
	 * <li>{@link Profile.Properties#GENDER GENDER}</li>
	 * <li>{@link Profile.Properties#LOCALE LOCALE}</li>
	 * <li>{@link Profile.Properties#AGE_RANGE AGE_RANGE}</li>
	 * </ul>
	 * 
	 */
	PUBLIC_PROFILE("public_profile", Type.READ),

	/**
	 * Provides access to {@link Profile.Properties#BIO BIO} property of the
	 * {@link Profile}
	 */
	USER_ABOUT_ME("user_about_me", Type.READ),

	/**
	 * Provides access to all common books actions published by any app the
	 * person has used. This includes books they've read, want to read, rated or
	 * quoted.
	 */
	USER_ACTIONS_BOOKS("user_actions.books", Type.READ),

	/**
	 * Provides access to all common Open Graph fitness actions published by any
	 * app the person has used. This includes runs, walks and bikes actions.
	 */
	USER_ACTIONS_FITNESS("user_actions.fitness", Type.READ),

	/**
	 * Provides access to all common Open Graph music actions published by any
	 * app the person has used. This includes songs they've listened to, and
	 * playlists they've created.
	 */
	USER_ACTIONS_MUSIC("user_actions.music", Type.READ),

	/**
	 * Provides access to all common Open Graph news actions published by any
	 * app the person has used which publishes these actions. This includes news
	 * articles they've read or news articles they've published.
	 */
	USER_ACTIONS_NEWS("user_actions.news", Type.READ),

	/**
	 * Provides access to all common Open Graph video actions published by any
	 * app the person has used which publishes these actions. This includes
	 * videos they've watched, videos they've rated and videos they want to
	 * watch.
	 */
	USER_ACTIONS_VIDEO("user_actions.video", Type.READ),

	/**
	 * Provides access to a person's list of activities as listed on their
	 * Profile. This is a subset of the pages they have liked, where those pages
	 * represent particular interests. This information is accessed through the
	 * activities connection on the user node.
	 */
	USER_ACTIVITIES("user_activities", Type.READ),

	/**
	 * Access the date and month of a person's birthday. This may or may not
	 * include the person's year of birth, dependent upon their privacy settings
	 * and the access token being used to query this field.
	 */
	USER_BIRTHDAY("user_birthday", Type.READ),

	/**
	 * Provides access to {@link Profile.Properties#EDUCATION EDUCATION}
	 * property of the {@link Profile}
	 */
	USER_EDUCATION_HISTORY("user_education_history", Type.READ),

	/**
	 * Provides read-only access to the Events a person is hosting or has RSVP'd
	 * to.
	 */
	USER_EVENTS("user_events", Type.READ),

	/**
	 * Provides access the list of friends that also use your app. In order for
	 * a person to show up in one person's friend list, <b>both people</b> must
	 * have decided to share their list of friends with your app and not
	 * disabled that permission during login.
	 */
	USER_FRIENDS("user_friends", Type.READ),

	/**
	 * Provides access to read a person's game activity (scores, achievements)
	 * in any game the person has played.
	 */
	USER_GAMES_ACTIVITY("user_games_activity", Type.READ),

	/**
	 * Enables your app to read the Groups a person is a member of through the
	 * groups edge on the User object. This permission does not allow you to
	 * create groups on behalf of a person. It is not possible to create groups
	 * via the Graph API
	 */
	USER_GROUPS("user_groups", Type.READ),

	/**
	 * Provides access to a person's hometown location through the hometown
	 * field on the User object. This is set by the user on the Profile.
	 */
	USER_HOMETOWN("user_hometown", Type.READ),

	/**
	 * Provides access to the list of interests in a person's Profile. This is a
	 * subset of the pages they have liked which represent particular interests.
	 */
	USER_INTERESTS("user_interests", Type.READ),

	/**
	 * Provides access to the list of all Facebook Pages and Open Graph objects
	 * that a person has liked. This list is available through the likes edge on
	 * the User object.
	 */
	USER_LIKES("user_likes", Type.READ),

	/**
	 * Provides access to a person's current city through the location field on
	 * the User object. The current city is set by a person on their Profile.
	 */
	USER_LOCATION("user_location", Type.READ),

	/**
	 * Provides access to the photos a person has uploaded or been tagged in.
	 * This is available through the photos edge on the User object.
	 */
	USER_PHOTOS("user_photos", Type.READ),

	/**
	 * Provides access to a person's relationship status, significant other and
	 * family members as fields on the User object.
	 */
	USER_RELATIONSHIPS("user_relationships", Type.READ),

	/**
	 * Provides access to a person's relationship interests as the interested_in
	 * field on the User object.
	 */
	USER_RELATIONSHIP_DETAILS("user_relationship_details", Type.READ),

	/**
	 * Provides access to a person's religious and political affiliations.
	 */
	USER_RELIGION_POLITICS("user_religion_politics", Type.READ),

	/**
	 * Provides access to a person's statuses. These are posts on Facebook which
	 * don't include links, videos or photos.
	 */
	USER_STATUS("user_status", Type.READ),

	/**
	 * Provides access to the Places a person has been tagged at in photos,
	 * videos, statuses and links.
	 */
	USER_TAGGED_PLACES("user_tagged_places", Type.READ),

	/**
	 * Provides access to the videos a person has uploaded or been tagged in
	 */
	USER_VIDEOS("user_videos", Type.READ),

	/**
	 * Provides access to the person's personal website URL via the website
	 * field on the {@link Profile} entity.
	 */
	USER_WEBSITE("user_website", Type.READ),

	/**
	 * Provides access to a person's work history and list of employers via the
	 * work field on the {@link Profile} entity.
	 */
	USER_WORK_HISTORY("user_work_history", Type.READ),

	/**
	 * Provides access to the names of custom lists a person has created to
	 * organize their friends. This is useful for rendering an audience selector
	 * when someone is publishing stories to Facebook from your app. This
	 * permission does not give access to a list of person's friends. If you
	 * want to access a person's friends who also use your app, you should use
	 * the user_friends permission. This permission also does not give the list
	 * of friends who are part of a friendlist. It only gives access to the
	 * names of the lists.<br>
	 * <br>
	 * 
	 * <b>Note:</b><br>
	 * Extended Permissions give access to more sensitive information and give
	 * your app the ability to publish and delete data. All extended permissions
	 * appear on a separate screen during the login flow so a person can decide
	 * if they want to grant them.
	 */
	READ_FRIENDLISTS("read_friendlists", Type.READ),

	/**
	 * Provides read-only access to the Insights data for Pages, Apps and web
	 * domains the person owns.
	 */
	READ_INSIGHTS("read_insights", Type.READ),

	/**
	 * Provides the ability to read the messages in a person's Facebook Inbox
	 * through the inbox edge and the thread node
	 */
	READ_MAILBOX("read_mailbox", Type.READ),

	/**
	 * Provides the ability to read from the Page Inboxes of the Pages managed
	 * by a person. This permission is often used alongside the manage_pages
	 * permission.
	 * 
	 * This permission does not let your app read the page owner's mailbox. It
	 * only applies to the page's mailbox.
	 */
	READ_PAGE_MAILBOX("read_page_mailboxes", Type.READ),

	/**
	 * Provides access to read the posts in a person's News Feed, or the posts
	 * on their Profile.
	 */
	READ_STREAM("read_stream", Type.READ),

	/**
	 * Provides access to the person's primary email address via the
	 * {@link Profile.Properties#EMAIL} property on the {@link Profile} object.<br>
	 * <br>
	 * <b>Note:</b><br>
	 * Even if you request the email permission it is not guaranteed you will
	 * get an email address. For example, if someone signed up for Facebook with
	 * a phone number instead of an email address, the email field may be empty.
	 */
	EMAIL("email", Type.READ),

	/**
	 * Provides access to publish Posts, Open Graph actions, achievements,
	 * scores and other activity on behalf of a person using your app.
	 */
	PUBLISH_ACTION("publish_actions", Type.PUBLISH),

	/**
	 * Provides the ability to set a person's attendee status on Facebook Events
	 * (e.g. attending, maybe, or declined). This permission does not let you
	 * invite people to an event. This permission does not let you update an
	 * event's details. This permission does not let you create an event. There
	 * is no way to create an event via the API as of Graph API v2.0.
	 */
	RSVP_EVENT("rsvp_event", Type.PUBLISH),

	/**
	 * Enables your app to read a person's notifications and mark them as read.
	 * This permission does not let you send notifications to a person.
	 */
	MANAGE_NOTIFICATIONS("manage_notifications", Type.PUBLISH),

	/**
	 * Enables your app to retrieve Page Access Tokens for the Pages and Apps
	 * that the person administrates.
	 */
	MANAGE_PAGES("manage_pages", Type.PUBLISH);

	// TODO - add support for new permission: 'user_actions:{app_namespace}'

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
