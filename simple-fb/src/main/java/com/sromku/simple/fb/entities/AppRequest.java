package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Application request that is sent by one user to another.
 */
public class  AppRequest {

    private static final String ID = "id";
    private static final String APPLICATION = "application";
    private static final String TO = "to";
    private static final String FROM = "from";
    private static final String DATA = "data";
    private static final String MESSAGE = "message";
    private static final String CREATED_TIME = "created_time";

    @SerializedName(ID)
    private String mRequestId;

    @SerializedName(APPLICATION)
    private Application mApplication;

    @SerializedName(TO)
    private User mTo;

    @SerializedName(FROM)
    private User mFrom;

    @SerializedName(DATA)
    private String mData;

    @SerializedName(MESSAGE)
    private String mMessage;

    @SerializedName(CREATED_TIME)
    private Date mCreatedTime;

    public String getRequestId() {
        return mRequestId;
    }

    /**
     * The application used to send the request.
     */
    public Application getApplication() {
        return mApplication;
    }

    /**
     * The user who got the request.
     */
    public User getTo() {
        return mTo;
    }

    /**
     * The user who sent the request.
     */
    public User getFrom() {
        return mFrom;
    }

    /**
     * Optional data passed with the request for tracking purposes.
     */
    public String getData() {
        return mData;
    }

    /**
     * The message included with the request.
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * Timestamp that indicates when the request was sent.
     */
    public Date getCreatedTime() {
        return mCreatedTime;
    }
}
