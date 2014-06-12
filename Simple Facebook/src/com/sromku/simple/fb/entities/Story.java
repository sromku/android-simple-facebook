package com.sromku.simple.fb.entities;

import org.json.JSONArray;

import android.os.Bundle;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

/**
 * Open graph story
 * 
 * @author sromku
 * @see http://ogp.me/
 */
public class Story implements Publishable {

	@Override
	public Bundle getBundle() {
		return null;
	}

	@Override
	public String getPath() {
		return null;
	}

	@Override
	public Permission getPermission() {
		return Permission.PUBLISH_ACTION;
	}

	private Story(Builder buidler) {
		StoryObject storyObject = buidler.storyObject;
		StoryAction storyAction = buidler.storyAction;

	}

	public class Builder {

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

	// private final ActionOpenGraph mAction;
	// private final ObjectOpenGraph mObject;
	//
	// private Story(ActionOpenGraph action, ObjectOpenGraph object) {
	// mAction = action;
	// mObject = object;
	//
	// // Connect between object and action
	// mAction.putProperty(mObject.getObjectName(), mObject.getObjectUrl());
	// }
	//
	// @Override
	// public Bundle getBundle() {
	// return mAction.getProperties();
	// }
	//
	// @Override
	// public String getPath() {
	// return SimpleFacebook.getConfiguration().getNamespace() + ":" +
	// mAction.getActionName();
	// }
	//
	// @Override
	// public Permission getPermission() {
	// return Permission.PUBLISH_ACTION;
	// }
	//
	// public String getGraphPath(String appNamespace) {
	// return "me" + "/" + appNamespace + ":" + mAction.getActionName();
	// }
	//
	// public Bundle getActionBundle() {
	// return mAction.getProperties();
	// }
	//
	// public static class Builder {
	// private String mObjectName = null;
	// private String mObjectUrl = null;
	// private String mActionName = null;
	// private final Bundle mObjectBundle;
	// private final Bundle mActionBundle;
	//
	// public Builder() {
	// mObjectBundle = new Bundle();
	// mActionBundle = new Bundle();
	// }
	//
	// public Builder setObject(String objectName, String objectUrl) {
	// mObjectName = objectName;
	// mObjectUrl = objectUrl;
	// return this;
	// }
	//
	// public Builder addObjectProperty(String property, String value) {
	// mObjectBundle.putString(property, value);
	// return this;
	// }
	//
	// public Builder setAction(String action) {
	// mActionName = action;
	// return this;
	// }
	//
	// public Builder addActionProperty(String property, String value) {
	// mActionBundle.putString(property, value);
	// return this;
	// }
	//
	// public Builder addActionProperty(String property, boolean value) {
	// mActionBundle.putBoolean(property, value);
	// return this;
	// }
	//
	// public Story build() {
	// // create story
	// ObjectOpenGraph object = new ObjectOpenGraph(mObjectName, mObjectUrl);
	// object.setProperties(mObjectBundle);
	//
	// ActionOpenGraph action = new ActionOpenGraph(mActionName);
	// action.setProperties(mActionBundle);
	//
	// return new Story(action, object);
	// }
	//
	// boolean isEmpty(String str) {
	// return str == null || str.length() == 0;
	// }
	// }
	//
	// /**
	// * Action of the open graph
	// *
	// * @author sromku
	// */
	static class ActionOpenGraph {
		private Bundle mBundle;
		private final String mActionName;

		ActionOpenGraph(String actionName) {
			mBundle = new Bundle();
			mActionName = actionName;
		}

		void putProperty(String property, String value) {
			mBundle.putString(property, value);
		}

		void setProperties(Bundle bundle) {
			this.mBundle = bundle;
		}

		Bundle getProperties() {
			return mBundle;
		}

		String getActionName() {
			return mActionName;
		}
	}

