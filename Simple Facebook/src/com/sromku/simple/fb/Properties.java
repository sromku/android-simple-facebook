package com.sromku.simple.fb;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;

import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.Utils;

public class Properties
{
	private final Bundle mBundle;

	private Properties(Builder builder)
	{
		mBundle = new Bundle();
		Iterator<String> iterator = builder.properties.iterator();
		String fields = Utils.join(iterator, ',');
		mBundle.putString("fields", fields);
	}

	Bundle getBundle()
	{
		return mBundle;
	}

	/**
	 * <b>Description:</b><br>
	 * The user's Facebook ID<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String ID = "id";

	/**
	 * <b>Description:</b><br>
	 * The user's full name<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String NAME = "name";

	/**
	 * <b>Description:</b><br>
	 * The user's first name<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String FIRST_NAME = "first_name";

	/**
	 * <b>Description:</b><br>
	 * The user's middle name<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String MIDDLE_NAME = "middle_name";

	/**
	 * <b>Description:</b><br>
	 * The user's last name<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String LAST_NAME = "last_name";

	/**
	 * <b>Description:</b><br>
	 * The user's gender: female or male<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String GENDER = "gender";

	/**
	 * <b>Description:</b><br>
	 * The user's locale<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String LOCALE = "locale";

	/**
	 * <b>Description:</b><br>
	 * The user's languages<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_LIKES}
	 * 
	 */
	public static final String LANGUAGE = "languages";

	/**
	 * <b>Description:</b><br>
	 * The URL of the profile for the user on Facebook<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String LINK = "link";

	/**
	 * <b>Description:</b><br>
	 * The user's Facebook username<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String USER_NAME = "username";

	/**
	 * <b>Description:</b><br>
	 * The user's age range<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String AGE_RANGE = "age_range";

	/**
	 * <b>Description:</b><br>
	 * An anonymous, but unique identifier for the user<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String THIRD_PARTY_ID = "third_party_id";

	/**
	 * <b>Description:</b><br>
	 * Specifies whether the user has installed the application associated with the app access token that is
	 * used to make the request<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String INSTALLED = "installed";

	/**
	 * <b>Description:</b><br>
	 * The user's timezone offset from UTC<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String TIMEZONE = "timezone";

	/**
	 * <b>Description:</b><br>
	 * The last time the user's profile was updated; changes to the languages, link, timezone, verified,
	 * interested_in, favorite_athletes, favorite_teams, and video_upload_limits are not not reflected in this
	 * value<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String UPDATED_TIME = "updated_time";

	/**
	 * <b>Description:</b><br>
	 * The user's account verification status, either true or false<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String VERIFIED = "verified";

	/**
	 * <b>Description:</b><br>
	 * The user's biography<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_ABOUT_ME}<br>
	 * {@link Permissions#FRIENDS_ABOUT_ME}
	 * 
	 */
	public static final String BIO = "bio";

	/**
	 * <b>Description:</b><br>
	 * The user's birthday<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_BIRTHDAY}<br>
	 * {@link Permissions#FRIENDS_BIRTHDAY}
	 * 
	 */
	public static final String BIRTHDAY = "birthday";

	/**
	 * <b>Description:</b><br>
	 * The user's cover photo<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String COVER = "cover";

	/**
	 * <b>Description:</b><br>
	 * The user's currency settings <br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String CURRENCY = "currency";

	/**
	 * <b>Description:</b><br>
	 * A list of the user's devices beyond desktop<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String DEVICES = "devices";

	/**
	 * <b>Description:</b><br>
	 * A list of the user's education history<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_EDUCATION_HISTORY}<br>
	 * {@link Permissions#FRIENDS_EDUCATION_HISTORY}
	 * 
	 */
	public static final String EDUCATION = "education";

	/**
	 * <b>Description:</b><br>
	 * The proxied or contact email address granted by the user<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#EMAIL}
	 * 
	 */
	public static final String EMAIL = "email";

	/**
	 * <b>Description:</b><br>
	 * The user's hometown<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_HOMETOWN}<br>
	 * {@link Permissions#FRIENDS_HOMETOWN}
	 * 
	 */
	public static final String HOMETOWN = "hometown";

	/**
	 * <b>Description:</b><br>
	 * The genders the user is interested in<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_RELATIONSHIP_DETAILS}<br>
	 * {@link Permissions#FRIENDS_RELATIONSHIP_DETAILS}
	 * 
	 */
	public static final String INTERESTED_IN = "interested_in";

	/**
	 * <b>Description:</b><br>
	 * The user's current city<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_LOCATION}<br>
	 * {@link Permissions#FRIENDS_LOCATION}
	 * 
	 */
	public static final String LOCATION = "location";

