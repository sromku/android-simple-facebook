package com.sromku.simple.fb.entities;

import java.util.List;

import android.util.Pair;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.Converter;

/**
 * An individual entry in a profile's feed as represented in the Graph API.
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/api/post
 */
public class Post {

	public static enum PostType {
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

		private PostType(String graphPath) {
			this.graphPath = graphPath;
		}

		public String getGraphPath() {
			return graphPath;
		}
	}

	private static final String ACTIONS = "actions";
	private static final String APPLICATION = "application";
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

	private List<Action> mActions;
	private Application mApplication;
	private String mCaption;
	private List<Comment> mComments;
	private List<Like> mLikes;
	private Long mCreatedTime;
	private String mDescription;
	private User mFrom;
	private String mIcon;
	private String mId;
	private Boolean mIsHidden;
	private String mLink;
	private String mMessage;
	private List<InlineTag> mMessageTags;
	private String mName;
	private String mObjectId;
	private String mPicture;
	private Place mPlace;
	// private Privacy mPrivacy;
	private List<Property> mProperties;
	private Integer mShares;
	private String mSource;
	private String mStatusType;
	private String mStory;
	private List<InlineTag> mStoryTags;
	private List<User> mTo;
	private String mType;
	private Long mUpdatedTime;
	private List<User> mWithTags;

	private Post(GraphObject graphObject) {

		// actions
		mActions = Utils.createList(graphObject, ACTIONS, new Converter<Action>() {
			@Override
			public Action convert(GraphObject graphObject) {
				String name = Utils.getPropertyString(graphObject, NAME);
				String link = Utils.getPropertyString(graphObject, LINK);
				return new Action(name, link);
			}
		});

		// application
		mApplication = Application.create(Utils.getPropertyGraphObject(graphObject, APPLICATION));

		// caption
		mCaption = Utils.getPropertyString(graphObject, CAPTION);

		// comments
		mComments = Utils.createList(graphObject, COMMENTS, "data", new Converter<Comment>() {
			@Override
			public Comment convert(GraphObject graphObject) {
				return Comment.create(graphObject);
			}
		});

		// likes
		mLikes = Utils.createList(graphObject, LIKES, "data", new Converter<Like>() {
			@Override
			public Like convert(GraphObject graphObject) {
				return Like.create(graphObject);
			}
		});

		// created time
		mCreatedTime = Utils.getPropertyLong(graphObject, CREATED_TIME);

		// description
		mDescription = Utils.getPropertyString(graphObject, DESCRIPTION);

		// from
		mFrom = Utils.createUser(graphObject, FROM);

		// icon
		mIcon = Utils.getPropertyString(graphObject, ICON);

		// id
		mId = Utils.getPropertyString(graphObject, ID);

		// is hidden
		mIsHidden = Utils.getPropertyBoolean(graphObject, IS_HIDDEN);

		// link
		mLink = Utils.getPropertyString(graphObject, LINK);

		// message
		mMessage = Utils.getPropertyString(graphObject, MESSAGE);

		// message tags
		mMessageTags = Utils.createListAggregateValues(graphObject, MESSAGE_TAGS, new Converter<InlineTag>() {
			@Override
			public InlineTag convert(GraphObject graphObject) {
				return InlineTag.create(graphObject);
			}
		});

		// name
		mName = Utils.getPropertyString(graphObject, NAME);

		// object id
		mObjectId = Utils.getPropertyString(graphObject, OBJECT_ID);

		// picture
		mPicture = Utils.getPropertyString(graphObject, PICTURE);

		// place
		mPlace = Place.create(Utils.getPropertyGraphObject(graphObject, PLACE));

		// privacy
		// mPrivacy = Privacy.create(Utils.getPropertyGraphObject(graphObject,
		// PRIVACY));

		// properties
		mProperties = Utils.createList(graphObject, PROPERTIES, new Converter<Property>() {
			@Override
			public Property convert(GraphObject graphObject) {
				String name = Utils.getPropertyString(graphObject, NAME);
				String text = Utils.getPropertyString(graphObject, "text");
				return new Property(name, text);
			}
		});

		// shares
		mShares = Utils.getPropertyInteger(graphObject, SHARES);

		// source
		mSource = Utils.getPropertyString(graphObject, SOURCE);

		// status_type
		mStatusType = Utils.getPropertyString(graphObject, STATUS_TYPE);

		// story
		mStory = Utils.getPropertyString(graphObject, STORY);

		// story tags
		mStoryTags = Utils.createListAggregateValues(graphObject, STORY_TAGS, new Converter<InlineTag>() {
			@Override
			public InlineTag convert(GraphObject graphObject) {
				return InlineTag.create(graphObject);
			}
		});

		// to
		mTo = Utils.createList(graphObject, TO, "data", new Converter<User>() {
			@Override
			public User convert(GraphObject graphObject) {
				return null;
			}
		});

		// type
		mType = Utils.getPropertyString(graphObject, TYPE);

		// updated time
		mUpdatedTime = Utils.getPropertyLong(graphObject, UPDATED_TIME);

		// with tags
		mWithTags = Utils.createList(graphObject, WITH_TAGS, "data", new Converter<User>() {
			@Override
			public User convert(GraphObject graphObject) {
				return Utils.createUser(graphObject);
			}
		});
	}

	public static Post create(GraphObject graphObject) {
		return new Post(graphObject);
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
	 * The caption of the link (appears beneath the link name).
	 */
	public String getCaption() {
		return mCaption;
	}

	/**
	 * Comments for this post.
	 */
	public List<Comment> getComments() {
		return mComments;
	}

	/**
	 * Likes of this post
	 */
	public List<Like> getLikes() {
		return mLikes;
	}

	/**
	 * The time the post was initially published.
	 */
	public Long getCreatedTime() {
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
		return mMessageTags;
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
		return mShares;
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
		return mStoryTags;
	}

	/**
	 * Profiles mentioned or targeted in this post.
	 */
	public List<User> getTo() {
		return mTo;
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
	public Long getUpdatedTime() {
		return mUpdatedTime;
	}

	/**
	 * Objects (Users, Pages, etc) tagged as being with the publisher of the
	 * post ('Who are you with?' on Facebook).
	 */
	public List<User> getWithTags() {
		return mWithTags;
	}

	public static class Action extends Pair<String, String> {

		public Action(String name, String link) {
			super(name, link);
		}

		public String getName() {
			return first;
		}

		public String getLink() {
			return second;
		}
	}

	public static class Property extends Pair<String, String> {

		public Property(String name, String text) {
			super(name, text);
		}

		public String getName() {
			return first;
		}

		public String getText() {
			return second;
		}
	}

	public static class InlineTag {

		private String mId;
		private String mName;
		private Integer mOffset;
		private Integer mLength;
		private String mType;

		private InlineTag(GraphObject graphObject) {

			// id
			mId = Utils.getPropertyString(graphObject, ID);

			// name
			mName = Utils.getPropertyString(graphObject, NAME);

			// offset
			mOffset = Utils.getPropertyInteger(graphObject, "offset");

			// length
			mLength = Utils.getPropertyInteger(graphObject, "length");

			// type
			mType = Utils.getPropertyString(graphObject, "type");
		}

		public static InlineTag create(GraphObject graphObject) {
			return new InlineTag(graphObject);
		}

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
