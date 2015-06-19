package com.sromku.simple.fb.entities;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.utils.GraphPath;

/**
 * // @see https://developers.facebook.com/docs/graph-api/reference/user/scores/
 */
public class Score implements Publishable {

    private static final String USER = "user";
    private static final String SCORE = "score";
    private static final String APPLICATION = "application";

    @SerializedName(USER)
    private User mUser;

    @SerializedName(SCORE)
    private Integer mScore;

    @SerializedName(APPLICATION)
    private Application mApplication;

    private Score(Builder builder) {
        mScore = builder.mScore;
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
