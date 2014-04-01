package com.sromku.simple.fb.entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.os.Bundle;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/page
 */
public class Page {

	private final GraphObject mGraphObject;
	private String mId;
	private String mAbout;
	private String mAttire;
	private String mBandMembers;
	private String mBirthday;
	private String mBookingAgent;
	private Boolean mCanPost;
	private String mCategory;
	private Integer mNumCheckins;
	private String mCompanyOverview;
	private String mCover;
	private String mCurrentLocation;
	private String mDescription;
	private String mDirectedBy;
	private String mFounded;
	private String mGeneralInfo;
	private String mGeneralManager;
	private String mHometown;
	// private List<String> mHours;
	private Boolean mIsPermanetlyClosed;
	private Boolean mIsPublished;
	private Boolean mIsUnclaimed;
	private Integer mLikes;
	private String mLink;
	private Location mLocation;
	private String mMission;
	private String mName;
	private Parking mParking;
	private String mPicture;
	private String mPhone;
	private String mPressContact;
	private String mPriceRange;
	private String mProduct;
	private RestaurantService mRestaurantService;
	private RestaurantSpecialties mRestaurantSpecialties;
	private Integer mTalkingAboutCount;
	private String mUsername;
	private String mWebsite;
	private Integer mWereHereCount;

	private Page(GraphObject graphObject) {
		mGraphObject = graphObject;

		// id
		mId = Utils.getPropertyString(graphObject, Properties.ID);

		// about
		mAbout = Utils.getPropertyString(graphObject, Properties.ABOUT);

		// attire
		mAttire = Utils.getPropertyString(graphObject, Properties.ATTIRE);

		// band member
		mBandMembers = Utils.getPropertyString(graphObject, Properties.BAND_MEMBERS);

		// birthday
		mBirthday = Utils.getPropertyString(graphObject, Properties.BIRTHDAY);

		// booking agent
		mBookingAgent = Utils.getPropertyString(graphObject, Properties.BOOKING_AGENT);

		// can post
		mCanPost = Utils.getPropertyBoolean(graphObject, Properties.CAN_POST);

		// category
		mCategory = Utils.getPropertyString(graphObject, Properties.CATEGORY);

		// num checkins
		mNumCheckins = Utils.getPropertyInteger(graphObject, Properties.CHECKINS);

		// company overview
		mCompanyOverview = Utils.getPropertyString(graphObject, Properties.COMPANY_OVERVIEW);

		// cover
		mCover = Utils.getPropertyInsideProperty(graphObject, Properties.COVER, "source");

		// current location
		mCurrentLocation = Utils.getPropertyString(graphObject, Properties.CURRENT_LOCATION);

		// description
		mDescription = Utils.getPropertyString(graphObject, Properties.DESCRIPTION);

		// directed by
		mDirectedBy = Utils.getPropertyString(graphObject, Properties.DIRECTED_BY);

		// founded
		mFounded = Utils.getPropertyString(graphObject, Properties.FOUNDED);

		// general info
		mGeneralInfo = Utils.getPropertyString(graphObject, Properties.GENERAL_INFO);

		// general manager
		mGeneralManager = Utils.getPropertyString(graphObject, Properties.GENERAL_MANAGER);

		// hometown
		mHometown = Utils.getPropertyString(graphObject, Properties.HOMETOWN);

		// hours

		// is permanently closed
		mIsPermanetlyClosed = Utils.getPropertyBoolean(graphObject, Properties.IS_PERMANENTLY_CLOSED);

		// is published
		mIsPublished = Utils.getPropertyBoolean(graphObject, Properties.IS_PUBLISHED);

		// is unclaimed
		mIsUnclaimed = Utils.getPropertyBoolean(graphObject, Properties.IS_UNCLAIMED);

		// likes
		mLikes = Utils.getPropertyInteger(graphObject, Properties.LIKES);

		// link
		mLink = Utils.getPropertyString(graphObject, Properties.LINK);

		// location
		GraphObject location = Utils.getPropertyGraphObject(graphObject, Properties.LOCATION);
		mLocation = Location.create(location);

		// mission
		mMission = Utils.getPropertyString(graphObject, Properties.MISSION);

		// name
		mName = Utils.getPropertyString(graphObject, Properties.NAME);

		// parking
		GraphObject parking = Utils.getPropertyGraphObject(graphObject, Properties.PARKING);
		mParking = Parking.create(parking);

		// picture
		GraphObject data = Utils.getPropertyGraphObject(mGraphObject, Properties.PICTURE);
		mPicture = Utils.getPropertyInsideProperty(data, "data", "url");

		// phone
		mPhone = Utils.getPropertyString(graphObject, Properties.PHONE);

		// press contact
		mPressContact = Utils.getPropertyString(graphObject, Properties.PRESS_CONTACT);

		// price range
		mPriceRange = Utils.getPropertyString(graphObject, Properties.PRICE_RANGE);

		// product
		mProduct = Utils.getPropertyString(graphObject, Properties.PRODUCTS);

		// restaurant service
		GraphObject service = Utils.getPropertyGraphObject(graphObject, Properties.RESTAURANT_SERVICES);
		mRestaurantService = RestaurantService.create(service);

		// restaurant specialties
		GraphObject specialties = Utils.getPropertyGraphObject(graphObject, Properties.RESTAURANT_SPECIALTIES);
		mRestaurantSpecialties = RestaurantSpecialties.create(specialties);

		// talking about count
		mTalkingAboutCount = Utils.getPropertyInteger(graphObject, Properties.TALKING_ABOUT_COUNT);

		// username
		mUsername = Utils.getPropertyString(graphObject, Properties.USERNAME);

		// website
		mWebsite = Utils.getPropertyString(graphObject, Properties.WEBSITE);

		// were here count
		mWereHereCount = Utils.getPropertyInteger(graphObject, Properties.WERE_HERE_COUNT);
	}

