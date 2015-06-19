package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Object that describes 'Application' on facebook side.
 *
 * @author sromku
 */
public class Application {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String NAMESPACE = "namespace";

    @SerializedName(ID)
    private String mAppId = null;

    @SerializedName(NAME)
    private String mAppName = null;

    @SerializedName(NAMESPACE)
    private String mAppNamespace = null;

    /**
     * @return Application id
     */
    public String getAppId() {
        return mAppId;
    }

    /**
     * @return Application name
     */
    public String getAppName() {
        return mAppName;
    }

    /**
     * @return Application namespace
     */
    public String getAppNamespace() {
        return mAppNamespace;
    }
}
