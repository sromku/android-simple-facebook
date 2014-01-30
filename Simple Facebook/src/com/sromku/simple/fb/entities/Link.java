package com.sromku.simple.fb.entities;

import java.util.List;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.Converter;

public class Link {

    public static final String COMMENTS = "comments";
    public static final String LIKES = "likes";
    public static final String CREATED_TIME = "created_time";
    public static final String DESCRIPTION = "description";
    public static final String FROM = "from";
    public static final String ICON = "icon";
    public static final String ID = "id";
    public static final String LINK = "link";
    public static final String MESSAGE = "messages";
    public static final String NAME = "name";
    public static final String PICTURE = "picture";
    
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
    
    public Long getCreatedTime() {
	return mCreateTime;
    }
    
    public String getDescription() {
	return mDescription;
    }
    
    public User getFrom() {
	return mFrom;
    }
    
    public String getIcon() {
	return mIcon;
    }
    
    public String getId() {
	return mId;
    }
    
    public String getLink() {
	return mLink;
    }
    
    public String getMessage() {
	return mMessage;
    }
    
    public String getName() {
	return mName;
    }
    
    public String getPicture() {
	return mPicture;
    }
    
}
