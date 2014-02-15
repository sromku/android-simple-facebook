package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Event;
import com.sromku.simple.fb.entities.Event.EventDesicion;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

/**
 * Get the events of the profile. <br>
 * <br>
 * The default retrieved events will be events that the user is attending. If
 * you want to get events that the user said 'maybe' or totally declined, then
 * you need to set the method: {@link #setEventDesicion(EventDesicion)}.
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/user/events/
 */
public class GetEventsAction extends GetAction<List<Event>> {

    private EventDesicion mEventDesicion = EventDesicion.ATTENDING; // default

    public GetEventsAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    /**
     * Get the events of the user based on his/her decision. You can not ask for
     * all events of the user and only then filter, you have to filter by
     * attendance of the user.
     * 
     * @param eventDesicion
     */
    public void setEventDesicion(EventDesicion eventDesicion) {
	mEventDesicion = eventDesicion;
    }

    @Override
    protected String getGraphPath() {
	// example path: {user-id}/events/attending
	return getTarget() + "/" + GraphPath.EVENTS + "/" + mEventDesicion.getGraphNode();
    }

    @Override
    protected Bundle getBundle() {
	Bundle bundle = new Bundle();
	bundle.putString("date_format", "U");
	return bundle;
    }

    @Override
    protected List<Event> processResponse(Response response) throws JSONException {
	List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
	List<Event> events = new ArrayList<Event>(graphObjects.size());
	for (GraphObject graphObject : graphObjects) {
	    Event event = Event.create(graphObject);
	    events.add(event);
	}
	return events;
    }

}
