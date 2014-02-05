package com.sromku.simple.fb.entities;

import org.json.JSONObject;

public class Group {
    private String mId;
    private String mName;
    private int mBookmarkOrder;

    private Group(JSONObject jsonObject)
    {
        mId = jsonObject.optString("id");
        mName = jsonObject.optString("name");
        mBookmarkOrder = jsonObject.optInt("bookmark_order");
    }
    
    public static Group create(JSONObject jsonObject)
    {
        return new Group(jsonObject);
    }
    
    public String getId()
    {
        return mId;
    }
    
    public String getName()
    {
        return mName;
    }
    
    public int getBookmarkOrder()
    {
        return mBookmarkOrder;
    }
}
