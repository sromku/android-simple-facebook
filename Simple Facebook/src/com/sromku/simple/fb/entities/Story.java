package com.sromku.simple.fb.entities;

import org.json.JSONArray;

import android.os.Bundle;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

/**
 * Open graph story
 * 
 * @author sromku
 * @see http://ogp.me/
 */
public class Story implements Publishable {

	private StoryObject storyObject;
	private StoryAction storyAction;

	@Override
	public Bundle getBundle() {
		Bundle bundle = new Bundle();
		/*
		 * set object id (that is hosted on facebook server) or url (that is
		 * hosted on your servers)
		 */
		if (storyObject.getId() != null) {
			bundle.putString(storyObject.getNoun(), storyObject.getId());
		} else {
			bundle.putString(storyObject.getNoun(), storyObject.getUrl());
		}

		// put action params if such exist
		if (storyAction.getParams() != null) {
			bundle.putAll(storyAction.getParams());
		}

		return bundle;
	}

	@Override
	public String getPath() {
		String namespace = SimpleFacebook.getConfiguration().getNamespace();
		return namespace + ":" + storyAction.getActionName();
	}

	@Override
	public Permission getPermission() {
		return Permission.PUBLISH_ACTION;
	}

	private Story(Builder buidler) {
		storyObject = buidler.storyObject;
		storyAction = buidler.storyAction;
	}

	public static class Builder {

		private StoryObject storyObject;
		private StoryAction storyAction;

		public Builder setAction(StoryAction storyAction) {
			this.storyAction = storyAction;
			return this;
		}

		public Builder setObject(StoryObject storyObject) {
			this.storyObject = storyObject;
			return this;
		}

		public Story build() {
			return new Story(this);
		}

	}

	public static class StoryAction {

		private Bundle mBundle;
		private String mActionName;

		private StoryAction(Builder builder) {
			mBundle = builder.bundle;
			mActionName = builder.actionName;
		}

		public static class Builder {

			private Bundle bundle;
			private String actionName;

			public Builder setAction(String actionName) {
				this.actionName = actionName;
				return this;
			}

			public Builder addProperty(String param, String value) {
				if (bundle == null) {
					bundle = new Bundle();
				}
				bundle.putString(param, value);
				return this;
			}

			public StoryAction build() {
				return new StoryAction(this);
			}
		}

		public String getActionName() {
			return mActionName;
		}

		public Bundle getParams() {
			return mBundle;
		}

		public String getParamValue(String param) {
			return mBundle.getString(param);
		}
	}

	public static class StoryObject implements Publishable {

		private static final String ID = "id";
		private static final String TYPE = "type";
		private static final String TITLE = "title";
		private static final String URL = "url";
		private static final String IMAGE = "image";
		private static final String DATA = "data";
		private static final String DESCRIPTION = "description";
		private static final String UPDATED_TIME = "updated_time";
		private static final String CREATED_TIME = "created_time";
		private static final String APPLICATION = "application";
		private static final String OBJECT = "object";
		private static final String PRIVACY = "privacy";

		private String mId;
		private String mType;
		private String mTitle;
		private String mUrl;
		private String mImage;
		private String mDescription;
		private Long mUpdatedTime;
		private Long mCreatedTime;
		private GraphObject mData;
		private Application mApplication;
		private Privacy mPrivacy;
		private String mNoun;

		private StoryObject(GraphObject graphObject) {

			// id
			mId = Utils.getPropertyString(graphObject, ID);

			// type
			mType = Utils.getPropertyString(graphObject, TYPE);
			if (mType != null) {
				String[] split = mType.split(":");
				if (split.length > 0) {
					mNoun = split[1];
				}
			}

			// title
			mTitle = Utils.getPropertyString(graphObject, TITLE);

			// url
			mUrl = Utils.getPropertyString(graphObject, URL);

			// image
			JSONArray jsonArray = Utils.getPropertyJsonArray(graphObject, IMAGE);
			if (jsonArray.length() > 0) {
				mImage = jsonArray.optJSONObject(0).optString(URL);
			}

			// description
			mDescription = Utils.getPropertyString(graphObject, DESCRIPTION);

			// updated time
			mUpdatedTime = Utils.getPropertyLong(graphObject, UPDATED_TIME);

			// created time
			mCreatedTime = Utils.getPropertyLong(graphObject, CREATED_TIME);

			// application
			mApplication = Application.create(Utils.getPropertyGraphObject(graphObject, APPLICATION));

			// data
			mData = Utils.getPropertyGraphObject(graphObject, DATA);
		}

