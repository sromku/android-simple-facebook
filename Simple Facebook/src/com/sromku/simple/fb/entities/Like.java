package com.sromku.simple.fb.entities;

import org.json.JSONObject;

import android.os.Bundle;

public class Like {

    private Bundle mBundle = null;

    private Like(Builder builder)
    {
        this.mBundle = builder.mBundle;
    }

    public Bundle getBundle()
    {
        return mBundle;
    }

    public static class Builder
    {
        Bundle mBundle;
        JSONObject mProperties = new JSONObject();
        JSONObject mActions = new JSONObject();

        static class Parameters
        {
            public static final String OBJECT = "object";
            public static final String IMAGE = "image";
            public static final String LINK = "link";
            public static final String TITLE = "title";
        }

        public Builder()
        {
            mBundle = new Bundle();
        }

        /**
         * The object that was liked (this is actually the link to the url with facebook meta tags)
         * 
         * @param object
         * @return {@link Builder}
         */
        public Builder setObject(String object)
        {
            mBundle.putString(Parameters.OBJECT, object);
            return this;
        }
        
        /**
         * The URL of an image that will override the image from the object meta tags
         * 
         * @param image
         * @return {@link Builder}
         */
        public Builder setImage(String image) {
            mBundle.putString(Parameters.IMAGE, image);
            return this;
        }
        
        /**
         * The redirect link when people click the like story
         * 
         * @param link
         * @return {@link Builder}
         */
        public Builder setLink(String link) {
            mBundle.putString(Parameters.LINK, link);
            return this;
        }
        
        /**
         * The title of the item that was liked.
         * 
         * @param link
         * @return {@link Builder}
         */
        public Builder setTitle(String title) {
            mBundle.putString(Parameters.TITLE, title);
            return this;
        }
        
        public Like build()
        {
            return new Like(this);
        }
    }
}
