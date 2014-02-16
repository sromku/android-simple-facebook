package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Comment;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetCommentsAction extends GetAction<List<Comment>> {

    public GetCommentsAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
	return getTarget() + "/" + GraphPath.COMMENTS;
    }

    @Override
    protected Bundle getBundle() {
	Bundle bundle = new Bundle();
	bundle.putString("date_format", "U");
	return bundle;
    }

    @Override
    protected List<Comment> processResponse(Response response) throws JSONException {
	List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
	List<Comment> comments = new ArrayList<Comment>(graphObjects.size());
	for (GraphObject graphObject : graphObjects) {
	    Comment comment = Comment.create(graphObject);
	    comments.add(comment);
	}
	return comments;
    }

}
