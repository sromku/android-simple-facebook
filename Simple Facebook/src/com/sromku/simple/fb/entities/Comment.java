package com.sromku.simple.fb.entities;

import java.util.List;

import android.os.Bundle;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.Converter;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/comment
 */
public class Comment implements Publishable {

	private static final String ID = "id";
	private static final String CAN_COMMENT = "can_comment";
	private static final String CAN_REMOVE = "can_remove";
	private static final String COMMENT_COUNT = "comment_count";
	private static final String CREATED_TIME = "created_time";
	private static final String FROM = "from";
	private static final String LIKE_COUNT = "like_count";
	private static final String MESSAGE = "message";
	private static final String MESSAGE_TAGS = "message_tags";
	private static final String PARENT = "parent";
	private static final String USER_LIKES = "user_likes";
	private static final String ATTACHMENT = "attachment";
	private static final String ATTACHMENT_URL = "attachment_url";

	private String mId;
	private Attachment mAttachment;
	private Boolean mCanComment;
	private Boolean mCanRemove;
	private Integer mCommentCount;
	private Long mCreatedTime;
	private User mFrom;
	private Integer mLikeCount;
	private String mMessage;
	private List<String> mMessageTags;
	private Comment mParent;
	private Boolean mUserLikes;
	private String mAttachmentUrl;

	private Comment(GraphObject graphObject) {
		// id
		mId = Utils.getPropertyString(graphObject, ID);

		// attachment
		mAttachment = Attachment.create(Utils.getPropertyGraphObject(graphObject, ATTACHMENT));

		// can comment
		mCanComment = Utils.getPropertyBoolean(graphObject, CAN_COMMENT);

		// can remove
		mCanRemove = Utils.getPropertyBoolean(graphObject, CAN_REMOVE);

		// comment count
		mCommentCount = Utils.getPropertyInteger(graphObject, COMMENT_COUNT);

		// created time
		mCreatedTime = Utils.getPropertyLong(graphObject, CREATED_TIME);

		// from
		mFrom = Utils.createUser(graphObject, FROM);

		// like count
		mLikeCount = Utils.getPropertyInteger(graphObject, LIKE_COUNT);

		// message
		mMessage = Utils.getPropertyString(graphObject, MESSAGE);

		// message tags
		mMessageTags = Utils.createList(graphObject, MESSAGE_TAGS, new Converter<String>() {
			@Override
			public String convert(GraphObject graphObject) {
				return graphObject.toString();
			}
		});

		// parent
		GraphObject commentGraph = Utils.getPropertyGraphObject(graphObject, PARENT);
		if (commentGraph != null) {
			mParent = Comment.create(commentGraph);
		}

		// user likes
		mUserLikes = Utils.getPropertyBoolean(graphObject, USER_LIKES);
	}

	private Comment(Builder builder) {
		mMessage = builder.mMessage;
		mAttachmentUrl = builder.mAttachmentUrl;
	}

	public static Comment create(GraphObject graphObject) {
		return new Comment(graphObject);
	}

	@Override
	public Bundle getBundle() {
		Bundle bundle = new Bundle();

		// add name
		if (mMessage != null) {
			bundle.putString(MESSAGE, mMessage);
		}

		// add attachment
		if (mAttachmentUrl != null) {
			bundle.putString(ATTACHMENT_URL, mAttachmentUrl);
		}
		
		return bundle;
	}

	@Override
	public String getPath() {
		return GraphPath.COMMENTS;
	}

	@Override
	public Permission getPermission() {
		return Permission.PUBLISH_ACTION;
	}

	/**
	 * The comment Id.
	 */
	public String getId() {
		return mId;
	}

	/**
	 * Get attachments to this comment. Thay may include photos and links.
	 */
	public Attachment getAttachment() {
		return mAttachment;
	}

	/**
	 * Whether the viewer can reply to this comment.
	 */
	public Boolean getCanComment() {
		return mCanComment;
	}

	/**
	 * Whether the viewer can remove this comment.
	 */
	public Boolean getCanRemove() {
		return mCanRemove;
	}

	/**
	 * Number of replies to this comment.
	 */
	public Integer getCommentCount() {
		return mCommentCount;
	}

	/**
	 * The time this comment was made.
	 */
	public Long getCreatedTime() {
		return mCreatedTime;
	}

	/**
	 * The person that made this comment.
	 */
	public User getFrom() {
		return mFrom;
	}

	/**
	 * Number of times this comment was liked.
	 */
	public Integer getLikeCount() {
		return mLikeCount;
	}

	/**
	 * The comment text.
	 */
	public String getMessage() {
		return mMessage;
	}

	/**
	 * Profiles tagged in mentions in the message.
	 */
	public List<String> getMessageTags() {
		return mMessageTags;
	}

	/**
	 * For comment replies, this the comment that this is a reply to.
	 */
	public Comment getParent() {
		return mParent;
	}

	/**
	 * Whether the viewer has liked this comment.
	 */
	public Boolean getUserLikes() {
		return mUserLikes;
	}

	/**
	 * Builder for preparing the Comment object to be published.
	 */
	public static class Builder {
		private String mMessage = null;
		private String mAttachmentUrl = null;

		public Builder() {
		}

		/**
		 * Set the comment text
		 * 
		 * @param message
		 *            The text of the comment
		 */
		public Builder setMessage(String message) {
			mMessage = message;
			return this;
		}
		
		/**
		 * The URL of an image to include as a photo comment
		 * @param attachmentImageUrl The image url to be attached to comment
		 */
		public Builder setAttachmentImageUrl(String attachmentImageUrl) {
			mAttachmentUrl = attachmentImageUrl;
			return this;
		}

		public Comment build() {
			return new Comment(this);
		}
	}
}
