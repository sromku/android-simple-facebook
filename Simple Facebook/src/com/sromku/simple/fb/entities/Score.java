package com.sromku.simple.fb.entities;

import android.os.Bundle;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

/**
 * @see https://developers.facebook.com/docs/graph-api/reference/user/scores/
 */
public class Score implements Publishable {

	private static final String USER = "user";
	private static final String SCORE = "score";
	private static final String APPLICATION = "application";

	private User mUser;
	private Integer mScore;
	private Application mApplication;

	private Score(Builder builder) {
		mScore = builder.mScore;
	}
	
	private Score(GraphObject graphObject) {
		// profile
		mUser = Utils.createUser(graphObject, USER);
		
		// score
		mScore = Utils.getPropertyInteger(graphObject, SCORE);
		
		// application
		mApplication = Application.create(Utils.getPropertyGraphObject(graphObject, APPLICATION));
	}
	
	public static Score create(GraphObject graphObject) {
		return new Score(graphObject);
	}

	/**
	 * The person associated with the scores.
	 */
	public User getUser() {
		return mUser;
	}

	/**
	 * The actual score.
	 */
	public Integer getScore() {
		return mScore;
	}

	/**
	 * The app in which the score was achieved.
	 */
	public Application getApplication() {
		return mApplication;
	}

	@Override
	public Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putInt(SCORE, mScore);
		return bundle;
	}

	@Override
	public String getPath() {
		return GraphPath.SCORES;
	}

	@Override
	public Permission getPermission() {
		return Permission.PUBLISH_ACTION;
	}

	public static class Builder {
		private int mScore;

		public Builder setScore(int score) {
			mScore = score;
			return this;
		}

		public Score build() {
			return new Score(this);
		}
	}

}
