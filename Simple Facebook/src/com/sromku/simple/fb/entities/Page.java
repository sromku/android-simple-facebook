package com.sromku.simple.fb.entities;

import java.util.List;

import org.json.JSONObject;

import com.facebook.model.GraphObject;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/page
 */
public class Page {

    private final GraphObject mGraphObject;

    private Page(GraphObject graphObject) {
	mGraphObject = graphObject;
    }

    public static Page create(GraphObject graphObject) {
	return new Page(graphObject);
    }

    /**
     * The Page ID
     * 
     * @return
     */
    public String getId() {
	return String.valueOf(mGraphObject.getProperty(Properties.ID));
    }

    /**
     * Information about the Page
     * 
     * @return
     */
    public String getAbout() {
	return String.valueOf(mGraphObject.getProperty(Properties.ABOUT));
    }

    /**
     * Dress code of the business. Applicable to Restaurants or Nightlife. Can
     * be one of Casual, Dressy or Unspecified
     * 
     * @return
     */
    public String getAttire() {
	return String.valueOf(mGraphObject.getProperty(Properties.ATTIRE));
    }

    /**
     * Members of the band. Applicable to Bands
     * 
     * @return
     */
    public String getBandMembers() {
	return String.valueOf(mGraphObject.getProperty(Properties.BAND_MEMBERS));
    }

    /**
     * Birthday of this person. Applicable to Pages representing people
     * 
     * @return
     */
    public String getBirthday() {
	return String.valueOf(mGraphObject.getProperty(Properties.BIRTHDAY));
    }

    /**
     * Booking agent of the band. Applicable to Bands
     * 
     * @return
     */
    public String getBookingAgent() {
	return String.valueOf(mGraphObject.getProperty(Properties.BOOKING_AGENT));
    }

    /**
     * Whether the current session user can post on this Page
     * 
     * @return
     */
    public Boolean canPost() {
	Boolean property = (Boolean) mGraphObject.getProperty(Properties.CAN_POST);
	if (property != null) {
	    return property;
	}
	return false;
    }

    /**
     * The Page's category. e.g. Product/Service, Computers/Technology
     * 
     * @return
     */
    public String getCategory() {
	return String.valueOf(mGraphObject.getProperty(Properties.CATEGORY));
    }

    /**
     * Number of checkins at a place represented by a Page
     * 
     * @return
     */
    public Integer getNumCheckins() {
	return Integer.valueOf(String.valueOf(mGraphObject.getProperty(Properties.CHECKINS)));
    }

    /**
     * The company overview. Applicable to Companies
     * 
     * @return
     */
    public String getCompanyOverview() {
	return String.valueOf(mGraphObject.getProperty(Properties.COMPANY_OVERVIEW));
    }

    /**
     * URL to the Photo that represents this cover photo.
     * 
     * @return
     */
    public String getCover() {
	JSONObject jsonObject = (JSONObject) mGraphObject.getProperty(Properties.COVER);
	return jsonObject.optString("source");	
    }

    /**
     * Current location of the Page
     * 
     * @return
     */
    public String getCurrentLocation() {
	return String.valueOf(mGraphObject.getProperty(Properties.CURRENT_LOCATION));
    }

    /**
     * The description of the Page
     * 
     * @return
     */
    public String getDescription() {
	return String.valueOf(mGraphObject.getProperty(Properties.DESCRIPTION));
    }

    /**
     * The director of the film. Applicable to Films
     * 
     * @return
     */
    public String getDirectedBy() {
	return String.valueOf(mGraphObject.getProperty(Properties.DIRECTED_BY));
    }

    /**
     * When the company is founded. Applicable to Companies
     * 
     * @return
     */
    public String getFounded() {
	return String.valueOf(mGraphObject.getProperty(Properties.FOUNDED));
    }

    /**
     * General information provided by the Page
     * 
     * @return
     */
    public String getGeneralInfo() {
	return String.valueOf(mGraphObject.getProperty(Properties.GENERAL_INFO));
    }

    /**
     * General manager of the business. Applicable to Restaurants or Nightlife
     * 
     * @return
     */
    public String getGeneralManager() {
	return String.valueOf(mGraphObject.getProperty(Properties.GENERAL_MANAGER));
    }

    /**
     * Hometown of the band. Applicable to Bands
     * 
     * @return
     */
    public String getHometown() {
	return String.valueOf(mGraphObject.getProperty(Properties.HOMETOWN));
    }

    /**
     * {day}_{number}_{status}_{time} <br>
     * Indicates a single range of opening hours for a day. Each day can have 2
     * different hours ranges. {day} should be the first 3 characters of the day
     * of the week, {number} should be either 1 or 2 to allow for the two
     * different hours ranges per day. {status} should be either open or close
     * to delineate the start or end of a time range. An example would be
     * mon_1_open with value 17:00 and mon_1_close with value 21:15 which would
     * represent a single opening range of 5pm to 9:15pm on Mondays.
     * 
     * @return
     */
    public List<String> getHours() {
	return null;
    }

