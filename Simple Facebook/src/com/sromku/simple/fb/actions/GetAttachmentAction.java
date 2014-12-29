package com.sromku.simple.fb.actions;

import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Attachment;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

/**
 * GetAttachmentAction.
 */
public class GetAttachmentAction extends GetAction<Attachment> {

	public GetAttachmentAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.ATTACHMENTS;
	}

	@Override
	protected Attachment processResponse(Response response) {
		Attachment attachment = null;
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		if (graphObjects != null && graphObjects.size() > 0) {
			attachment = Attachment.create(graphObjects.get(0));
		}
		return attachment;
	}
}
