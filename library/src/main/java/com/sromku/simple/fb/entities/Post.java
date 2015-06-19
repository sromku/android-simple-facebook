package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * An individual entry in a profile's feed as represented in the Graph API.
 *
 * @author sromku
 * // @see https://developers.facebook.com/docs/reference/api/post
 */
public class Post {

    public enum PostType {
        /**
         * Everything that was published (links, statuses, photos...) that
         * appears on the users' wall.
         */
        ALL(GraphPath.FEED),
        /**
         * Link published by the user themselves.
         */
        LINKS(GraphPath.LINKS),
        /**
         * Posts published by the person themselves.
         */
        POSTS(GraphPath.POSTS),
        /**
         * Status update posts published by the person themselves.
         */
        STATUSES(GraphPath.STATUSES),
        /**
         * Posts in which the person is tagged.
         */
        TAGGED(GraphPath.TAGGED);

        private String graphPath;

        PostType(String graphPath) {
            this.graphPath = graphPath;
        }

        public String getGraphPath() {
            return graphPath;
        }
    }

    private static final String ACTIONS = "actions";
    private static final String APPLICATION = "application";
    private static final String ATTACHMENTS = "attachments";
    private static final String CAPTION = "caption";
    private static final String COMMENTS = "comments";
    private static final String LIKES = "likes";
    private static final String CREATED_TIME = "created_time";
    private static final String DESCRIPTION = "description";
    private static final String FROM = "from";
    private static final String ICON = "icon";
    private static final String ID = "id";
    private static final String IS_HIDDEN = "is_hidden";
    private static final String LINK = "link";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_TAGS = "message_tags";
    private static final String NAME = "name";
    private static final String OBJECT_ID = "object_id";
    private static final String PICTURE = "picture";
    private static final String PLACE = "place";
    // private static final String PRIVACY = "privacy";
    private static final String PROPERTIES = "properties";
    private static final String SHARES = "shares";
    private static final String SOURCE = "source";
    private static final String STATUS_TYPE = "status_type";
    private static final String STORY = "story";
    private static final String STORY_TAGS = "story_tags";
    private static final String TO = "to";
    private static final String TYPE = "type";
    private static final String UPDATED_TIME = "updated_time";
    private static final String WITH_TAGS = "with_tags";

    @SerializedName(ACTIONS)
    private List<Action> mActions;

    @SerializedName(APPLICATION)
    private Application mApplication;

    @SerializedName(ATTACHMENTS)
    private Attachment mAttachment;

    @SerializedName(CAPTION)
    private String mCaption;

    @SerializedName(COMMENTS)
    private Utils.DataResult<Comment> mComments;

    @SerializedName(LIKES)
    private Utils.DataResult<Like> mLikes;

    @SerializedName(CREATED_TIME)
    private Date mCreatedTime;

    @SerializedName(DESCRIPTION)
    private String mDescription;

    @SerializedName(FROM)
    private User mFrom;

    @SerializedName(ICON)
    private String mIcon;

    @SerializedName(ID)
    private String mId;

    @SerializedName(IS_HIDDEN)
    private Boolean mIsHidden;

    @SerializedName(LINK)
    private String mLink;

    @SerializedName(MESSAGE)
    private String mMessage;

    @SerializedName(MESSAGE_TAGS)
    private Map<String, List<InlineTag>> mMessageTags;

    @SerializedName(NAME)
    private String mName;

    @SerializedName(OBJECT_ID)
    private String mObjectId;

    @SerializedName(PICTURE)
    private String mPicture;

    @SerializedName(PLACE)
    private Place mPlace;

//    @SerializedName(PRIVACY)
//    private Privacy mPrivacy;

    @SerializedName(PROPERTIES)
    private List<Property> mProperties;

    @SerializedName(SHARES)
    private Shares mShares;

    @SerializedName(SOURCE)
    private String mSource;

    @SerializedName(STATUS_TYPE)
    private String mStatusType;

    @SerializedName(STORY)
    private String mStory;

    @SerializedName(STORY_TAGS)
    private Map<String, List<InlineTag>> mStoryTags;

    @SerializedName(TO)
    private Utils.DataResult<User> mTo;

    @SerializedName(TYPE)
    private String mType;

    @SerializedName(UPDATED_TIME)
    private Date mUpdatedTime;

    @SerializedName(WITH_TAGS)
    private Utils.DataResult<User> mWithTags;

    private static class Shares {
        Integer count;
    }

    /**
     * A list of available actions on the post (including commenting, liking,
     * and an optional app-specified action).
     */
    public List<Action> getActions() {
        return mActions;
    }

    /**
     * Information about the application this post came from.
     */
    public Application getApplication() {
        return mApplication;
    }

    /**
     * Media content associated with a story or comment.
     */
    public Attachment getAttachment() {
        return mAttachment;
    }

    /**
     * The caption of the link (appears beneath the link name).
     */
    public String getCaption() {
        return mCaption;
    }

    /**
     * Comments for this post.
     */
    public List<Comment> getComments() {
        return mComments.data;
    }

