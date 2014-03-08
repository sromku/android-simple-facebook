package com.sromku.simple.fb.entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;

import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.Converter;

/**
 * The facebook user
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/user
 */
public class Profile implements User {

	private final GraphObject mGraphObject;

	private String mId;
	private String mName;
	private String mFirstName;
	private String mMiddleName;
	private String mLastName;
	private String mGender;
	private String mLocale;
	private List<Language> mLanguages;
	private String mLink;
	private String mUsername;
	private AgeRange mAgeRange;
	private String mThirdPartyId;
	private Boolean mIsInstalled;
	private Integer mTimeZone;
	private String mUpdatedTime;
	private Boolean mVerified;
	private String mBio;
	private String mBirthday;
	private Photo mCover;
	private String mCurrency;
	private List<Education> mEducation;
	private String mEmail;
	private String mHometown;
	private Location mLocation;
	private String mPolitical;
	private List<String> mFavoriteAthletess;
	private List<String> mFavoriteTeams;
	private String mPicture;
	private String mQuotes;
	private String mRelationshipStatus;
	private String mReligion;
	private String mWebsite;
	private List<Work> mWorks;

	private Profile(GraphObject graphObject) {
		mGraphObject = graphObject;

		// id
		mId =Utils.getPropertyString(mGraphObject, Properties.ID);

		// name
		mName = Utils.getPropertyString(mGraphObject, Properties.NAME);

		// first name
		mFirstName = Utils.getPropertyString(mGraphObject, Properties.FIRST_NAME);

		// middle name
		mMiddleName = Utils.getPropertyString(mGraphObject, Properties.MIDDLE_NAME);

		// last name
		mLastName = Utils.getPropertyString(mGraphObject, Properties.LAST_NAME);

		// gender
		mGender = Utils.getPropertyString(mGraphObject, Properties.GENDER);

		// locale
		mLocale = Utils.getPropertyString(mGraphObject, Properties.LOCALE);

		// languages
		mLanguages = Utils.createList(mGraphObject, Properties.LANGUAGE, new Converter<Language>() {
			@Override
			public Language convert(GraphObject graphObject) {
				Language language = new Language();
				language.setId(Utils.getPropertyString(graphObject, "id"));
				language.setName(Utils.getPropertyString(graphObject, "name"));
				return language;
			}
		});

		// link
		mLink = Utils.getPropertyString(mGraphObject, Properties.LINK);

		// username
		mUsername = Utils.getPropertyString(mGraphObject, Properties.USER_NAME);

		// age range
		GraphObject ageRangeGraphObject = Utils.getPropertyGraphObject(mGraphObject, Properties.AGE_RANGE);
		if (ageRangeGraphObject != null) {
			mAgeRange = new AgeRange(Utils.getPropertyString(ageRangeGraphObject, "min"), Utils.getPropertyString(ageRangeGraphObject, "max"));
		}

		// third party id
		mThirdPartyId = Utils.getPropertyString(mGraphObject, Properties.THIRD_PARTY_ID);

		// installed
		mIsInstalled = Utils.getPropertyBoolean(mGraphObject, Properties.INSTALLED);

		// time zone
		mTimeZone = Utils.getPropertyInteger(mGraphObject, Properties.TIMEZONE);

		// updated time
		mUpdatedTime = Utils.getPropertyString(mGraphObject, Properties.UPDATED_TIME);

		// verified
		mVerified = Utils.getPropertyBoolean(mGraphObject, Properties.INSTALLED);

		// bio
		mBio = Utils.getPropertyString(mGraphObject, Properties.BIO);

		// birthday
		mBirthday = Utils.getPropertyString(mGraphObject, Properties.BIRTHDAY);

		// cover
		mCover = Photo.create(Utils.getPropertyGraphObject(mGraphObject, Properties.COVER));

		// currency
		mCurrency = Utils.getPropertyInsideProperty(mGraphObject, Properties.CURRENCY, "user_currency");

		// education
		mEducation = Utils.createList(mGraphObject, Properties.EDUCATION, new Converter<Education>() {
			@Override
			public Education convert(GraphObject graphObject) {
				return Education.create(graphObject);
			}
		});

		// email
		mEmail = Utils.getPropertyString(mGraphObject, Properties.EMAIL);

		// hometown
		mHometown = Utils.getPropertyString(mGraphObject, Properties.HOMETOWN);

		// location
		mLocation = Location.create(Utils.getPropertyGraphObject(mGraphObject, Properties.LOCATION));

		// political
		mPolitical = Utils.getPropertyString(mGraphObject, Properties.POLITICAL);

		// favorite athletes
		mFavoriteAthletess = Utils.createList(mGraphObject, Properties.FAVORITE_ATHLETES, new Converter<String>() {
			@Override
			public String convert(GraphObject graphObject) {
				return Utils.getPropertyString(graphObject, Properties.NAME);
			}
		});

		// favorite teams
		mFavoriteTeams = Utils.createList(mGraphObject, Properties.FAVORITE_TEAMS, new Converter<String>() {
			@Override
			public String convert(GraphObject graphObject) {
				return Utils.getPropertyString(graphObject, Properties.NAME);
			}
		});

		// picture
		GraphObject data = Utils.getPropertyGraphObject(mGraphObject, Properties.PICTURE);
		mPicture = Utils.getPropertyInsideProperty(data, "data", "url");

		// quotes
		mQuotes = Utils.getPropertyString(mGraphObject, Properties.QUOTES);

		// relationship status
		mRelationshipStatus = Utils.getPropertyString(mGraphObject, Properties.RELATIONSHIP_STATUS);

		// religion
		mReligion = Utils.getPropertyString(mGraphObject, Properties.RELIGION);

		// website
		mWebsite = Utils.getPropertyString(mGraphObject, Properties.WEBSITE);

		// work
		mWorks = Utils.createList(mGraphObject, Properties.WORK, new Converter<Work>() {
			@Override
			public Work convert(GraphObject graphObject) {
				return Work.create(graphObject);
			}
		});
	}