	public static Page create(GraphObject graphObject) {
		return new Page(graphObject);
	}

	public GraphObject getGraphObject() {
		return mGraphObject;
	}

	/**
	 * The Page ID
	 * 
	 * @return
	 */
	public String getId() {
		return mId;
	}

	/**
	 * Information about the Page
	 * 
	 * @return
	 */
	public String getAbout() {
		return mAbout;
	}

	/**
	 * Dress code of the business. Applicable to Restaurants or Nightlife. Can
	 * be one of Casual, Dressy or Unspecified
	 * 
	 * @return
	 */
	public String getAttire() {
		return mAttire;
	}

	/**
	 * Members of the band. Applicable to Bands
	 * 
	 * @return
	 */
	public String getBandMembers() {
		return mBandMembers;
	}

	/**
	 * Birthday of this person. Applicable to Pages representing people
	 * 
	 * @return
	 */
	public String getBirthday() {
		return mBirthday;
	}

	/**
	 * Booking agent of the band. Applicable to Bands
	 * 
	 * @return
	 */
	public String getBookingAgent() {
		return mBookingAgent;
	}

	/**
	 * Whether the current session user can post on this Page
	 * 
	 * @return
	 */
	public Boolean canPost() {
		return mCanPost;
	}

	/**
	 * The Page's category. e.g. Product/Service, Computers/Technology
	 * 
	 * @return
	 */
	public String getCategory() {
		return mCategory;
	}

	/**
	 * Number of checkins at a place represented by a Page
	 * 
	 * @return
	 */
	public Integer getNumCheckins() {
		return mNumCheckins;
	}

	/**
	 * The company overview. Applicable to Companies
	 * 
	 * @return
	 */
	public String getCompanyOverview() {
		return mCompanyOverview;
	}

	/**
	 * URL to the Photo that represents this cover photo.
	 * 
	 * @return
	 */
	public String getCover() {
		return mCover;
	}

	/**
	 * Current location of the Page
	 * 
	 * @return
	 */
	public String getCurrentLocation() {
		return mCurrentLocation;
	}

	/**
	 * The description of the Page
	 * 
	 * @return
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * The director of the film. Applicable to Films
	 * 
	 * @return
	 */
	public String getDirectedBy() {
		return mDirectedBy;
	}

	/**
	 * When the company is founded. Applicable to Companies
	 * 
	 * @return
	 */
	public String getFounded() {
		return mFounded;
	}

	/**
	 * General information provided by the Page
	 * 
	 * @return
	 */
	public String getGeneralInfo() {
		return mGeneralInfo;
	}

	/**
	 * General manager of the business. Applicable to Restaurants or Nightlife
	 * 
	 * @return
	 */
	public String getGeneralManager() {
		return mGeneralManager;
	}

	/**
	 * Hometown of the band. Applicable to Bands
	 * 
	 * @return
	 */
	public String getHometown() {
		return mHometown;
	}

	// /**
	// * {day}_{number}_{status}_{time} <br>
	// * Indicates a single range of opening hours for a day. Each day can have
	// 2
	// * different hours ranges. {day} should be the first 3 characters of the
	// day
	// * of the week, {number} should be either 1 or 2 to allow for the two
	// * different hours ranges per day. {status} should be either open or close
	// * to delineate the start or end of a time range. An example would be
	// * mon_1_open with value 17:00 and mon_1_close with value 21:15 which
	// would
	// * represent a single opening range of 5pm to 9:15pm on Mondays.
	// *
	// * @return
	// */
	// public List<String> getHours() {
	// return null;
	// }

