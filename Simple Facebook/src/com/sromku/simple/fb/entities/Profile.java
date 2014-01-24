package com.sromku.simple.fb.entities;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;

import com.facebook.model.GraphLocation;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.Utils;

/**
 * The facebook user
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/user
 */
public class Profile implements User {

    private final GraphUser mGraphUser;

    private Profile(GraphUser graphUser) {
	mGraphUser = graphUser;
    }

    /**
     * Create new profile based on {@link GraphUser} instance.
     * 
     * @param graphUser
     *            The {@link GraphUser} instance
     * @return {@link Profile} of the user
     */
    public static Profile create(GraphUser graphUser) {
	return new Profile(graphUser);
    }

    /**
     * Return the graph user
     * 
     * @return The graph user
     */
    public GraphUser getGraphUser() {
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
    public String getId() {
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
    public String getName() {
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
    public String getFirstName() {
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
    public String getMiddleName() {
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
    public String getLastName() {
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
    public String getGender() {
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
    public String getLocale() {
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
    public List<Language> getLanguages() {
	List<Language> languages = new ArrayList<Language>();

	JSONArray jsonArray = (JSONArray) mGraphUser.getProperty(Properties.LANGUAGE);
	if (jsonArray != null) {
	    for (int i = 0; i < jsonArray.length(); i++) {
		JSONObject jsonObject = jsonArray.optJSONObject(i);
		int id = jsonObject.optInt(Properties.ID);
		String name = jsonObject.optString(Properties.NAME);

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
    public String getLink() {
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
    public String getUsername() {
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
    public AgeRange getAgeRange() {
	JSONObject jsonObject = (JSONObject) mGraphUser.getProperty(Properties.AGE_RANGE);
	String min = jsonObject.optString("min");
	String max = jsonObject.optString("max");
	AgeRange ageRange = new AgeRange(min, max);
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
    public String getThirdPartyId() {
	Object property = mGraphUser.getProperty(Properties.THIRD_PARTY_ID);
	return String.valueOf(property);
    }

    /**
     * Specifies whether the user has installed the application associated with
     * the app access token that is used to make the request. <br>
     * <br>
     * <b> Permissions:</b><br>
     * {@link Permissions#BASIC_INFO}
     * 
     * @return <code>True</code> if installed, otherwise <code>False</code>
     */
    public boolean getInstalled() {
	Boolean property = (Boolean) mGraphUser.asMap().get(Properties.INSTALLED);
	if (property != null) {
	    return property;
	}
	return false;
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
    public int getTimeZone() {
	int timeZone = Integer.valueOf(mGraphUser.getProperty(Properties.TIMEZONE).toString());
	return timeZone;
    }

    /**
     * The last time the user's profile was updated; changes to the languages,
     * link, timezone, verified, interested_in, favorite_athletes,
     * favorite_teams, and video_upload_limits are not not reflected in this
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
    public String getUpdatedTime() {
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
     * A user is considered verified if she takes any of the following actions:
     * <li>Registers for mobile</li> <li>Confirms her account via SMS</li> <li>
     * Enters a valid credit card</li> <br>
     * <br>
     * 
     * @return The user's account verification status
     */
    public Boolean getVerified() {
	Boolean property = (Boolean) mGraphUser.asMap().get(Properties.INSTALLED);
	if (property != null) {
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
    public String getBio() {
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
    public String getBirthday() {
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
    public Photo getCover() {
	GraphObject graphObject = mGraphUser.getPropertyAs(Properties.COVER, GraphObject.class);
	Photo photo = Photo.create(graphObject);
	return photo;
    }

    /**
     * The user's currency settings <br>
     * <br>
     * <b> Permissions:</b><br>
     * {@link Permissions#BASIC_INFO}
     * 
     * @return The user's currency settings
     */
    public String getCurrency() {
	JSONObject jsonObject = (JSONObject) mGraphUser.getProperty(Properties.CURRENCY);
	if (jsonObject != null) {
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
    public List<Education> getEducation() {
	List<Education> educations = new ArrayList<Education>();
	GraphObjectList<GraphObject> graphObjectList = mGraphUser.getPropertyAsList(Properties.EDUCATION, GraphObject.class);
	for (GraphObject graphObject : graphObjectList) {
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
    public String getEmail() {
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
    public String getHometown() {
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
    public Location getLocation() {
	GraphLocation graphLocation = mGraphUser.getLocation();
	if (graphLocation != null) {
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
    public String getPolitical() {
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
    public List<String> getFavoriteAthletes() {
	List<String> athletes = new ArrayList<String>();
	JSONArray jsonArray = (JSONArray) mGraphUser.getProperty(Properties.FAVORITE_ATHLETES);
	if (jsonArray != null) {
	    for (int i = 0; i < jsonArray.length(); i++) {
		JSONObject jsonObject = jsonArray.optJSONObject(i);
		if (jsonObject != null) {
		    String name = jsonObject.optString(Properties.NAME);
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
    public List<String> getFavoriteTeams() {
	List<String> athletes = new ArrayList<String>();
	JSONArray jsonArray = (JSONArray) mGraphUser.getProperty(Properties.FAVORITE_TEAMS);
	if (jsonArray != null) {
	    for (int i = 0; i < jsonArray.length(); i++) {
		JSONObject jsonObject = jsonArray.optJSONObject(i);
		if (jsonObject != null) {
		    String name = jsonObject.optString(Properties.NAME);
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
    public String getPicture() {
	JSONObject result = (JSONObject) mGraphUser.getProperty(Properties.PICTURE);
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
    public String getQuotes() {
	String quotes = String.valueOf(mGraphUser.getProperty(Properties.QUOTES));
	return quotes;
    }

    /**
     * The user's relationship status: <br>
     * <li>Single</li> <li>In a relationship</li> <li>Engaged</li> <li>Married</li>
     * <li>It's complicated</li> <li>In an open relationship</li> <li>Widowed</li>
     * <li>Separated</li> <li>Divorced</li> <li>In a civil union</li> <li>In a
     * domestic partnership</li> <br>
     * <br>
     * <b> Permissions:</b><br>
     * {@link Permissions#USER_RELATIONSHIPS}<br>
     * {@link Permissions#FRIENDS_RELATIONSHIPS}
     * 
     * @return The user's relationship status
     */
    public String getRelationshipStatus() {
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
    public String getReligion() {
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
    public String getWebsite() {
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
    public List<Work> getWork() {
	List<Work> works = new ArrayList<Work>();
	GraphObjectList<GraphObject> graphObjectList = mGraphUser.getPropertyAsList(Properties.WORK, GraphObject.class);
	for (GraphObject graphObject : graphObjectList) {
	    Work work = Work.create(graphObject);
	    works.add(work);
	}
	return works;
    }

    public static class Properties {
	private final Bundle mBundle;

	private Properties(Builder builder) {
	    mBundle = new Bundle();
	    Iterator<String> iterator = builder.properties.iterator();
	    String fields = Utils.join(iterator, ',');
	    mBundle.putString("fields", fields);
	}

	public Bundle getBundle() {
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
	 * Specifies whether the user has installed the application associated
	 * with the app access token that is used to make the request<br>
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
	 * The last time the user's profile was updated; changes to the
	 * languages, link, timezone, verified, interested_in,
	 * favorite_athletes, favorite_teams, and video_upload_limits are not
	 * not reflected in this value<br>
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
	 * The mobile payment price-points available for that user, for use when
	 * processing payments using Facebook Credits<br>
	 * <br>
	 * 
	 * <b>Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 */
	public static final String PAYMENT_PRICEPOINTS = "payment_pricepoints";

	/**
	 * <b>Description:</b><br>
	 * The mobile payment price-points available for that user, for use when
	 * processing payments using Local Currency<br>
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
	 * The user's relationship status: Single, In a relationship, Engaged,
	 * Married, It's complicated, In an open relationship, Widowed,
	 * Separated, Divorced, In a civil union, In a domestic partnership<br>
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
	 * The size of the video file and the length of the video that a user
	 * can upload<br>
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

	public static class Builder {
	    Set<String> properties;

	    public Builder() {
		properties = new HashSet<String>();
	    }

	    /**
	     * Add property you need
	     * 
	     * @param property
	     *            The property of the user profile<br>
	     *            For example: {@link Properties#FIRST_NAME}
	     * @return {@link Builder}
	     */
	    public Builder add(String property) {
		properties.add(property);
		return this;
	    }

	    /**
	     * Add property you need
	     * 
	     * @param property
	     *            The property of the user profile<br>
	     *            For example: {@link Properties#PICTURE}
	     * @return {@link Builder}
	     */

	    /**
	     * Add property and attribute you need
	     * 
	     * @param property
	     *            The property of the user profile<br>
	     *            For example: {@link Properties#PICTURE}
	     * @param attributes
	     *            For example: picture can have type,width and height<br>
	     * 
	     * 
	     * @return {@link Builder}
	     */
	    public Builder add(String property, Attributes attributes) {
		Map<String, String> map = attributes.getAttributes();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(property);
		stringBuilder.append('.');
		stringBuilder.append(Utils.join(map, '.', '(', ')'));

		properties.add(stringBuilder.toString());
		return this;
	    }

	    public Properties build() {
		return new Properties(this);
	    }

	}
    }

}