	/**
	 * Create new profile based on {@link GraphUser} instance.
	 * 
	 * @param graphObject
	 *            The {@link GraphObject} instance
	 * @return {@link Profile} of the user
	 */
	public static Profile create(GraphObject graphObject) {
		return new Profile(graphObject);
	}

	/**
	 * Return the graph object
	 * 
	 * @return The graph object
	 */
	public GraphObject getGraphObject() {
		return mGraphObject;
	}

	/**
	 * Returns the ID of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the ID of the user
	 */
	public String getId() {
		return mId;
	}

	/**
	 * Returns the name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the name of the user
	 */
	public String getName() {
		return mName;
	}

	/**
	 * Returns the first name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the first name of the user
	 */
	public String getFirstName() {
		return mFirstName;
	}

	/**
	 * Returns the middle name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the middle name of the user
	 */
	public String getMiddleName() {
		return mMiddleName;
	}

	/**
	 * Returns the last name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the last name of the user
	 */
	public String getLastName() {
		return mLastName;
	}

	/**
	 * Returns the gender of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the gender of the user
	 */
	public String getGender() {
		return mGender;
	}

	/**
	 * Return the ISO language code and ISO country code of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the ISO language code and ISO country code of the user
	 */
	public String getLocale() {
		return mLocale;
	}

	/**
	 * Return the languages of the user.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_LIKES}
	 * 
	 * @return the languages of the user
	 */
	public List<Language> getLanguages() {
		return mLanguages;
	}

	/**
	 * Returns the Facebook URL of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the Facebook URL of the user
	 */
	public String getLink() {
		return mLink;
	}

	/**
	 * Returns the Facebook username of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the Facebook username of the user
	 */
	public String getUsername() {
		return mUsername;
	}

	/**
	 * The user's age range. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the user's age range
	 */
	public AgeRange getAgeRange() {
		return mAgeRange;
	}