		private StoryObject(Builder builder) {
			String namespace = SimpleFacebook.getConfiguration().getNamespace();
			mType = namespace + ":" + builder.noun;
			mNoun = builder.noun;
			mTitle = builder.title;
			mId = builder.id;
			mUrl = builder.url;
			mDescription = builder.description;
			mData = builder.data;
			mImage = builder.image;
			mPrivacy = builder.privacy;
		}

		public static StoryObject create(GraphObject graphObject) {
			return new StoryObject(graphObject);
		}

		@Override
		public Bundle getBundle() {
			Bundle bundle = new Bundle();
			GraphObject object = GraphObject.Factory.create();
			object.setProperty(URL, mUrl);
			object.setProperty(IMAGE, mImage);
			object.setProperty(TITLE, mTitle);
			object.setProperty(DESCRIPTION, mDescription);
			if (mData != null) {
				object.setProperty(DATA, mData);
			}
			bundle.putString(OBJECT, object.getInnerJSONObject().toString());
			if (mPrivacy != null) {
				bundle.putString(PRIVACY, mPrivacy.getJSONString());
			}
			return bundle;
		}

		@Override
		public String getPath() {
			return GraphPath.OBJECTS + "/" + mType;
		}

		@Override
		public Permission getPermission() {
			return Permission.PUBLISH_ACTION;
		}

		public String getId() {
			return mId;
		}

		public String getType() {
			return mType;
		}

		public String getNoun() {
			return mNoun;
		}

		public String getTitle() {
			return mTitle;
		}

		public String getUrl() {
			return mUrl;
		}

		public String getImage() {
			return mImage;
		}

		public String getDescription() {
			return mDescription;
		}

		public Long getUpdatedTime() {
			return mUpdatedTime;
		}

		public Long getCreatedTime() {
			return mCreatedTime;
		}

		public Application getApplication() {
			return mApplication;
		}

		public GraphObject getData() {
			return mData;
		}

		/**
		 * Get your custom parameter of your object, as you defined in Open
		 * Graph dashboard of your story.
		 * 
		 * @param param
		 * @return The value of your custom parameter
		 */
		public Object getCustomData(String param) {
			return mData.getProperty(param);
		}

		public static class Builder {

			private String id;
			private String noun;
			private String url;
			private String image;
			private String title;
			private String description;
			private GraphObject data = null;
			private Privacy privacy;

			public Builder() {
			}

			/**
			 * Set the noun. For example: food
			 * 
			 * @param noun
			 * @return {@link Builder}
			 */
			public Builder setNoun(String noun) {
				this.noun = noun;
				return this;
			}

			/**
			 * Set the url of the object. By clicking on published open graph,
			 * this url will be opened.
			 * 
			 * @param url
			 * @return {@link Builder}
			 */
			public Builder setUrl(String url) {
				this.url = url;
				return this;
			}
			
			/**
			 * Set the id of the object that was created on facebook servers.
			 * 
			 * @param id The object id
			 * @return {@link Builder}
			 */
			public Builder setId(String id) {
				this.id = id;
				return this;
			}

			/**
			 * Set image url of the object
			 * 
			 * @param imageUrl
			 * @return {@link Builder}
			 */
			public Builder setImage(String imageUrl) {
				this.image = imageUrl;
				return this;
			}

			/**
			 * Set the title of the object. This is the concrete instance of
			 * noun. For example: steak, honey, apple, ...
			 * 
			 * @param title
			 * @return {@link Builder}
			 */
			public Builder setTitle(String title) {
				this.title = title;
				return this;
			}

			/**
			 * Set the description of the concrete object
			 * 
			 * @param description
			 * @return {@link Builder}
			 */
			public Builder setDescription(String description) {
				this.description = description;
				return this;
			}

			/**
			 * Set the max privacy of the object. <br>
			 * <br>
			 * Your privacy setting can not be more public than what the person
			 * has chosen for your app. For example, if a person has chosen
			 * 'Friends' you can't set the privacy of an object to 'Public.' If
			 * you don't include a privacy parameter, the default privacy
			 * setting that the person has chosen for the app will be used for
			 * the privacy of the object.
			 * 
			 * @param privacy
			 * @return
			 */
			public Builder setPrivacy(Privacy privacy) {
				this.privacy = privacy;
				return this;
			}

			/**
			 * Add custom properties and their values
			 * 
			 * @param param
			 *            The paramater as defined in facebook dashboard of
			 *            custom story in your object
			 * @param value
			 *            The value that this param should have
			 * @return {@link Builder}
			 */
			public Builder addProperty(String param, Object value) {
				if (data == null) {
					data = GraphObject.Factory.create();
				}
				data.setProperty(param, value);
				return this;
			}

			public StoryObject build() {
				return new StoryObject(this);
			}
		}
	}

}
