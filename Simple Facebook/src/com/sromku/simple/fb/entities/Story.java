package com.sromku.simple.fb.entities;

import org.json.JSONArray;

import android.os.Bundle;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permission;
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

	// public static final String CHARSET_NAME = "UTF-8";
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
	// static class ActionOpenGraph {
	// private Bundle mBundle;
	// private final String mActionName;
	//
	// ActionOpenGraph(String actionName) {
	// mBundle = new Bundle();
	// mActionName = actionName;
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
	// String getActionName() {
	// return mActionName;
	// }
	// }
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
	// private static String encodeUrl(Bundle parameters) {
	// if (parameters == null) {
	// return "";
	// }
	//
	// StringBuilder sb = new StringBuilder();
	// boolean first = true;
	// for (String key : parameters.keySet()) {
	// Object parameter = parameters.get(key);
	// if (!(parameter instanceof String)) {
	// continue;
	// }
	//
	// if (first) {
	// first = false;
	// } else {
	// sb.append("&");
	// }
	// try {
	// sb.append(URLEncoder.encode(key,
	// CHARSET_NAME)).append("=").append(URLEncoder.encode(parameters.getString(key),
	// CHARSET_NAME));
	// } catch (UnsupportedEncodingException e) {
	// Logger.logError(Story.class, "Error enconding URL", e);
	// }
	// }
	// return sb.toString();
	// }
	//
	// }

	public static class StoryAction {

	}

	public static class StoryObject {

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

			// data
			mData = Utils.getPropertyGraphObject(graphObject, DATA);
		}

		public static StoryObject create(GraphObject graphObject) {
			return new StoryObject(graphObject);
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
	}

}
