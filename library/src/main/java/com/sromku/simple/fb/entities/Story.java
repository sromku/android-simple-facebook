package com.sromku.simple.fb.entities;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.utils.GraphPath;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Open graph story
 *
 * @author sromku
 * // @see http://ogp.me/
 */

public class Story implements Publishable {

    private StoryObject mStoryObject;
    private StoryAction mStoryAction;

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
		/*
		 * set object id (that is hosted on facebook server) or url (that is
		 * hosted on your servers) or user-owned one
		 */
        if (mStoryObject.getId() != null) {
            bundle.putString(mStoryObject.getNoun(), mStoryObject.getId());
        } else if (mStoryObject.getHostedUrl() != null) {
            bundle.putString(mStoryObject.getNoun(), mStoryObject.getHostedUrl());
        } else {
            bundle = mStoryObject.getBundle();
            bundle.putString(mStoryObject.getNoun(), bundle.getString(StoryObject.OBJECT));
        }

        // put action params if such exist
        if (mStoryAction.getParams() != null) {
            bundle.putAll(mStoryAction.getParams());
        }

        return bundle;
    }

    @Override
    public String getPath() {
        String namespace = SimpleFacebook.getConfiguration().getNamespace();
        return namespace + ":" + mStoryAction.getActionName();
    }

    public String getObjectType() {
        String namespace = SimpleFacebook.getConfiguration().getNamespace();
        return namespace + ":" + mStoryObject.getNoun();
    }

    @Override
    public Permission getPermission() {
        return Permission.PUBLISH_ACTION;
    }

    public StoryAction getStoryAction() {
        return mStoryAction;
    }

    public StoryObject getStoryObject() {
        return mStoryObject;
    }

    private Story(Builder buidler) {
        mStoryObject = buidler.storyObject;
        mStoryAction = buidler.storyAction;
    }

    public static class Builder {

        private StoryObject storyObject;
        private StoryAction storyAction;

        public Builder setAction(StoryAction storyAction) {
            this.storyAction = storyAction;
            return this;
        }

        public Builder setObject(StoryObject storyObject) {
            this.storyObject = storyObject;
            return this;
        }

        public Story build() {
            return new Story(this);
        }

    }

    public static class StoryAction {

        private Bundle mBundle = new Bundle();
        private String mActionName;

        private StoryAction(Builder builder) {
            mBundle = builder.bundle;
            mActionName = builder.actionName;
        }

        public static class Builder {

            private Bundle bundle = new Bundle();
            private String actionName;

            public Builder setAction(String actionName) {
                this.actionName = actionName;
                return this;
            }

            public Builder addProperty(String param, String value) {
                bundle.putString(param, value);
                return this;
            }

            public StoryAction build() {
                return new StoryAction(this);
            }
        }

        public String getActionName() {
            return mActionName;
        }

        public Bundle getParams() {
            return mBundle;
        }

        public String getParamValue(String param) {
            return mBundle.getString(param);
        }
    }

    public static class StoryObject implements Publishable {

        private static final String ID = "id";
        private static final String TYPE = "type";
        private static final String TITLE = "title";
        private static final String URL = "url";
        private static final String IMAGE = "image";
        private static final String DATA = "data";
        private static final String DESCRIPTION = "description";
        private static final String UPDATED_TIME = "updated_time";
        private static final String CREATED_TIME = "created_time";
        private static final String APPLICATION = "application";
        private static final String OBJECT = "object";
        private static final String PRIVACY = "privacy";

        @SerializedName(ID)
        private String mId;

        @SerializedName(TYPE)
        private String mType;

        @SerializedName(TITLE)
        private String mTitle;

        @SerializedName(URL)
        private String mUrl;

        private String mImage;

        @SerializedName(IMAGE)
        private List<ImageUrl> mImageUrl;

        @SerializedName(DESCRIPTION)
        private String mDescription;

        @SerializedName(UPDATED_TIME)
        private Date mUpdatedTime;

        @SerializedName(CREATED_TIME)
        private Date mCreatedTime;

        @SerializedName(APPLICATION)
        private Application mApplication;

        @SerializedName(PRIVACY)
        private Privacy mPrivacy;

        private String mNoun;
        private String mHostedUrl;
        private JSONObject mData;

        private static class ImageUrl {
            String url;
        }

        private StoryObject(Builder builder) {
            String namespace = SimpleFacebook.getConfiguration().getNamespace();
            mType = namespace + ":" + builder.noun;
            mNoun = builder.noun;
            mTitle = builder.title;
            mId = builder.id;
            mUrl = builder.url;
            mDescription = builder.description;
            mData = builder.data;
            mImage = builder.image;
            mPrivacy = builder.privacy;
            mHostedUrl = builder.hostedUrl;
        }

        @Override
        public Bundle getBundle() {
            Bundle bundle = new Bundle();
            JSONObject object = new JSONObject();
            try {
                object.put(URL, mUrl);
                object.put(IMAGE, mImage);
                object.put(TITLE, mTitle);
                object.put(DESCRIPTION, mDescription);
                if (mData != null) {
                    object.put(DATA, mData);
                }
            } catch (Exception e) {
                // do nothing
            }
            bundle.putString(OBJECT, object.toString());
            if (mPrivacy != null) {
                bundle.putString(PRIVACY, mPrivacy.getJSONString());
            }
            return bundle;
        }

        @Override
        public String getPath() {
            return GraphPath.OBJECTS + "/" + mType;
        }

        @Override
        public Permission getPermission() {
            return Permission.PUBLISH_ACTION;
        }

        public Bundle getObjectProperties() {
            Bundle bundle = new Bundle();
            bundle.putString(URL, mUrl);
            bundle.putString(IMAGE, mImage);
            bundle.putString(TITLE, mTitle);
            bundle.putString(DESCRIPTION, mDescription);
            return bundle;
        }

        public String getId() {
            return mId;
        }

        public String getType() {
            return mType;
        }

        public String getNoun() {
            return mNoun;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getUrl() {
            return mUrl;
        }

        public String getHostedUrl() {
            return mHostedUrl;
        }

        public String getImage() {
            if (mImageUrl != null && mImageUrl.size() > 0) {
                return mImageUrl.get(0).url;
            }
            return null;
        }

        public String getDescription() {
            return mDescription;
        }

        public Date getUpdatedTime() {
            return mUpdatedTime;
        }

        public Date getCreatedTime() {
            return mCreatedTime;
        }

        public Application getApplication() {
            return mApplication;
        }

        public JSONObject getData() {
            return mData;
        }

        /**
         * Get your custom parameter of your object, as you defined in Open
         * Graph dashboard of your story.
         *
         * @param param
         * @return The value of your custom parameter
         */
        public Object getCustomData(String param) {
            return mData.opt(param);
        }

        public static class Builder {

            private String id;
            private String noun;
            private String url;
            private String image;
            private String title;
            private String description;
            private Privacy privacy;
            private String hostedUrl;
            private JSONObject data = null;

            public Builder() {
            }

            /**
             * Set the noun. For example: food
             *
             * @param noun
             * @return {@link com.sromku.simple.fb.entities.Story.StoryObject.Builder}
             */
            public Builder setNoun(String noun) {
                this.noun = noun;
                return this;
            }

            /**
             * Set the url of the object. By clicking on published open graph,
             * this url will be opened.
             *
             * @param url
             * @return {@link com.sromku.simple.fb.entities.Story.StoryObject.Builder}
             */
            public Builder setUrl(String url) {
                this.url = url;
                return this;
            }

            /**
             * Once you set this one, all other properties except
             * {@link #setNoun(String)} and {@link #addProperty(String, Object)}
             * WON'T be valid upon publishing.
             *
             * @param hostedUrl
             */
            public Builder setHostedUrl(String hostedUrl) {
                this.hostedUrl = hostedUrl;
                return this;
            }

            /**
             * Set the id of the object that was created on facebook servers.
             *
             * @param id
             *            The object id
             * @return {@link com.sromku.simple.fb.entities.Story.StoryObject.Builder}
             */
            public Builder setId(String id) {
                this.id = id;
                return this;
            }

            /**
             * Set image url of the object
             *
             * @param imageUrl
             * @return {@link com.sromku.simple.fb.entities.Story.StoryObject.Builder}
             */
            public Builder setImage(String imageUrl) {
                this.image = imageUrl;
                return this;
            }

            /**
             * Set the title of the object. This is the concrete instance of
             * noun. For example: steak, honey, apple, ...
             *
             * @param title
             * @return {@link com.sromku.simple.fb.entities.Story.StoryObject.Builder}
             */
            public Builder setTitle(String title) {
                this.title = title;
                return this;
            }

            /**
             * Set the description of the concrete object
             *
             * @param description
             * @return {@link com.sromku.simple.fb.entities.Story.StoryObject.Builder}
             */
            public Builder setDescription(String description) {
                this.description = description;
                return this;
            }

            /**
             * Set the max privacy of the object. <br>
             * <br>
             * Your privacy setting can not be more public than what the person
             * has chosen for your app. For example, if a person has chosen
             * 'Friends' you can't set the privacy of an object to 'Public.' If
             * you don't include a privacy parameter, the default privacy
             * setting that the person has chosen for the app will be used for
             * the privacy of the object.
             *
             * @param privacy
             * @return
             */
            public Builder setPrivacy(Privacy privacy) {
                this.privacy = privacy;
                return this;
            }

            /**
             * Add custom properties and their values
             *
             * @param param
             *            The paramater as defined in facebook dashboard of
             *            custom story in your object
             * @param value
             *            The value that this param should have
             * @return {@link com.sromku.simple.fb.entities.Story.StoryObject.Builder}
             */
            public Builder addProperty(String param, Object value) {
                if (data == null) {
                    data = new JSONObject();
                }
                try {
                    data.put(param, value);
                } catch (JSONException e) {
                    // do nothing
                }
                return this;
            }

            public StoryObject build() {
                return new StoryObject(this);
            }
        }
    }

}
