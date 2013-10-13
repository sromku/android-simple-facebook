package com.sromku.simple.fb.entities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.model.GraphLocation;
import com.facebook.model.GraphUser;
import com.sromku.simple.fb.Permissions;

/**
 * The facebook user
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/api/user/
 */
public class Profile
{
	private static final String PROPERTY_LOCALE = "locale";
	private static final String PROPERTY_LANGUAGES = "languages";
	private static final String PROPERTY_TIMEZONE = "timezone";
	private static final String PROPERTY_BIO = "bio";
	private static final String PROPERTY_EMAIL = "email";
	private static final String PROPERTY_GENDER = "gender";

	private static final String ID = "id";
	private static final String NAME = "name";

	private final GraphUser mGraphUser;

	private Profile(GraphUser graphUser)
	{
		mGraphUser = graphUser;
	}

	/**
	 * Create new profile based on {@link GraphUser} instance.
	 * 
	 * @param graphUser The {@link GraphUser} instance
	 * @return
	 */
	public static Profile create(GraphUser graphUser)
	{
		return new Profile(graphUser);
	}

	/**
	 * Return the graph user
	 * 
	 * @return
	 */
	public GraphUser getGraphUser()
	{
		return mGraphUser;
	}

	/**
	 * Returns the ID of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the ID of the user
	 */
	public String getId()
	{
		return mGraphUser.getId();
	}

	/**
	 * Returns the name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the name of the user
	 */
	public String getName()
	{
		return mGraphUser.getName();
	}

	/**
	 * Returns the first name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the first name of the user
	 */
	public String getFirstName()
	{
		return mGraphUser.getFirstName();
	}

	/**
	 * Returns the middle name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the middle name of the user
	 */
	public String getMiddleName()
	{
		return mGraphUser.getMiddleName();
	}

	/**
	 * Returns the last name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the last name of the user
	 */
	public String getLastName()
	{
		return mGraphUser.getLastName();
	}

	/**
	 * Returns the gender of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the gender of the user
	 */
	public String getGender()
	{
		String gender = String.valueOf(mGraphUser.getProperty(PROPERTY_GENDER));
		return gender;
	}

	/**
	 * Returns the Facebook URL of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the Facebook URL of the user
	 */
	public String getLink()
	{
		return mGraphUser.getLink();
	}

	/**
	 * Returns the Facebook username of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the Facebook username of the user
	 */
	public String getUsername()
	{
		return mGraphUser.getUsername();
	}

	/**
	 * Returns the birthday of the user. <b>MM/DD/YYYY</b> format <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_BIRTHDAY} <br>
	 * {@link Permissions#FRIENDS_BIRTHDAY}
	 * 
	 * @return the birthday of the user
	 */
	public String getBirthday()
	{
		return mGraphUser.getBirthday();
	}

	/**
	 * Returns the current city of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_LOCATION}
	 * {@link Permissions#FRIENDS_LOCATION}
	 * 
	 * @return the current city of the user
	 */
	public Location getLocation()
	{
		GraphLocation graphLocation = mGraphUser.getLocation();
		if (graphLocation != null)
		{
			String id = String.valueOf(graphLocation.getProperty(ID));
			String name = String.valueOf(graphLocation.getProperty(NAME));
			Location location = new Location();
			location.setId(id);
			location.setName(name);
			return location;
		}

		return null;
	}

	/**
	 * Return the email of the user.<br>
	 * <br>
	 * <b> Permissions:</b> <br>
	 * {@link Permissions#EMAIL}
	 * 
	 * @return the email of the user
	 */
	public String getEmail()
	{
		String email = String.valueOf(mGraphUser.getProperty(PROPERTY_EMAIL));
		return email;
	}

	/**
	 * Return the ISO language code and ISO country code of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the ISO language code and ISO country code of the user
	 */
	public String getLocale()
	{
		String locale = String.valueOf(mGraphUser.getProperty(PROPERTY_LOCALE));
		return locale;
	}

	/**
	 * Return the languages of the user.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_LIKES}
	 * 
	 * @return the languages of the user
	 */
	public List<Language> getLanguages()
	{
		List<Language> languages = new ArrayList<Language>();

		JSONArray jsonArray = (JSONArray)mGraphUser.getProperty(PROPERTY_LANGUAGES);
		if (jsonArray != null)
		{
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = jsonArray.optJSONObject(i);
				int id = jsonObject.optInt(ID);
				String name = jsonObject.optString(NAME);

				Language language = new Language();
				language.setId(id);
				language.setName(name);
				languages.add(language);
			}
		}

		return languages;
	}

	/**
	 * Return the timezone of the user.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * <br>
	 * <br>
	 * <b>Note:</b> <br>
	 * Avaliable only for my profile
	 * 
	 * @return the timezone of the user
	 */
	public int getTimeZone()
	{
		int timeZone = Integer.valueOf(mGraphUser.getProperty(PROPERTY_TIMEZONE).toString());
		return timeZone;
	}

	/**
	 * Return the biography of the user.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_ABOUT_ME}<br>
	 * {@link Permissions#FRIENDS_ABOUT_ME}
	 * 
	 * @return the biography of the user
	 */
	public String getBio()
	{
		String bio = String.valueOf(mGraphUser.getProperty(PROPERTY_BIO));
		return bio;
	}

}
