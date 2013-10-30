package com.sromku.simple.fb.entities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.model.GraphLocation;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.Properties;

/**
 * The facebook user
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/api/user/
 */
public class Profile
{
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
	 * @return The graph user
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
		String gender = String.valueOf(mGraphUser.getProperty(Properties.GENDER));
		return gender;
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
		String locale = String.valueOf(mGraphUser.getProperty(Properties.LOCALE));
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

		JSONArray jsonArray = (JSONArray)mGraphUser.getProperty(Properties.LANGUAGE);
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
	 * The user's age range. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the user's age range
	 */
	public String getAgeRange()
	{
		JSONObject jsonObject = (JSONObject)mGraphUser.getProperty(Properties.AGE_RANGE);
		String min = jsonObject.optString("min");
		String max = jsonObject.optString("max");
		String ageRange = min + max;
		return ageRange;
	}

	/**
	 * An anonymous, but unique identifier for the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the an anonymous, but unique identifier for the user
	 */
	public String getThirdPartyId()
	{
		Object property = mGraphUser.getProperty(Properties.THIRD_PARTY_ID);
		return String.valueOf(property);
	}

	/**
	 * Specifies whether the user has installed the application associated with the app access token that is
	 * used to make the request. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return <code>True</code> if installed, otherwise <code>False</code>
	 */
	public boolean getInstalled()
	{
		Boolean property = (Boolean)mGraphUser.asMap().get(Properties.INSTALLED);
		if (property != null)
		{
			return false;
		}
		return true;
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
		int timeZone = Integer.valueOf(mGraphUser.getProperty(Properties.TIMEZONE).toString());
		return timeZone;
	}

	/**
	 * The last time the user's profile was updated; changes to the languages, link, timezone, verified,
	 * interested_in, favorite_athletes, favorite_teams, and video_upload_limits are not not reflected in this
	 * value.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * <br>
	 * <br>
	 * 
	 * @return string containing an ISO-8601 datetime
	 */
	public String getUpdatedTime()
	{
		String property = String.valueOf(mGraphUser.getProperty(Properties.UPDATED_TIME));
		return property;
	}

	/**
	 * The user's account verification status.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * <br>
	 * <br>
	 * <b>Note:</b> <br>
	 * A user is considered verified if she takes any of the following actions: <li>Registers for mobile</li>
	 * <li>Confirms her account via SMS</li> <li>Enters a valid credit card</li> <br>
	 * <br>
	 * 
	 * @return The user's account verification status
	 */
	public Boolean getVerified()
	{
		Boolean property = (Boolean)mGraphUser.asMap().get(Properties.INSTALLED);
		if (property != null)
		{
			return property;
		}
		return null;
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
		String bio = String.valueOf(mGraphUser.getProperty(Properties.BIO));
		return bio;
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
	 * The user's cover photo <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return The user's cover photo
	 */
	public String getCover()
	{
		JSONObject jsonObject = (JSONObject)mGraphUser.getProperty(Properties.COVER);
		if (jsonObject != null)
		{
			String coverUrl = jsonObject.optString("source");
			return coverUrl;
		}
		return null;
	}

	/**
	 * The user's currency settings <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return The user's currency settings
	 */
	public String getCurrency()
	{
		JSONObject jsonObject = (JSONObject)mGraphUser.getProperty(Properties.CURRENCY);
		if (jsonObject != null)
		{
			String userCurrency = jsonObject.optString("user_currency");
			return userCurrency;
		}
		return null;
	}

	/**
	 * The user's education history <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_EDUCATION_HISTORY}<br>
	 * {@link Permissions#FRIENDS_EDUCATION_HISTORY}
	 * 
	 * @return The user's education history
	 */
	public List<Education> getEducation()
	{
		List<Education> educations = new ArrayList<Education>();
		GraphObjectList<GraphObject> graphObjectList = mGraphUser.getPropertyAsList(Properties.EDUCATION, GraphObject.class);
		for (GraphObject graphObject: graphObjectList)
		{
			Education education = Education.create(graphObject);
			educations.add(education);
		}
		return educations;
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
		String email = String.valueOf(mGraphUser.getProperty(Properties.EMAIL));
		return email;
	}

	/**
	 * The user's hometown <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_HOMETOWN}<br>
	 * {@link Permissions#FRIENDS_HOMETOWN}
	 * 
	 * @return The user's hometown
	 */
	public String getHometown()
	{
		String hometown = String.valueOf(mGraphUser.getProperty(Properties.HOMETOWN));
		return hometown;
	}

	/**
	 * Returns the current city of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_LOCATION}<br>
	 * {@link Permissions#FRIENDS_LOCATION}
	 * 
	 * @return the current city of the user
	 */
	public Location getLocation()
	{
		GraphLocation graphLocation = mGraphUser.getLocation();
		if (graphLocation != null)
		{
			Location location = Location.create(graphLocation);
			return location;
		}

		return null;
	}

	/**
	 * The user's political view <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_RELIGION_POLITICS}<br>
	 * {@link Permissions#FRIENDS_RELIGION_POLITICS}
	 * 
	 * @return The user's political view
	 */
	public String getPolitical()
	{
		String political = String.valueOf(mGraphUser.getProperty(Properties.POLITICAL));
		return political;
	}

	/**
	 * The user's favorite athletes <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_LIKES}<br>
	 * {@link Permissions#FRIENDS_LIKES}
	 * 
	 * @return The user's favorite athletes
	 */
	public List<String> getFavoriteAthletes()
	{
		List<String> athletes = new ArrayList<String>();
		JSONArray jsonArray = (JSONArray)mGraphUser.getProperty(Properties.FAVORITE_ATHLETES);
		if (jsonArray != null)
		{
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = jsonArray.optJSONObject(i);
				if (jsonObject != null)
				{
					String name = jsonObject.optString(NAME);
					athletes.add(name);
				}
			}
		}
		return athletes;
	}

