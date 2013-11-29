package com.sromku.simple.fb;

import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * https://developers.facebook.com/docs/reference/api/privacy-parameter/
 *
 * @author ronlut
 */
public class Privacy {
    private final String PRIVACY = "value";
    private final String ALLOW = "allow";
    private final String DENY = "deny";

    private PrivacySettings mPrivacySetting = null;
    private ArrayList<String> allowedUsers = new ArrayList<String>();
    private ArrayList<String> deniedUsers = new ArrayList<String>();

    /**
     * <b>Important:</b> If you choose "CUSTOM", you must call either<br/>
     * addAllowedUserOrListID(s) or addDeniedUserOrListID(s).
     *
     * @param privacySettings The wanted privacy settings
     */
    public Privacy(PrivacySettings privacySettings) {
        mPrivacySetting = privacySettings;
    }

    private void ValidateListsAccessRequest() {
        if (mPrivacySetting != PrivacySettings.CUSTOM)
            throw new UnsupportedOperationException("Can't add / delete from allowed / denied lists when privacy setting is different than \"CUSTOM\"");
    }

    public void addAllowedUserOrListID(String userOrListID) {

        ValidateListsAccessRequest();
        allowedUsers.add(userOrListID);
    }

    public void addAllowedUserOrListIDs(Collection<? extends String> userOrListIDs) {
        ValidateListsAccessRequest();
        allowedUsers.addAll(userOrListIDs);
    }

    public void addDeniedUserOrListID(String userOrListID) {
        ValidateListsAccessRequest();
        deniedUsers.add(userOrListID);
    }

    public void addDeniedUserOrListIDs(Collection<? extends String> userOrListIDs) {
        ValidateListsAccessRequest();
        deniedUsers.addAll(userOrListIDs);
    }

    public static enum PrivacySettings {
        EVERYONE,
        ALL_FRIENDS,
        FRIENDS_OF_FRIENDS,
        SELF,
        CUSTOM
    }

    public String getJSONString() {
        JSONObject jsonRepresentation = new JSONObject();
        try {
            jsonRepresentation.put(PRIVACY, mPrivacySetting.name());
            if (mPrivacySetting == PrivacySettings.CUSTOM) {
                if (!allowedUsers.isEmpty()) {
                    jsonRepresentation.put(ALLOW, Utils.join(allowedUsers.iterator(), ','));
                }

                if (!deniedUsers.isEmpty()) {
                    jsonRepresentation.put(DENY, Utils.join(deniedUsers.iterator(), ','));
                }
            }
        } catch (JSONException e) {
            Logger.logError(Privacy.class, "Failed to get json string from properties", e);
        }

        return jsonRepresentation.toString();
    }
}