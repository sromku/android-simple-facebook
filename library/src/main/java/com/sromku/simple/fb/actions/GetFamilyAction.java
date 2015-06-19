package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.FamilyUser;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

/**
 * @author sromku
 * // @see https://developers.facebook.com/docs/graph-api/reference/user/family/
 */
public class GetFamilyAction extends GetAction<List<FamilyUser>> {

    public GetFamilyAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
        return getTarget() + "/" + GraphPath.FAMILY;
    }

    @Override
    protected List<FamilyUser> processResponse(GraphResponse response) {
        Utils.DataResult<FamilyUser> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<FamilyUser>>() {
        }.getType());
        return dataResult.data;
    }
}
