package com.sromku.simple.fb.entities;

import java.util.List;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.Converter;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/comment
 */
public class Comment {

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

    // TODO
    @SuppressWarnings("unused")
    private static final String ATTACHMENT = "attachment";

    private String mId;
    private Boolean mCanComment;
    private Boolean mCanRemove;
    private Integer mCommentCount;
    private Long mCreatedTime;
    private String mFrom;
    private Integer mLikeCount;
    private String mMessage;
    private List<String> mMessageTags;
    private Comment mParent;
    private Boolean mUserLikes;

    private Comment(GraphObject graphObject) {
	// id
	mId = String.valueOf(graphObject.getProperty(ID));

	// can comment
	mCanComment = Boolean.valueOf(String.valueOf(graphObject.getProperty(CAN_COMMENT)));

	// can remove
	mCanRemove = Boolean.valueOf(String.valueOf(graphObject.getProperty(CAN_REMOVE)));

	// comment count
	mCommentCount = Integer.valueOf(String.valueOf(graphObject.getProperty(COMMENT_COUNT)));

	// created time
	mCreatedTime = Long.valueOf(String.valueOf(graphObject.getProperty(CREATED_TIME)));

	// from
	mFrom = String.valueOf(graphObject.getProperty(FROM));

	// like count
	mLikeCount = Integer.valueOf(String.valueOf(graphObject.getProperty(LIKE_COUNT)));

	// message
	mMessage = String.valueOf(graphObject.getProperty(MESSAGE));

	// message tags
	mMessageTags = Utils.createList(graphObject, MESSAGE_TAGS, new Converter<String>() {
	    @Override
	    public String convert(GraphObject graphObject) {
		return graphObject.toString();
	    }
	});

	// parent
	mParent = Comment.create(graphObject.getPropertyAs(PARENT, GraphObject.class));

	// user likes
	mUserLikes = Boolean.valueOf(String.valueOf(graphObject.getProperty(USER_LIKES)));
    }

    public static Comment create(GraphObject graphObject) {
	return new Comment(graphObject);
    }

    public String getId() {
	return mId;
    }

    public Boolean getCanComment() {
	return mCanComment;
    }

    public Boolean getCanRemove() {
	return mCanRemove;
    }

    public Integer getCommentCount() {
	return mCommentCount;
    }

    public Long getCreatedTime() {
	return mCreatedTime;
    }

    public String getFrom() {
	return mFrom;
    }

    public Integer getLikeCount() {
	return mLikeCount;
    }

    public String getMessage() {
	return mMessage;
    }

    public List<String> getMessageTags() {
	return mMessageTags;
    }

    public Comment getParent() {
	return mParent;
    }

    public Boolean getUserLikes() {
	return mUserLikes;
    }
}
