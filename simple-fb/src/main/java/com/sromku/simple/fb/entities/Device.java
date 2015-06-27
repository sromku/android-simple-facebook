package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sromku on 6/27/15.
 * // @see https://developers.facebook.com/docs/facebook-login/for-devices
 */
public class Device {

    private static final String CODE = "code";
    private static final String USER_CODE = "user_code";
    private static final String VERIFICATION_URI = "verification_uri";
    private static final String EXPIRES_IN = "expires_in";
    private static final String INTERVAL = "interval";

    @SerializedName(CODE)
    private String mAuthorizationCode;

    @SerializedName(USER_CODE)
    private String mUserCode;

    @SerializedName(VERIFICATION_URI)
    private String mUserVerificationUrl;

    @SerializedName(EXPIRES_IN)
    private Integer mExpiresIn;

    @SerializedName(INTERVAL)
    private Integer mPollInterval;

    /**
     * The code that user should insert in verification url
     * @return
     */
    public String getUserCode() {
        return mUserCode;
    }

    /**
     * The verification url that user should open and insert the code there
     * @return
     */
    public String getUserVerificationUrl() {
        return mUserVerificationUrl;
    }

    /**
     * The internal code for polling once in 5 seconds to know if user was authenticated
     * @return
     */
    public String getAuthorizationCode() {
        return mAuthorizationCode;
    }

    /**
     * Expiration time of the code in seconds
     * @return
     */
    public Integer getExpiresIn() {
        return mExpiresIn;
    }

    /**
     * Minimum time between polling requests
     * @return
     */
    public Integer getPollInterval() {
        return mPollInterval;
    }

}