	/**
	 * An anonymous, but unique identifier for the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return the an anonymous, but unique identifier for the user
	 */
	public String getThirdPartyId() {
		return mThirdPartyId;
	}

	/**
	 * Specifies whether the user has installed the application associated with
	 * the app access token that is used to make the request. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return <code>True</code> if installed, otherwise <code>False</code>
	 */
	public boolean getInstalled() {
		return mIsInstalled;
	}

	/**
	 * Return the timezone of the user.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * <br>
	 * <br>
	 * <b>Note:</b> <br>
	 * Avaliable only for my profile
	 * 
	 * @return the timezone of the user
	 */
	public int getTimeZone() {
		return mTimeZone;
	}

	/**
	 * The last time the user's profile was updated; changes to the languages,
	 * link, timezone, verified, interested_in, favorite_athletes,
	 * favorite_teams, and video_upload_limits are not not reflected in this
	 * value.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * <br>
	 * <br>
	 * 
	 * @return string containing an ISO-8601 datetime
	 */
	public String getUpdatedTime() {
		return mUpdatedTime;
	}

	/**
	 * The user's account verification status.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
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
		return mVerified;
	}

	/**
	 * Return the biography of the user.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_ABOUT_ME}<br>
	 * {@link Permission#FRIENDS_ABOUT_ME}
	 * 
	 * @return the biography of the user
	 */
	public String getBio() {
		return mBio;
	}

	/**
	 * Returns the birthday of the user. <b>MM/DD/YYYY</b> format <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_BIRTHDAY} <br>
	 * {@link Permission#FRIENDS_BIRTHDAY}
	 * 
	 * @return the birthday of the user
	 */
	public String getBirthday() {
		return mBirthday;
	}

	/**
	 * The user's cover photo <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return The user's cover photo
	 */
	public Photo getCover() {
		return mCover;
	}

	/**
	 * The user's currency settings <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return The user's currency settings
	 */
	public String getCurrency() {
		return mCurrency;
	}

	/**
	 * The user's education history <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_EDUCATION_HISTORY}<br>
	 * {@link Permission#FRIENDS_EDUCATION_HISTORY}
	 * 
	 * @return The user's education history
	 */
	public List<Education> getEducation() {
		return mEducation;
	}

	/**
	 * Return the email of the user.<br>
	 * <br>
	 * <b> Permissions:</b> <br>
	 * {@link Permission#EMAIL}
	 * 
	 * @return the email of the user
	 */
	public String getEmail() {
		return mEmail;
	}

	/**
	 * The user's hometown <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_HOMETOWN}<br>
	 * {@link Permission#FRIENDS_HOMETOWN}
	 * 
	 * @return The user's hometown
	 */
	public String getHometown() {
		return mHometown;
	}

	/**
	 * Returns the current city of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_LOCATION}<br>
	 * {@link Permission#FRIENDS_LOCATION}
	 * 
	 * @return the current city of the user
	 */
	public Location getLocation() {
		return mLocation;
	}

	/**
	 * The user's political view <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_RELIGION_POLITICS}<br>
	 * {@link Permission#FRIENDS_RELIGION_POLITICS}
	 * 
	 * @return The user's political view
	 */
	public String getPolitical() {
		return mPolitical;
	}

	/**
	 * The user's favorite athletes <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_LIKES}<br>
	 * {@link Permission#FRIENDS_LIKES}
	 * 
	 * @return The user's favorite athletes
	 */
	public List<String> getFavoriteAthletes() {
		return mFavoriteAthletess;
	}

	/**
	 * The user's favorite teams <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_LIKES}<br>
	 * {@link Permission#FRIENDS_LIKES}
	 * 
	 * @return The user's favorite teams
	 */
	public List<String> getFavoriteTeams() {
		return mFavoriteTeams;
	}

	/**
	 * The user's profile pic <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#BASIC_INFO}
	 * 
	 * @return The user's profile pic
	 */
	public String getPicture() {
		return mPicture;
	}