	/**
	 * <b>Description:</b><br>
	 * The user's political view<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_RELIGION_POLITICS}<br>
	 * {@link Permissions#FRIENDS_RELIGION_POLITICS}
	 * 
	 */
	public static final String POLITICAL = "political";

	/**
	 * <b>Description:</b><br>
	 * The mobile payment price-points available for that user, for use when processing payments using
	 * Facebook Credits<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String PAYMENT_PRICEPOINTS = "payment_pricepoints";

	/**
	 * <b>Description:</b><br>
	 * The mobile payment price-points available for that user, for use when processing payments using Local
	 * Currency<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String PAYMENT_MOBILE_PRICEPOINTS = "payment_mobile_pricepoints";

	/**
	 * <b>Description:</b><br>
	 * The user's favorite athletes<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_LIKES}<br>
	 * {@link Permissions#FRIENDS_LIKES}
	 * 
	 */
	public static final String FAVORITE_ATHLETES = "favorite_athletes";

	/**
	 * <b>Description:</b><br>
	 * The user's favorite teams<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_LIKES}<br>
	 * {@link Permissions#FRIENDS_LIKES}
	 * 
	 */
	public static final String FAVORITE_TEAMS = "favorite_teams";

	/**
	 * <b>Description:</b><br>
	 * The user's profile pic<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String PICTURE = "picture";

	/**
	 * <b>Description:</b><br>
	 * The user's favorite quotes<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_ABOUT_ME}<br>
	 * {@link Permissions#FRIENDS_ABOUT_ME}
	 * 
	 */
	public static final String QUOTES = "quotes";

	/**
	 * <b>Description:</b><br>
	 * The user's relationship status: Single, In a relationship, Engaged, Married, It's complicated, In an
	 * open relationship, Widowed, Separated, Divorced, In a civil union, In a domestic partnership<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_RELATIONSHIPS}<br>
	 * {@link Permissions#FRIENDS_RELATIONSHIPS}
	 * 
	 */
	public static final String RELATIONSHIP_STATUS = "relationship_status";

	/**
	 * <b>Description:</b><br>
	 * The user's religion<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_RELIGION_POLITICS}<br>
	 * {@link Permissions#FRIENDS_RELIGION_POLITICS}
	 * 
	 */
	public static final String RELIGION = "religion";

	/**
	 * <b>Description:</b><br>
	 * Information about security settings enabled on the user's account<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String SECURITY_SETTINGS = "security_settings";

	/**
	 * <b>Description:</b><br>
	 * The user's significant other<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_RELATIONSHIPS}<br>
	 * {@link Permissions#FRIENDS_RELATIONSHIPS}
	 * 
	 */
	public static final String SINGNIFICANT_OTHER = "significant_other";

	/**
	 * <b>Description:</b><br>
	 * The size of the video file and the length of the video that a user can upload<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String VIDEO_UPLOAD_LIMITS = "video_upload_limits";

	/**
	 * <b>Description:</b><br>
	 * The URL of the user's personal website<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_WEBSITE}<br>
	 * {@link Permissions#FRIENDS_WEBSITE}
	 * 
	 */
	public static final String WEBSITE = "website";

	/**
	 * <b>Description:</b><br>
	 * A list of the user's work history<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#USER_WORK_HISTORY}<br>
	 * {@link Permissions#FRIENDS_WORK_HISTORY}
	 * 
	 */
	public static final String WORK = "work";

	public static class Builder
	{
		Set<String> properties;

		public Builder()
		{
			properties = new HashSet<String>();
		}

		/**
		 * Add property you need
		 * 
		 * @param property The property of the user profile<br>
		 *            For example: {@link Properties#FIRST_NAME}
		 * @return {@link Builder}
		 */
		public Builder add(String property)
		{
			properties.add(property);
			return this;
		}

		/**
		 * Add property you need
		 * 
		 * @param property The property of the user profile<br>
		 *            For example: {@link Properties#PICTURE}
		 * @return {@link Builder}
		 */

		/**
		 * Add property and attribute you need
		 * 
		 * @param property The property of the user profile<br>
		 *            For example: {@link Properties#PICTURE}
		 * @param attributes For example: picture can have type,width and height<br>
		 * 
		 * 
		 * @return {@link Builder}
		 */
		public Builder add(String property, Attributes attributes)
		{
			Map<String, String> map = attributes.getAttributes();

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(property);
			stringBuilder.append('.');
			stringBuilder.append(Utils.join(map, '.', '(', ')'));

			properties.add(stringBuilder.toString());
			return this;
		}

		public Properties build()
		{
			return new Properties(this);
		}

	}
}
