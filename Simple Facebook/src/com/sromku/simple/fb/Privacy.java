package com.sromku.simple.fb;

import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class that helps control the audience on Facebook that can see a post made by an app on behalf of a user.
 * Reference: https://developers.facebook.com/docs/reference/api/privacy-parameter/
 *
 * @author ronlut
 */
public class Privacy
{
    private final String PRIVACY = "value";
    private final String ALLOW = "allow";
    private final String DENY = "deny";

    private PrivacySettings mPrivacySetting = null;
    private ArrayList<String> allowedUsers = new ArrayList<String>();
    private ArrayList<String> deniedUsers = new ArrayList<String>();

    public static enum PrivacySettings
    {
        EVERYONE,
        ALL_FRIENDS,
        FRIENDS_OF_FRIENDS,
        SELF,
        CUSTOM
    }

    /**
     * The privacy parameter only applies for posts to the user's own timeline<br/>
     * and is ultimately governed by the privacy ceiling a user has configured for an app.<br/>
     * <b>Important:</b> If you choose "CUSTOM", you must call either<br/>
     * addAllowedUserOrListID(s) or addDeniedUserOrListID(s).
     *
     * @param privacySettings The wanted privacy settings
     */
    public Privacy(PrivacySettings privacySettings)
    {
        mPrivacySetting = privacySettings;
    }

    /**
     * Validates that the allowed / denied lists can be accessed.<br/>
     * In case you use a predefined privacy setting different than {@code CUSTOM}, you <b>must not</b> use the custom lists.
     */
    private void ValidateListsAccessRequest()
    {
        if (mPrivacySetting != PrivacySettings.CUSTOM)
            throw new UnsupportedOperationException("Can't add / delete from allowed / denied lists when privacy setting is different than \"CUSTOM\"");
    }

    /**
     * Add a user or a friend list to the allowed list
     * @param userOrListID user ID or friend list ID that "can" see the post
     */
    public void addAllowedUserOrListID(String userOrListID)
    {
        ValidateListsAccessRequest();
        allowedUsers.add(userOrListID);
    }

    /**
     * Add a predefined friend list to the allowed list
     * This can <b>only</b> be ALL_FRIENDS or FRIENDS_OF_FRIENDS to include all members of those sets.
     * @param friendList ALL_FRIENDS or FRIENDS_OF_FRIENDS to include all members of those sets
     */
    public void addallowedUserOrListID(PrivacySettings friendList)
    {
        ValidateListsAccessRequest();
        if (friendList != PrivacySettings.ALL_FRIENDS || friendList != PrivacySettings.FRIENDS_OF_FRIENDS)
            throw new UnsupportedOperationException("Can't add this predefined friend list. Only allowed are: ALL_FRIENDS or FRIENDS_OF_FRIENDS");

        allowedUsers.add(friendList.name());
    }

    /**
     * Add users and/or friend lists to the allowed list.
     * @param userOrListIDs mixed user IDs and friend list IDs that "can" see the post
     */
    public void addAllowedUserOrListIDs(Collection<? extends String> userOrListIDs)
    {
        ValidateListsAccessRequest();
        allowedUsers.addAll(userOrListIDs);
    }

    /**
     * Add a user or a friend list to the denied list
     * @param userOrListID user ID or friend list ID that "cannot" see the post
     */
    public void addDeniedUserOrListID(String userOrListID)
    {
        ValidateListsAccessRequest();
        deniedUsers.add(userOrListID);
    }

    /**
     * Add users and/or friend lists to the denied list
     * @param userOrListIDs mixed user IDs and friend list IDs that "can" see the post
     */
    public void addDeniedUserOrListIDs(Collection<? extends String> userOrListIDs)
    {
        ValidateListsAccessRequest();
        deniedUsers.addAll(userOrListIDs);
    }

    /**
     * Returns the {@code JSON} representation that should be used as the value of the privacy parameter<br/>
     * in the entities that have privacy settings.
     * @return A {@code String} representing the value of the privacy parameter
     */
    public String getJSONString()
    {
        JSONObject jsonRepresentation = new JSONObject();
        try
        {
            jsonRepresentation.put(PRIVACY, mPrivacySetting.name());
            if (mPrivacySetting == PrivacySettings.CUSTOM)
            {
                if (!allowedUsers.isEmpty())
                {
                    jsonRepresentation.put(ALLOW, Utils.join(allowedUsers.iterator(), ','));
                }

                if (!deniedUsers.isEmpty())
                {
                    jsonRepresentation.put(DENY, Utils.join(deniedUsers.iterator(), ','));
                }
            }
        }
        catch (JSONException e)
        {
            Logger.logError(Privacy.class, "Failed to get json string from properties", e);
        }

        return jsonRepresentation.toString();
    }
}