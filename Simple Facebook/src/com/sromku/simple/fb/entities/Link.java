package com.sromku.simple.fb.entities;

import java.util.List;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.Converter;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/api/link
 */
public class Link {

    private static final String COMMENTS = "comments";
    private static final String LIKES = "likes";
    private static final String CREATED_TIME = "created_time";
    private static final String DESCRIPTION = "description";
    private static final String FROM = "from";
    private static final String ICON = "icon";
    private static final String ID = "id";
    private static final String LINK = "link";
    private static final String MESSAGE = "messages";
    private static final String NAME = "name";
    private static final String PICTURE = "picture";
    
    private List<Comment> mComments;
    private List<Like> mLikes;
    private Long mCreateTime;
    private String mDescription;
    private User mFrom;
    private String mIcon;
    private String mId;
    private String mLink;
    private String mMessage;
    private String mName;
    private String mPicture;

    private Link(GraphObject graphObject) {
	
	// comments
	mComments = Utils.createList(graphObject, COMMENTS, new Converter<Comment>() {
	    @Override
	    public Comment convert(GraphObject graphObject) {
		return Comment.create(graphObject);
	    }
	});
	
	// likes
	mLikes = Utils.createList(graphObject, LIKES, new Converter<Like>() {
	    @Override
	    public Like convert(GraphObject graphObject) {
		return Like.create(graphObject);
	    }
	});
	
	// create time
	mCreateTime = Utils.getPropertyLong(graphObject, CREATED_TIME);
	
	// description
	mDescription = Utils.getPropertyString(graphObject, DESCRIPTION);
	
	// from
	mFrom = Utils.createUser(graphObject, FROM);
	
	// icon
	mIcon = Utils.getPropertyString(graphObject, ICON);
	
	// id
	mId = Utils.getPropertyString(graphObject, ID);
	
	// link
	mLink = Utils.getPropertyString(graphObject, LINK);
	
	// message
	mMessage = Utils.getPropertyString(graphObject, MESSAGE);
	
	// name
	mName = Utils.getPropertyString(graphObject, NAME);
	
	// picture
	mPicture = Utils.getPropertyString(graphObject, PICTURE);
    }
    
    public static Link create(GraphObject graphObject) {
	return new Link(graphObject);
    }
    
    public List<Comment> getComments() {
	return mComments;
    }
    
    public List<Like> getLikes() {
	return mLikes;
    }
    
    /**
     * The time the message was published.
     */
    public Long getCreatedTime() {
	return mCreateTime;
    }
    
    /**
     * A description of the link (appears beneath the link caption).
     */
    public String getDescription() {
	return mDescription;
    }
    
    /**
     * The user that created the link.
     */
    public User getFrom() {
	return mFrom;
    }
    
    /**
     * A URL to the link icon that Facebook displays in the news feed.
     */
    public String getIcon() {
	return mIcon;
    }
    
    /**
     * The link Id.
     */
    public String getId() {
	return mId;
    }
    
    /**
     * The URL that was shared.
     */
    public String getLink() {
	return mLink;
    }
    
    /**
     * The optional message from the user about this link.
     */
    public String getMessage() {
	return mMessage;
    }
    
    /**
     * The name of the link.
     */
    public String getName() {
	return mName;
    }
    
    /**
     * A URL to the thumbnail image used in the link post.
     */
    public String getPicture() {
	return mPicture;
    }
    
}