	/**
	 * The user's favorite quotes <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_ABOUT_ME}<br>
	 * {@link Permission#FRIENDS_ABOUT_ME}
	 * 
	 * @return The user's favorite quotes
	 */
	public String getQuotes() {
		return mQuotes;
	}

	/**
	 * The user's relationship status: <br>
	 * <li>Single</li> <li>In a relationship</li> <li>Engaged</li> <li>Married</li>
	 * <li>It's complicated</li> <li>In an open relationship</li> <li>Widowed</li>
	 * <li>Separated</li> <li>Divorced</li> <li>In a civil union</li> <li>In a
	 * domestic partnership</li> <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_RELATIONSHIPS}<br>
	 * {@link Permission#FRIENDS_RELATIONSHIPS}
	 * 
	 * @return The user's relationship status
	 */
	public String getRelationshipStatus() {
		return mRelationshipStatus;
	}

	/**
	 * The user's religion <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_RELIGION_POLITICS}<br>
	 * {@link Permission#FRIENDS_RELIGION_POLITICS}
	 * 
	 * @return The user's religion
	 */
	public String getReligion() {
		return mReligion;
	}

	/**
	 * The URL of the user's personal website <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_WEBSITE}<br>
	 * {@link Permission#FRIENDS_WEBSITE}
	 * 
	 * @return The URL of the user's personal website
	 */
	public String getWebsite() {
		return mWebsite;
	}

