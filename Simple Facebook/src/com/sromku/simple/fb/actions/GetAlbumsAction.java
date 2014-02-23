package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Album;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetAlbumsAction extends GetAction<List<Album>> {

	public GetAlbumsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.ALBUMS);
	}

	@Override
	protected Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putString("date_format", "U");
		return bundle;
	}

	@Override
	protected List<Album> processResponse(Response response) throws JSONException {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Album> albums = new ArrayList<Album>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Album album = Album.create(graphObject);
			albums.add(album);
		}
		return albums;
	}

}
