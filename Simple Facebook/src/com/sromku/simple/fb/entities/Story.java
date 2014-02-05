package com.sromku.simple.fb.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.os.Bundle;
import com.sromku.simple.fb.utils.Logger;

/**
 * Open graph story
 * 
 * @author sromku
 * @see http://ogp.me/
 */
public class Story
{
	public static final String CHARSET_NAME = "UTF-8";
	private final ActionOpenGraph mAction;
	private final ObjectOpenGraph mObject;

	private Story(ActionOpenGraph action, ObjectOpenGraph object)
	{
		mAction = action;
		mObject = object;

		// Connect between object and action
		mAction.putProperty(mObject.getObjectName(), mObject.getObjectUrl());
	}

	public String getGraphPath(String appNamespace)
	{
		return "me" + "/" + appNamespace + ":" + mAction.getActionName();
	}

	public Bundle getActionBundle()
	{
		return mAction.getProperties();
	}

	public static class Builder
	{
		private String mObjectName = null;
		private String mObjectUrl = null;
		private String mActionName = null;
		private final Bundle mObjectBundle;
		private final Bundle mActionBundle;

		public Builder()
		{
			mObjectBundle = new Bundle();
			mActionBundle = new Bundle();
		}

		public Builder setObject(String objectName, String objectUrl)
		{
			mObjectName = objectName;
			mObjectUrl = objectUrl;
			return this;
		}

		public Builder addObjectProperty(String property, String value)
		{
			mObjectBundle.putString(property, value);
			return this;
		}

		public Builder setAction(String action)
		{
			mActionName = action;
			return this;
		}

		public Builder addActionProperty(String property, String value)
		{
			mActionBundle.putString(property, value);
			return this;
		}

		public Builder addActionProperty(String property, boolean value)
		{
			mActionBundle.putBoolean(property, value);
			return this;
		}

		public Story build()
		{
			// // validate input
			// validate();

			// create story
			ObjectOpenGraph object = new ObjectOpenGraph(mObjectName, mObjectUrl);
			object.setProperties(mObjectBundle);

			ActionOpenGraph action = new ActionOpenGraph(mActionName);
			action.setProperties(mActionBundle);

			return new Story(action, object);
		}

		// private void validate()
		// {
		// if (isEmpty(mObjectName))
		// {
		// throw new RuntimeException("Object name must be set");
		// }
		//
		// if (isEmpty(mObjectUrl))
		// {
		// throw new RuntimeException("Object url must be set");
		// }
		//
		// if (isEmpty(mActionName))
		// {
		// throw new RuntimeException("Action must be set");
		// }
		// }

		boolean isEmpty(String str)
		{
			return str == null || str.length() == 0;
		}
	}

	/**
	 * Action of the open graph
	 * 
	 * @author sromku
	 */
	static class ActionOpenGraph
	{
		private Bundle mBundle;
		private final String mActionName;

		ActionOpenGraph(String actionName)
		{
			mBundle = new Bundle();
			mActionName = actionName;
		}

		void putProperty(String property, String value)
		{
			mBundle.putString(property, value);
		}

		void setProperties(Bundle bundle)
		{
			this.mBundle = bundle;
		}

		Bundle getProperties()
		{
			return mBundle;
		}

		String getActionName()
		{
			return mActionName;
		}
	}

	/**
	 * Object of the open graph
	 * 
	 * @author sromku
	 * 
	 */
	static class ObjectOpenGraph
	{
		private Bundle mBundle;
		private final String mHostFileUrl;
		private final String mObjectName;

		ObjectOpenGraph(String objectName, String hostFileUrl)
		{
			mBundle = new Bundle();
			mHostFileUrl = hostFileUrl;
			mObjectName = objectName;
		}

		void putProperty(String property, String value)
		{
			mBundle.putString(property, value);
		}

		void setProperties(Bundle bundle)
		{
			this.mBundle = bundle;
		}

		Bundle getProperties()
		{
			return mBundle;
		}

		String getObjectName()
		{
			return mObjectName;
		}

		String getObjectUrl()
		{
			return mHostFileUrl + "?" + encodeUrl(mBundle);
		}

		@SuppressWarnings("deprecation")
        private static String encodeUrl(Bundle parameters)
		{
			if (parameters == null)
			{
				return "";
			}

			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for (String key: parameters.keySet())
			{
				Object parameter = parameters.get(key);
				if (!(parameter instanceof String))
				{
					continue;
				}

				if (first)
				{
					first = false;
				}
				else
				{
					sb.append("&");
				}
				try
				{
					sb.append(URLEncoder.encode(key, CHARSET_NAME))
							.append("=")
							.append(URLEncoder.encode(parameters.getString(key), CHARSET_NAME));
				}
				catch (UnsupportedEncodingException e)
				{
					Logger.logError(Story.class, "Error enconding URL", e);
				}
			}
			return sb.toString();
		}

	}

}
