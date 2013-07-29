package com.sromku.simple.fb;

import android.os.Bundle;

/**
 * The feed to be published on the wall
 * 
 * @author Romku
 */
public class Feed
{
	private Bundle mBundle = null;

	private Feed(Builder builder)
	{
		this.mBundle = builder.mBundle;
	}

	Bundle getBundle()
	{
		return mBundle;
	}

	public static class Builder
	{
		Bundle mBundle;

		protected static class Parameters
		{
			public static final String MESSAGE = "message";
			public static final String LINK = "link";
			public static final String PICTURE = "picture";
			public static final String NAME = "name";
			public static final String CAPTION = "caption";
			public static final String DESCRIPTION = "description";
		}

		public Builder()
		{
			mBundle = new Bundle();
		}

		public Builder setName(String name)
		{
			mBundle.putString(Parameters.NAME, name);
			return this;
		}

		public Builder setMessage(String message)
		{
			mBundle.putString(Parameters.MESSAGE, message);
			return this;
		}

		public Builder setLink(String link)
		{
			mBundle.putString(Parameters.LINK, link);
			return this;
		}

		public Builder setPicture(String picture)
		{
			mBundle.putString(Parameters.PICTURE, picture);
			return this;
		}

		public Builder setCaption(String caption)
		{
			mBundle.putString(Parameters.CAPTION, caption);
			return this;
		}

		public Builder setDescription(String description)
		{
			mBundle.putString(Parameters.DESCRIPTION, description);
			return this;
		}

		public Feed build()
		{
			return new Feed(this);
		}

	}

}