	//
	// /**
	// * Object of the open graph
	// *
	// * @author sromku
	// *
	// */
	// static class ObjectOpenGraph {
	// private Bundle mBundle;
	// private final String mHostFileUrl;
	// private final String mObjectName;
	//
	// ObjectOpenGraph(String objectName, String hostFileUrl) {
	// mBundle = new Bundle();
	// mHostFileUrl = hostFileUrl;
	// mObjectName = objectName;
	// }
	//
	// void putProperty(String property, String value) {
	// mBundle.putString(property, value);
	// }
	//
	// void setProperties(Bundle bundle) {
	// this.mBundle = bundle;
	// }
	//
	// Bundle getProperties() {
	// return mBundle;
	// }
	//
	// String getObjectName() {
	// return mObjectName;
	// }
	//
	// String getObjectUrl() {
	// return mHostFileUrl + "?" + encodeUrl(mBundle);
	// }
	//
	// }

	public static class StoryAction {

		private Bundle mBundle;
		private String mActionName;

		private StoryAction(Builder builder) {
			mBundle = builder.bundle;
			mActionName = builder.actionName;
		}

		public class Builder {

			private Bundle bundle;
			private String actionName;

			public Builder setName(String name) {
				this.actionName = name;
				return this;
			}

			public Builder put(String param, String value) {
				bundle.putString(param, value);
				return this;
			}

			public StoryAction build() {
				return new StoryAction(this);
			}
		}

		public String getName() {
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

		private String mId;
		private String mType;
		private String mTitle;
		private String mUrl;
		private String mImage;
		private String mDescription;
		private Long mUpdatedTime;
		private Long mCreatedTime;
		private GraphObject mData;
		private String mAppId;
		private String mNamespace;
		private Application mApplication;

		private StoryObject(GraphObject graphObject) {

			// id
			mId = Utils.getPropertyString(graphObject, ID);

			// type
			mType = Utils.getPropertyString(graphObject, TYPE);

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
			mAppId = mApplication.getAppId();
			mNamespace = mApplication.getAppNamespace();

			// data
			mData = Utils.getPropertyGraphObject(graphObject, DATA);
		}

		private StoryObject(Builder builder) {
			mType = builder.namespace + ":" + builder.name;
			mTitle = builder.title;
			mUrl = builder.url;
			mDescription = builder.description;
			mData = builder.data;
			mImage = builder.image;
			mAppId = builder.appId;
			mNamespace = builder.namespace;
		}

		public static StoryObject create(GraphObject graphObject) {
			return new StoryObject(graphObject);
		}
		
		@Override
		public Bundle getBundle() {
			Bundle bundle = new Bundle();
			GraphObject object = GraphObject.Factory.create();
			object.setProperty("app_id", mAppId);
			object.setProperty(TYPE, mType);
			object.setProperty(URL, mUrl);
			object.setProperty(IMAGE, mImage);
			object.setProperty(TITLE, mTitle);
			object.setProperty(DESCRIPTION, mDescription);
			object.setProperty(IMAGE, mImage);
			object.setProperty(DATA, mData.toString());
			bundle.putString(OBJECT, object.toString());
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

			private String namespace;
			private String name;
			private String appId;
			private String url;
			private String image;
			private String title;
			private String description;
			private GraphObject data = null;

			public Builder() {
			}

			/**
			 * TODO - try to remove this method
			 * 
			 * @param appId
			 * @param namespace
			 * @return
			 */
			public Builder setApp(String appId, String namespace) {
				this.appId = appId;
				this.namespace = namespace;
				return this;
			}

			/**
			 * Set name of the noun. For example: food
			 * 
			 * @param name
			 * @return {@link Builder}
			 */
			public Builder setName(String name) {
				this.name = name;
				return this;
			}

			/**
			 * Set on click referencing url
			 * 
			 * @param url
			 * @return {@link Builder}
			 */
			public Builder setUrl(String url) {
				this.url = url;
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
			 * noun. For example: steak, honey, ...
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