    /**
     * For businesses that are no longer operating.
     * 
     * @return
     */
    public Boolean isPermanentlyClosed() {
	return Boolean.valueOf(String.valueOf(mGraphObject.getProperty(Properties.IS_PERMANENTLY_CLOSED)));
    }

    /**
     * Indicates whether the Page is published and visible to non-admins
     * 
     * @return
     */
    public Boolean isPublished() {
	return Boolean.valueOf(String.valueOf(mGraphObject.getProperty(Properties.IS_PUBLISHED)));
    }

    /**
     * Indicates whether the Page is unclaimed
     * 
     * @return
     */
    public Boolean isUnclaimed() {
	return Boolean.valueOf(String.valueOf(mGraphObject.getProperty(Properties.IS_UNCLAIMED)));
    }

    /**
     * The number of users who like the Page. For Global Brand Pages this is the
     * count for all pages across the brand
     * 
     * @return
     */
    public Integer getLikes() {
	return Integer.valueOf(String.valueOf(mGraphObject.getProperty(Properties.LIKES)));
    }

    /**
     * The Page's Facebook URL
     * 
     * @return
     */
    public String getLink() {
	return String.valueOf(mGraphObject.getProperty(Properties.LINK));
    }

    /**
     * The location of this place. Applicable to all Places
     * 
     * @return
     */
    public Location getLocation() {
	return null;
    }

    /**
     * The company mission. Applicable to Companies
     * 
     * @return
     */
    public String getMission() {
	return String.valueOf(mGraphObject.getProperty(Properties.MISSION));
    }

    /**
     * The name of the Page.
     * 
     * @return
     */
    public String getName() {
	return String.valueOf(mGraphObject.getProperty(Properties.NAME));
    }

    /**
     * Information about the parking available at a place
     * 
     * @return
     */
    public Parking getParking() {
	GraphObject graphObject = mGraphObject.getPropertyAs(Properties.PARKING, GraphObject.class);
	return Parking.create(graphObject);
    }

    /**
     * Phone number provided by a Page
     * 
     * @return
     */
    public String getPhone() {
	return String.valueOf(mGraphObject.getProperty(Properties.PHONE));
    }

    /**
     * Press contact information of the band. Applicable to Bands
     * 
     * @return
     */
    public String getPressContanct() {
	return String.valueOf(mGraphObject.getProperty(Properties.PRESS_CONTACT));
    }

    /**
     * Price range of the business. Applicable to Restaurants or Nightlife.
     * 
     * @return
     */
    public String getPriceRange() {
	return String.valueOf(mGraphObject.getProperty(Properties.PRICE_RANGE));
    }

    /**
     * The products of this company. Applicable to Companies
     * 
     * @return
     */
    public String getProducts() {
	return String.valueOf(mGraphObject.getProperty(Properties.PRODUCTS));
    }

    /**
     * Services the restaurant provides. Applicable to Restaurants
     * 
     * @return
     */
    public RestaurantService getRestaurantService() {
	GraphObject graphObject = mGraphObject.getPropertyAs(Properties.RESTAURANT_SERVICES, GraphObject.class);
	return RestaurantService.create(graphObject);
    }

    /**
     * The restaurant's specialties. Applicable to Restaurants
     * 
     * @return
     */
    public RestaurantSpecialties getRestaurantSpecialties() {
	GraphObject graphObject = mGraphObject.getPropertyAs(Properties.RESTAURANT_SPECIALTIES, GraphObject.class);
	return RestaurantSpecialties.create(graphObject);
    }

    /**
     * The number of people talking about this Page
     * 
     * @return
     */
    public Integer getTalkingAboutCount() {
	return Integer.valueOf(String.valueOf(mGraphObject.getProperty(Properties.TALKING_ABOUT_COUNT)));
    }

    /**
     * The alias of the Page. For example, for www.facebook.com/platform the
     * username is 'platform'
     * 
     * @return
     */
    public String getUsername() {
	return String.valueOf(mGraphObject.getProperty(Properties.USERNAME));
    }

    /**
     * The URL of the Page's website
     * 
     * @return
     */
    public String getWebsite() {
	return String.valueOf(mGraphObject.getProperty(Properties.WEBSITE));
    }

    /**
     * The number of visits to this Page's location
     * 
     * @return
     */
    public Integer getWereHereCount() {
	return Integer.valueOf(String.valueOf(mGraphObject.getProperty(Properties.WERE_HERE_COUNT)));
    }

    public static class Properties {

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
	 * The number of users who like the Page. For Global Brand Pages this is the count for all pages across the brand
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
	 * The alias of the Page. For example, for www.facebook.com/platform the username is 'platform'
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
    }
}
