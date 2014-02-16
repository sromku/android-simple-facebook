package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetPhotosAction extends GetAction<List<Photo>> {

    public GetPhotosAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
	return getTarget() + "/" + GraphPath.PHOTOS;
    }

    @Override
    protected Bundle getBundle() {
	Bundle bundle = new Bundle();
	bundle.putString("date_format", "U");
	return bundle;
    }

    @Override
    protected List<Photo> processResponse(Response response) throws JSONException {
	List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
	List<Photo> photos = new ArrayList<Photo>(graphObjects.size());
	for (GraphObject graphObject : graphObjects) {
	    Photo photo = Photo.create(graphObject);
	    photos.add(photo);
	}
	return photos;
    }

}
