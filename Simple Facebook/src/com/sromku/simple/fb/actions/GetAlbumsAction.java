package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Album;
import com.sromku.simple.fb.listeners.OnAlbumsRequestListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;

public class GetAlbumsAction extends AbstractAction {

    private OnAlbumsRequestListener mOnAlbumsRequestListener;

    public GetAlbumsAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    public void setOnAlbumsRequestListener(OnAlbumsRequestListener onAlbumsRequestListener) {
	mOnAlbumsRequestListener = onAlbumsRequestListener;
    }

    @Override
    protected void executeImpl() {
	// if we are logged in
	if (sessionManager.isLogin()) {
	    // move these params to method call parameters
	    Session session = sessionManager.getOpenSession();
	    Bundle bundle = new Bundle();
	    bundle.putString("date_format", "U");
	    Request request = new Request(session, "me/albums", bundle, HttpMethod.GET, new Request.Callback() {
		@Override
		public void onCompleted(Response response) {
		    List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);

		    FacebookRequestError error = response.getError();
		    if (error != null) {
			Logger.logError(GetAlbumsAction.class, "failed to get albums", error.getException());

			// callback with 'exception'
			if (mOnAlbumsRequestListener != null) {
			    mOnAlbumsRequestListener.onException(error.getException());
			}
		    } else {
			// callback with 'complete'
			if (mOnAlbumsRequestListener != null) {
			    List<Album> albums = new ArrayList<Album>(graphObjects.size());
			    for (GraphObject graphObject : graphObjects) {
				Album album = Album.create(graphObject);
				albums.add(album);
			    }
			    mOnAlbumsRequestListener.onComplete(albums);
			}
		    }

		}
	    });

	    RequestAsyncTask task = new RequestAsyncTask(request);
	    task.execute();

	    // callback with 'thinking'
	    if (mOnAlbumsRequestListener != null) {
		mOnAlbumsRequestListener.onThinking();
	    }
	} else {
	    String reason = Errors.getError(ErrorMsg.LOGIN);
	    Logger.logError(GetAlbumsAction.class, reason, null);

	    // callback with 'fail' due to not being loged
	    if (mOnAlbumsRequestListener != null) {
		mOnAlbumsRequestListener.onFail(reason);
	    }
	}

    }

}
