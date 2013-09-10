package com.sromku.simple.fb.entities;

import java.net.URLEncoder;

import android.os.Bundle;

/**
 * Open graph story
 * 
 * @author sromku
 * @see http://ogp.me/
 */
public class Story
{
	private final ActionOpenGraph _action;
	private final ObjectOpenGraph _object;

	private Story(ActionOpenGraph action, ObjectOpenGraph object)
	{
		_action = action;
		_object = object;

		// Connect between object and action
		_action.putProperty(_object.getObjectName(), _object.getObjectUrl());
	}

	public String getGraphPath(String appNamespace)
	{
		return "me" + "/" + appNamespace + ":" + _action.getActionName();
	}

	public Bundle getActionBundle()
	{
		return _action.getProperties();
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
			if (str == null || str.length() == 0)
			{
				return true;
			}
			return false;
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
				sb.append(URLEncoder.encode(key) + "=" + URLEncoder.encode(parameters.getString(key)));
			}
			return sb.toString();
		}

	}

}