	/**
	 * The user's favorite teams <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_LIKES}<br>
	 * {@link Permissions#FRIENDS_LIKES}
	 * 
	 * @return The user's favorite teams
	 */
	public List<String> getFavoriteTeams()
	{
		List<String> athletes = new ArrayList<String>();
		JSONArray jsonArray = (JSONArray)mGraphUser.getProperty(Properties.FAVORITE_TEAMS);
		if (jsonArray != null)
		{
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = jsonArray.optJSONObject(i);
				if (jsonObject != null)
				{
					String name = jsonObject.optString(NAME);
					athletes.add(name);
				}
			}
		}
		return athletes;
	}

	/**
	 * The user's profile pic <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return The user's profile pic
	 */
	public String getPicture()
	{
		JSONObject result = (JSONObject)mGraphUser.getProperty(Properties.PICTURE);
		JSONObject data = result.optJSONObject("data");
		String url = data.optString("url");
		return url;
	}

	/**
	 * The user's favorite quotes <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_ABOUT_ME}<br>
	 * {@link Permissions#FRIENDS_ABOUT_ME}
	 * 
	 * @return The user's favorite quotes
	 */
	public String getQuotes()
	{
		String quotes = String.valueOf(mGraphUser.getProperty(Properties.QUOTES));
		return quotes;
	}

	/**
	 * The user's relationship status: <br>
	 * <li>Single</li> <li>In a relationship</li> <li>Engaged</li> <li>Married</li> <li>It's complicated</li>
	 * <li>In an open relationship</li> <li>Widowed</li> <li>Separated</li> <li>Divorced</li> <li>In a civil
	 * union</li> <li>In a domestic partnership</li> <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_RELATIONSHIPS}<br>
	 * {@link Permissions#FRIENDS_RELATIONSHIPS}
	 * 
	 * @return The user's relationship status
	 */
	public String getRelationshipStatus()
	{
		String relationshipStatus = String.valueOf(mGraphUser.getProperty(Properties.RELATIONSHIP_STATUS));
		return relationshipStatus;
	}

	/**
	 * The user's religion <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_RELIGION_POLITICS}<br>
	 * {@link Permissions#FRIENDS_RELIGION_POLITICS}
	 * 
	 * @return The user's religion
	 */
	public String getReligion()
	{
		String religion = String.valueOf(mGraphUser.getProperty(Properties.RELIGION));
		return religion;
	}

	/**
	 * The URL of the user's personal website <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_WEBSITE}<br>
	 * {@link Permissions#FRIENDS_WEBSITE}
	 * 
	 * @return The URL of the user's personal website
	 */
	public String getWebsite()
	{
		String website = String.valueOf(mGraphUser.getProperty(Properties.WEBSITE));
		return website;
	}

	/**
	 * The user's work history <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_WORK_HISTORY}<br>
	 * {@link Permissions#FRIENDS_WORK_HISTORY}
	 * 
	 * @return The user's work history
	 */
	public List<Work> getWork()
	{
		List<Work> works = new ArrayList<Work>();
		GraphObjectList<GraphObject> graphObjectList = mGraphUser.getPropertyAsList(Properties.WORK, GraphObject.class);
		for (GraphObject graphObject: graphObjectList)
		{
			Work work = Work.create(graphObject);
			works.add(work);
		}
		return works;
	}
}
