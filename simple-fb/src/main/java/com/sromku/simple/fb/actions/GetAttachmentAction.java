package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Attachment;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

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
    protected Attachment processResponse(GraphResponse response) {
        List<Attachment> attachments = Utils.typedListFromResponse(response);
        if (attachments != null && attachments.size() > 0) {
            return attachments.get(0);
        }
        return new Attachment();
    }
}
