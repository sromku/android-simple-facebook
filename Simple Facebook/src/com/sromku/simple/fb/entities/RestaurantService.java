package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/page
 */
public class RestaurantService {

	private static final String KIDS = "kids";
	private static final String DELIVERY = "delivery";
	private static final String WALKINS = "walkins";
	private static final String CATERING = "catering";
	private static final String RESERVE = "reserve";
	private static final String GROUPS = "groups";
	private static final String WAITER = "waiter";
	private static final String OUTDOOR = "outdoor";
	private static final String TAKEOUT = "takeout";

	private Integer mKids;
	private Integer mDelivery;
	private Integer mWalkins;
	private Integer mCatering;
	private Integer mReserve;
	private Integer mGroups;
	private Integer mWaiter;
	private Integer mOutdoor;
	private Integer mTakeout;

	private final GraphObject mGraphObject;

	private RestaurantService(GraphObject graphObject) {
		mGraphObject = graphObject;

		// kids
		mKids = Utils.getPropertyInteger(graphObject, KIDS);

		// delivery
		mDelivery = Utils.getPropertyInteger(graphObject, DELIVERY);

		// walkins
		mWalkins = Utils.getPropertyInteger(graphObject, WALKINS);

		// category
		mCatering = Utils.getPropertyInteger(graphObject, CATERING);

		// reserver
		mReserve = Utils.getPropertyInteger(graphObject, RESERVE);

		// groups
		mGroups = Utils.getPropertyInteger(graphObject, GROUPS);

		// waiters
		mWaiter = Utils.getPropertyInteger(graphObject, WAITER);

		// outdoor
		mOutdoor = Utils.getPropertyInteger(graphObject, OUTDOOR);

		// takeout
		mTakeout = Utils.getPropertyInteger(graphObject, TAKEOUT);
	}

	public static RestaurantService create(GraphObject graphObject) {
		return new RestaurantService(graphObject);
	}

	public GraphObject getGraphObject() {
		return mGraphObject;
	}

	/**
	 * Whether the restaurant is kids-friendly
	 */
	public Integer getKids() {
		return mKids;
	}

	/**
	 * Whether the restaurant has delivery service
	 * 
	 * @return
	 */
	public Integer getDelivery() {
		return mDelivery;
	}

	/**
	 * Whether the restaurant welcomes walkins
	 */
	public Integer getWalkins() {
		return mWalkins;
	}

	/**
	 * Whether the restaurant has catering service
	 */
	public Integer getCatering() {
		return mCatering;
	}

	/**
	 * Whether the restaurant takes reservations
	 */
	public Integer getReserve() {
		return mReserve;
	}

	/**
	 * Whether the restaurant is group-friendly
	 */
	public Integer getGroups() {
		return mGroups;
	}

	/**
	 * Whether the restaurant has waiters
	 */
	public Integer getWaiter() {
		return mWaiter;
	}

	/**
	 * Whether the restaurant has outdoor seating
	 */
	public Integer getOutdoor() {
		return mOutdoor;
	}

	/**
	 * Whether the restaurant has takeout service
	 */
	public Integer getTakeout() {
		return mTakeout;
	}
}
