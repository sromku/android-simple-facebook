package com.sromku.simple.fb.entities;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Logger;

/**
 * The feed to be published on the wall
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/dialogs/feed/
 */
public class Feed implements Publishable {
	private Bundle mBundle = null;

	private Feed(Builder builder) {
		this.mBundle = builder.mBundle;
	}

	public Bundle getBundle() {
		return mBundle;
	}

	public String getPath() {
		return GraphPath.FEED;
	}

	@Override
	public Permission getPermission() {
		return Permission.PUBLISH_ACTION;
	}

	public static class Builder {
		Bundle mBundle;
		JSONObject mProperties = new JSONObject();
		JSONObject mActions = new JSONObject();

		public static class Parameters {
			public static final String MESSAGE = "message";
			public static final String LINK = "link";
			public static final String PICTURE = "picture";
			public static final String NAME = "name";
			public static final String CAPTION = "caption";
			public static final String DESCRIPTION = "description";
			public static final String PROPERTIES = "properties";
			public static final String ACTIONS = "actions";
			public static final String PRIVACY = "privacy";
			public static final String PLACE = "place";
		}

		public Builder() {
			mBundle = new Bundle();
		}
		
		/**
		 * 
		 * @param pageId the pageId of the place
		 * @return
		 */
		public Builder setPlace(String pageId){
			mBundle.putString(Parameters.PLACE, pageId);
			return this;
		}

		/**
		 * The name of the link attachment.
		 * 
		 * @param name
		 * @return {@link Builder}
		 */
		public Builder setName(String name) {
			mBundle.putString(Parameters.NAME, name);
			return this;
		}

		/**
		 * This message (shown as user input) attached to this post.
		 * 
		 * @param message
		 * @return {@link Builder}
		 */
		public Builder setMessage(String message) {
			mBundle.putString(Parameters.MESSAGE, message);
			return this;
		}

		/**
		 * The link attached to this post
		 * 
		 * @param link
		 * @return {@link Builder}
		 */
		public Builder setLink(String link) {
			mBundle.putString(Parameters.LINK, link);
			return this;
		}

		/**
		 * The URL of a picture attached to this post. The picture must be at
		 * least 200px by 200px
		 * 
		 * @param picture
		 * @return {@link Builder}
		 */
		public Builder setPicture(String picture) {
			mBundle.putString(Parameters.PICTURE, picture);
			return this;
		}

		/**
		 * The caption of the link (appears beneath the link name). If not
		 * specified, this field is automatically populated with the URL of the
		 * link.
		 * 
		 * @param caption
		 * @return {@link Builder}
		 */
		public Builder setCaption(String caption) {
			mBundle.putString(Parameters.CAPTION, caption);
			return this;
		}

		/**
		 * The description of the link (appears beneath the link caption). If
		 * not specified, this field is automatically populated by information
		 * scraped from the link, typically the title of the page.
		 * 
		 * @param description
		 * @return {@link Builder}
		 */
		public Builder setDescription(String description) {
			mBundle.putString(Parameters.DESCRIPTION, description);
			return this;
		}

		/**
		 * The privacy settings of the feed. If not specified, this setting is
		 * automatically populated by the privacy setting a user has configured
		 * for the app.
		 * 
		 * @param privacy
		 * @return {@link Builder}
		 */
		public Builder setPrivacy(Privacy privacy) {
			mBundle.putString(Parameters.PRIVACY, privacy.getJSONString());
			return this;
		}

		/**
		 * Object of key/value pairs which will appear in the stream attachment
		 * beneath the description, with each property on its own line.
		 * 
		 * @param text
		 * @param secondText
		 * @return {@link Builder}
		 */
		public Builder addProperty(String text, String secondText) {
			try {
				mProperties.put(text, secondText);
			}
			catch (JSONException e) {
				Logger.logError(Feed.class, "Failed to add property", e);
			}

			return this;
		}

		/**
		 * Object of key/value pairs which will appear in the stream attachment
		 * beneath the description, with each property on its own line.
		 * 
		 * @param text
		 * @param urlName
		 * @param url
		 * @return {@link Builder}
		 */
		public Builder addProperty(String text, String linkName, String link) {
			JSONObject json = new JSONObject();

			try {
				json.put("text", linkName);
				json.put("href", link);
				mProperties.put(text, json);
			}
			catch (JSONException e) {
				Logger.logError(Feed.class, "Failed to add property", e);
			}

			return this;
		}

		/**
		 * The action link which will appear next to the 'Comment' and 'Like'
		 * link under posts. The contained object must have the keys name and
		 * link.
		 * 
		 * @param name
		 * @param link
		 * @return {@link Builder}
		 */
		public Builder addAction(String name, String link) {
			try {
				mActions.put("name", name);
				mActions.put("link", link);
			}
			catch (JSONException e) {
				Logger.logError(Feed.class, "Failed to add action", e);
			}
			return this;
		}

		public Feed build() {
			// add properties if needed
			if (mProperties.length() > 0) {
				mBundle.putString(Parameters.PROPERTIES, mProperties.toString());
			}

			// add actions if needed
			if (mActions.length() > 0) {
				mBundle.putString(Parameters.ACTIONS, mActions.toString());
			}

			return new Feed(this);
		}

	}

}
