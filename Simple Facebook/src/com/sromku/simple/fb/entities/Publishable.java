package com.sromku.simple.fb.entities;

import android.os.Bundle;

import com.sromku.simple.fb.Permission;

public interface Publishable {

    /**
     * Is used for publishing actions
     * 
     * @return
     */
    Bundle getBundle();

    /**
     * https://developers.facebook.com/docs/reference/api/publishing/
     * 
     * @return Kinds of object via the Graph API
     */
    String getPath();

    /**
     * Get the needed permission for being able to publish this item
     * 
     * @return {@link Permission}
     */
    Permission getPermission();

}