    /**
     * Likes of this post
     */
    public List<Like> getLikes() {
        return mLikes.data;
    }

    /**
     * The time the post was initially published.
     */
    public Date getCreatedTime() {
        return mCreatedTime;
    }

    /**
     * A description of the link (appears beneath the link caption).
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Information about the user who posted the message.
     */
    public User getFrom() {
        return mFrom;
    }

    /**
     * A link to an icon representing the type of this post.
     */
    public String getIcon() {
        return mIcon;
    }

    /**
     * The post ID.
     */
    public String getId() {
        return mId;
    }

    /**
     * If this post is marked as hidden (applies to posts on Page Timelines
     * only).
     */
    public Boolean getIsHidden() {
        return mIsHidden;
    }

    /**
     * The link attached to this post.
     */
    public String getLink() {
        return mLink;
    }

    /**
     * The message.
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * Objects tagged in the message (Users, Pages, etc).
     */
    public List<InlineTag> getMessageTags() {
        List<InlineTag> inlineTags = new ArrayList<InlineTag>();
        for (Map.Entry<String, List<InlineTag>> entry : mMessageTags.entrySet()) {
            inlineTags.addAll(entry.getValue());
        }
        return inlineTags;
    }

    /**
     * The name of the link.
     */
    public String getName() {
        return mName;
    }

    /**
     * The Facebook object id for an uploaded photo or video.
     */
    public String getObjectId() {
        return mObjectId;
    }

    /**
     * If available, a link to the picture included with this post.
     */
    public String getPicture() {
        return mPicture;
    }

    /**
     * Location associated with a Post, if any.
     */
    public Place getPlace() {
        return mPlace;
    }

    /**
     * The privacy settings of the Post.
     */
    // public Privacy getPrivacy() {
    // return mPrivacy;
    // }

    /**
     * A list of properties for an uploaded video, for example, the length of
     * the video.
     */
    public List<Property> getProperties() {
        return mProperties;
    }

    /**
     * The number of times this post has been shared.
     */
    public Integer getShares() {
        return mShares != null ? mShares.count : 0;
    }

    /**
     * A URL to a Flash movie or video file to be embedded within the post.
     */
    public String getSource() {
        return mSource;
    }

    /**
     * Type of post. One of:
     * <ul>
     * <li>mobile_status_update</li>
     * <li>created_note</li>
     * <li>added_photos</li>
     * <li>added_video</li>
     * <li>shared_story</li>
     * <li>created_group</li>
     * <li>created_event</li>
     * <li>wall_post</li>
     * <li>app_created_story</li>
     * <li>published_story</li>
     * <li>tagged_in_photo</li>
     * <li>approved_friend</li>
     * </ul>
     */
    public String getStatusType() {
        return mStatusType;
    }

    /**
     * Text of stories not intentionally generated by users, such as those
     * generated when two users become friends.
     */
    public String getStory() {
        return mStory;
    }

    /**
     * Objects (Users, Pages, etc) tagged in a non-intentional story.
     */
    public List<InlineTag> getStoryTags() {
        List<InlineTag> inlineTags = new ArrayList<InlineTag>();
        for (Map.Entry<String, List<InlineTag>> entry : mStoryTags.entrySet()) {
            inlineTags.addAll(entry.getValue());
        }
        return inlineTags;
    }

    /**
     * Profiles mentioned or targeted in this post.
     */
    public List<User> getTo() {
        return mTo.data;
    }

    /**
     * A string indicating the type for this post (including link, photo,
     * video).
     */
    public String getType() {
        return mType;
    }

    /**
     * The time of the last comment on this post.
     */
    public Date getUpdatedTime() {
        return mUpdatedTime;
    }

    /**
     * Objects (Users, Pages, etc) tagged as being with the publisher of the
     * post ('Who are you with?' on Facebook).
     */
    public List<User> getWithTags() {
        return mWithTags.data;
    }

    public static class Action  {

        private static final String NAME = "name";
        private static final String LINK = "link";

        @SerializedName(NAME)
        private String mName;

        @SerializedName(LINK)
        private String mLink;

        public String getName() {
            return mName;
        }

        public String getLink() {
            return mLink;
        }
    }

    public static class Property {

        private static final String NAME = "name";
        private static final String TEXT = "text";

        @SerializedName(NAME)
        private String mName;

        @SerializedName(TEXT)
        private String mText;

        public String getName() {
            return mName;
        }

        public String getText() {
            return mText;
        }
    }

    public static class InlineTag {

        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String OFFSET = "offset";
        private static final String LENGTH = "length";
        private static final String TYPE = "type";

        @SerializedName(ID)
        private String mId;

        @SerializedName(NAME)
        private String mName;

        @SerializedName(OFFSET)
        private Integer mOffset;

        @SerializedName(LENGTH)
        private Integer mLength;

        @SerializedName(TYPE)
        private String mType;

        public String getId() {
            return mId;
        }

        public String getName() {
            return mName;
        }

        public Integer getOffset() {
            return mOffset;
        }

        public Integer getLength() {
            return mLength;
        }

        public String getType() {
            return mType;
        }
    }

}