	/**
	 * For businesses that are no longer operating.
	 * 
	 * @return
	 */
	public Boolean isPermanentlyClosed() {
		return mIsPermanetlyClosed;
	}

	/**
	 * Indicates whether the Page is published and visible to non-admins
	 * 
	 * @return
	 */
	public Boolean isPublished() {
		return mIsPublished;
	}

	/**
	 * Indicates whether the Page is unclaimed
	 * 
	 * @return
	 */
	public Boolean isUnclaimed() {
		return mIsUnclaimed;
	}

	/**
	 * The number of users who like the Page. For Global Brand Pages this is the
	 * count for all pages across the brand
	 * 
	 * @return
	 */
	public Integer getLikes() {
		return mLikes;
	}

	/**
	 * The Page's Facebook URL
	 * 
	 * @return
	 */
	public String getLink() {
		return mLink;
	}

	/**
	 * The location of this place. Applicable to all Places
	 * 
	 * @return
	 */
	public Location getLocation() {
		return mLocation;
	}

	/**
	 * The company mission. Applicable to Companies
	 * 
	 * @return
	 */
	public String getMission() {
		return mMission;
	}

	/**
	 * The name of the Page.
	 * 
	 * @return
	 */
	public String getName() {
		return mName;
	}

	/**
	 * Information about the parking available at a place
	 * 
	 * @return
	 */
	public Parking getParking() {
		return mParking;
	}

	/**
	 * The page 'profile' picture
	 */
	public String getPicture() {
		return mPicture;
	}
	
	/**
	 * Phone number provided by a Page
	 * 
	 * @return
	 */
	public String getPhone() {
		return mPhone;
	}

	/**
	 * Press contact information of the band. Applicable to Bands
	 * 
	 * @return
	 */
	public String getPressContanct() {
		return mPressContact;
	}

	/**
	 * Price range of the business. Applicable to Restaurants or Nightlife.
	 * 
	 * @return
	 */
	public String getPriceRange() {
		return mPriceRange;
	}

	/**
	 * The products of this company. Applicable to Companies
	 * 
	 * @return
	 */
	public String getProducts() {
		return mProduct;
	}

	/**
	 * Services the restaurant provides. Applicable to Restaurants
	 * 
	 * @return
	 */
	public RestaurantService getRestaurantService() {
		return mRestaurantService;
	}

	/**
	 * The restaurant's specialties. Applicable to Restaurants
	 * 
	 * @return
	 */
	public RestaurantSpecialties getRestaurantSpecialties() {
		return mRestaurantSpecialties;
	}

	/**
	 * The number of people talking about this Page
	 * 
	 * @return
	 */
	public Integer getTalkingAboutCount() {
		return mTalkingAboutCount;
	}

	/**
	 * The alias of the Page. For example, for www.facebook.com/platform the
	 * username is 'platform'
	 * 
	 * @return
	 */
	public String getUsername() {
		return mUsername;
	}

	/**
	 * The URL of the Page's website
	 * 
	 * @return
	 */
	public String getWebsite() {
		return mWebsite;
	}

