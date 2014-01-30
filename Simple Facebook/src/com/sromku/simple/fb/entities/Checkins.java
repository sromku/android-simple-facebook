package com.sromku.simple.fb.entities;

import java.util.List;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.Converter;

public class Checkins {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String APPLICATION = "application";
    public static final String COMMENTS = "comments";
    public static final String CREATED_TIME = "created_time";
    public static final String FROM = "from";
    public static final String LIKES = "likes";
    public static final String MESSAGE = "message";
    public static final String PLACE = "place";
    public static final String TAGS = "tags";
    public static final String TYPE = "type";

    private final GraphObject mGraphObject;

    private Application mApplication;
    private List<Comment> mComments;
    private Long mCreatedTime;
    private User mFrom;
    private String mId;
    private List<Like> mLikes;
    private String mMessage;
    private Place mPlace;
    private List<Tag> mTags;

    private Checkins(GraphObject graphObject) {
	mGraphObject = graphObject;

	if (mGraphObject != null) {

	    // create application
	    GraphObject applicationGraphObject = graphObject.getPropertyAs(APPLICATION, GraphObject.class);
	    mApplication = Application.create(applicationGraphObject);

	    // create comments
	    Utils.createList(mGraphObject, COMMENTS, new Converter<Comment>() {
		@Override
		public Comment convert(GraphObject graphObject) {
		    return Comment.create(graphObject);
		}
	    });

	    // created time
	    mCreatedTime = Long.valueOf(String.valueOf(mGraphObject.getProperty(CREATED_TIME)));

	    // from
	    mFrom = Utils.createUser(graphObject, FROM);

	    // id
	    mId = String.valueOf(mGraphObject.getProperty(ID));

	    // create likes
	    Utils.createList(mGraphObject, LIKES, new Converter<Like>() {
		@Override
		public Like convert(GraphObject graphObject) {
		    return Like.create(graphObject);
		}
	    });

	    // message
	    mMessage = String.valueOf(mGraphObject.getProperty(MESSAGE));

	    // place
	    GraphObject graphObjectPlace = graphObject.getPropertyAs(PLACE, GraphObject.class);
	    if (graphObjectPlace != null) {
		mPlace = Place.create(graphObjectPlace);
	    }

	    // create tags
	    Utils.createList(mGraphObject, TAGS, new Converter<Tag>() {
		@Override
		public Tag convert(GraphObject graphObject) {
		    return Tag.create(graphObject);
		}
	    });

	}
    }

    public static Checkins create(GraphObject graphObject) {
	return new Checkins(graphObject);
    }

    public Application getApplication() {
	return mApplication;
    }
    
    public List<Comment> getComments() {
	return mComments;
    }
    
    public Long getCreatedTime() {
	return mCreatedTime;
    }
    
    public User getFrom() {
	return mFrom;
    }
    
    public String getId() {
	return mId;
    }
    
    public List<Like> getLikes() {
	return mLikes;
    }
    
    public String getMessage() {
	return mMessage;
    }

    public Place getPlace() {
	return mPlace;
    }

    public List<Tag> getTags() {
	return mTags;
    }
    
}