	/**
	 * The user's work history <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permission#USER_WORK_HISTORY}<br>
	 * {@link Permission#FRIENDS_WORK_HISTORY}
	 * 
	 * @return The user's work history
	 */
	public List<Work> getWork() {
		return mWorks;
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
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String ID = "id";

		/**
		 * <b>Description:</b><br>
		 * The user's full name<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String NAME = "name";

		/**
		 * <b>Description:</b><br>
		 * The user's first name<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String FIRST_NAME = "first_name";

		/**
		 * <b>Description:</b><br>
		 * The user's middle name<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String MIDDLE_NAME = "middle_name";

		/**
		 * <b>Description:</b><br>
		 * The user's last name<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String LAST_NAME = "last_name";

		/**
		 * <b>Description:</b><br>
		 * The user's gender: female or male<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String GENDER = "gender";

		/**
		 * <b>Description:</b><br>
		 * The user's locale<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String LOCALE = "locale";

		/**
		 * <b>Description:</b><br>
		 * The user's languages<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_LIKES}
		 * 
		 */
		public static final String LANGUAGE = "languages";

		/**
		 * <b>Description:</b><br>
		 * The URL of the profile for the user on Facebook<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String LINK = "link";

		/**
		 * <b>Description:</b><br>
		 * The user's Facebook username<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String USER_NAME = "username";

		/**
		 * <b>Description:</b><br>
		 * The user's age range<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String AGE_RANGE = "age_range";

		/**
		 * <b>Description:</b><br>
		 * An anonymous, but unique identifier for the user<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
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
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String INSTALLED = "installed";

		/**
		 * <b>Description:</b><br>
		 * The user's timezone offset from UTC<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
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
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String UPDATED_TIME = "updated_time";

		/**
		 * <b>Description:</b><br>
		 * The user's account verification status, either true or false<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String VERIFIED = "verified";

		/**
		 * <b>Description:</b><br>
		 * The user's biography<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_ABOUT_ME}<br>
		 * {@link Permission#FRIENDS_ABOUT_ME}
		 * 
		 */
		public static final String BIO = "bio";

		/**
		 * <b>Description:</b><br>
		 * The user's birthday<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_BIRTHDAY}<br>
		 * {@link Permission#FRIENDS_BIRTHDAY}
		 * 
		 */
		public static final String BIRTHDAY = "birthday";

		/**
		 * <b>Description:</b><br>
		 * The user's cover photo<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String COVER = "cover";

		/**
		 * <b>Description:</b><br>
		 * The user's currency settings <br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String CURRENCY = "currency";

		/**
		 * <b>Description:</b><br>
		 * A list of the user's devices beyond desktop<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String DEVICES = "devices";

		/**
		 * <b>Description:</b><br>
		 * A list of the user's education history<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_EDUCATION_HISTORY}<br>
		 * {@link Permission#FRIENDS_EDUCATION_HISTORY}
		 * 
		 */
		public static final String EDUCATION = "education";

		/**
		 * <b>Description:</b><br>
		 * The email address granted by the user<br>
		 * <br>
		 * 
		 * <b>Note:</b> There is no way for apps to obtain email addresses for a
		 * user's friends.<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#EMAIL}
		 */
		public static final String EMAIL = "email";

		/**
		 * <b>Description:</b><br>
		 * The user's hometown<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_HOMETOWN}<br>
		 * {@link Permission#FRIENDS_HOMETOWN}
		 * 
		 */
		public static final String HOMETOWN = "hometown";

		/**
		 * <b>Description:</b><br>
		 * The genders the user is interested in<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_RELATIONSHIP_DETAILS}<br>
		 * {@link Permission#FRIENDS_RELATIONSHIP_DETAILS}
		 * 
		 */
		public static final String INTERESTED_IN = "interested_in";

		/**
		 * <b>Description:</b><br>
		 * The user's current city<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_LOCATION}<br>
		 * {@link Permission#FRIENDS_LOCATION}
		 * 
		 */
		public static final String LOCATION = "location";

		/**
		 * <b>Description:</b><br>
		 * The user's political view<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_RELIGION_POLITICS}<br>
		 * {@link Permission#FRIENDS_RELIGION_POLITICS}
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
		 * {@link Permission#BASIC_INFO}
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
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String PAYMENT_MOBILE_PRICEPOINTS = "payment_mobile_pricepoints";

		/**
		 * <b>Description:</b><br>
		 * The user's favorite athletes<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_LIKES}<br>
		 * {@link Permission#FRIENDS_LIKES}
		 * 
		 */
		public static final String FAVORITE_ATHLETES = "favorite_athletes";

		/**
		 * <b>Description:</b><br>
		 * The user's favorite teams<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_LIKES}<br>
		 * {@link Permission#FRIENDS_LIKES}
		 * 
		 */
		public static final String FAVORITE_TEAMS = "favorite_teams";

		/**
		 * <b>Description:</b><br>
		 * The user's profile pic<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String PICTURE = "picture";

		/**
		 * <b>Description:</b><br>
		 * The user's favorite quotes<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_ABOUT_ME}<br>
		 * {@link Permission#FRIENDS_ABOUT_ME}
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
		 * {@link Permission#USER_RELATIONSHIPS}<br>
		 * {@link Permission#FRIENDS_RELATIONSHIPS}
		 * 
		 */
		public static final String RELATIONSHIP_STATUS = "relationship_status";

		/**
		 * <b>Description:</b><br>
		 * The user's religion<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_RELIGION_POLITICS}<br>
		 * {@link Permission#FRIENDS_RELIGION_POLITICS}
		 * 
		 */
		public static final String RELIGION = "religion";

		/**
		 * <b>Description:</b><br>
		 * Information about security settings enabled on the user's account<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String SECURITY_SETTINGS = "security_settings";

		/**
		 * <b>Description:</b><br>
		 * The user's significant other<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_RELATIONSHIPS}<br>
		 * {@link Permission#FRIENDS_RELATIONSHIPS}
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
		 * {@link Permission#BASIC_INFO}
		 * 
		 */
		public static final String VIDEO_UPLOAD_LIMITS = "video_upload_limits";

		/**
		 * <b>Description:</b><br>
		 * The URL of the user's personal website<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_WEBSITE}<br>
		 * {@link Permission#FRIENDS_WEBSITE}
		 * 
		 */
		public static final String WEBSITE = "website";

		/**
		 * <b>Description:</b><br>
		 * A list of the user's work history<br>
		 * <br>
		 * 
		 * <b>Permissions:</b><br>
		 * {@link Permission#USER_WORK_HISTORY}<br>
		 * {@link Permission#FRIENDS_WORK_HISTORY}
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