	/**
	 * The number of visits to this Page's location
	 * 
	 * @return
	 */
	public Integer getWereHereCount() {
		return mWereHereCount;
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
		 * The Page ID
		 */
		public static final String ID = "id";

		/**
		 * Information about the Page
		 */
		public static final String ABOUT = "about";

		/**
		 * Dress code of the business. Applicable to Restaurants or Nightlife.
		 * Can be one of Casual, Dressy or Unspecified
		 */
		public static final String ATTIRE = "attire";

		/**
		 * Members of the band. Applicable to Bands
		 */
		public static final String BAND_MEMBERS = "band_members";

		/**
		 * The best available Page on Facebook for the concept represented by
		 * this Page. The best available Page takes into account authenticity
		 * and fan count
		 */
		public static final String BEST_PAGE = "best_page";

		/**
		 * Birthday of this person. Applicable to Pages representing people
		 */
		public static final String BIRTHDAY = "birthday";

		/**
		 * Booking agent of the band. Applicable to Bands
		 */
		public static final String BOOKING_AGENT = "booking_agent";

		/**
		 * Whether the current session user can post on this Page
		 */
		public static final String CAN_POST = "can_post";

		/**
		 * The Page's category. e.g. Product/Service, Computers/Technology
		 */
		public static final String CATEGORY = "category";

		/**
		 * Number of checkins at a place represented by a Page
		 */
		public static final String CHECKINS = "checkins";

		/**
		 * The company overview. Applicable to Companies
		 */
		public static final String COMPANY_OVERVIEW = "company_overview";

		/**
		 * URL to the Photo that represents this cover photo.
		 */
		public static final String COVER = "cover";

		/**
		 * Current location of the Page
		 */
		public static final String CURRENT_LOCATION = "current_location";

		/**
		 * The description of the Page
		 */
		public static final String DESCRIPTION = "description";

		/**
		 * The director of the film. Applicable to Films
		 */
		public static final String DIRECTED_BY = "directed_by";

		/**
		 * When the company is founded. Applicable to Companies
		 */
		public static final String FOUNDED = "founded";

		/**
		 * General information provided by the Page
		 */
		public static final String GENERAL_INFO = "general_info";

		/**
		 * General manager of the business. Applicable to Restaurants or
		 * Nightlife
		 */
		public static final String GENERAL_MANAGER = "general_manager";

		/**
		 * Hometown of the band. Applicable to Bands
		 */
		public static final String HOMETOWN = "hometown";

		/**
		 * {day}_{number}_{status}_{time} <br>
		 * Indicates a single range of opening hours for a day. Each day can
		 * have 2 different hours ranges. {day} should be the first 3 characters
		 * of the day of the week, {number} should be either 1 or 2 to allow for
		 * the two different hours ranges per day. {status} should be either
		 * open or close to delineate the start or end of a time range. An
		 * example would be mon_1_open with value 17:00 and mon_1_close with
		 * value 21:15 which would represent a single opening range of 5pm to
		 * 9:15pm on Mondays.
		 */
		public static final String HOURS = "hours";

		/**
		 * For businesses that are no longer operating
		 */
		public static final String IS_PERMANENTLY_CLOSED = "is_permanently_closed";

		/**
		 * Indicates whether the Page is published and visible to non-admins
		 */
		public static final String IS_PUBLISHED = "is_published";

		/**
		 * Indicates whether the Page is unclaimed
		 */
		public static final String IS_UNCLAIMED = "is_unclaimed";

		/**
		 * The number of users who like the Page. For Global Brand Pages this is
		 * the count for all pages across the brand
		 */
		public static final String LIKES = "likes";

		/**
		 * The Page's Facebook URL
		 */
		public static final String LINK = "link";

		/**
		 * The location of this place. Applicable to all Places
		 */
		public static final String LOCATION = "location";

		/**
		 * The company mission. Applicable to Companies
		 */
		public static final String MISSION = "mission";

		/**
		 * The name of the Page
		 */
		public static final String NAME = "name";

		/**
		 * Information about the parking available at a place
		 */
		public static final String PARKING = "parking";

		public static final String PICTURE = "picture";
		
		/**
		 * Phone number provided by a Page
		 */
		public static final String PHONE = "phone";

		/**
		 * Press contact information of the band. Applicable to Bands
		 */
		public static final String PRESS_CONTACT = "press_contact";

		/**
		 * Price range of the business. Applicable to Restaurants or Nightlife
		 */
		public static final String PRICE_RANGE = "price_range";

		/**
		 * The products of this company. Applicable to Companies
		 */
		public static final String PRODUCTS = "products";

		/**
		 * Services the restaurant provides. Applicable to Restaurants
		 */
		public static final String RESTAURANT_SERVICES = "restaurant_services";

		/**
		 * The restaurant's specialties. Applicable to Restaurants
		 */
		public static final String RESTAURANT_SPECIALTIES = "restaurant_specialties";

		/**
		 * The number of people talking about this Page
		 */
		public static final String TALKING_ABOUT_COUNT = "talking_about_count";

		/**
		 * The alias of the Page. For example, for www.facebook.com/platform the
		 * username is 'platform'
		 */
		public static final String USERNAME = "username";

		/**
		 * The URL of the Page's website
		 */
		public static final String WEBSITE = "website";

		/**
		 * The number of visits to this Page's location
		 */
		public static final String WERE_HERE_COUNT = "were_here_count";
		
		public static class Builder {
			Set<String> properties;

			public Builder() {
				properties = new HashSet<String>();
			}

			/**
			 * Add property you need
			 * 
			 * @param property
			 *            The property of the page<br>
			 *            For example: {@link Properties#FOUNDED}
			 * @return {@link Builder}
			 */
			public Builder add(String property) {
				properties.add(property);
				return this;
			}

			public Properties build() {
				return new Properties(this);
			}
		}
	}
}
