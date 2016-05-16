package com.sromku.simple.fb.example;

import android.app.Application;

import com.facebook.login.DefaultAudience;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.example.utils.SharedObjects;
import com.sromku.simple.fb.utils.Logger;

public class SampleApplication extends Application {
    private static final String FACEBOOK_APP_ID = "728615400528729";
    private static final String APP_NAMESPACE = "sromkuapp_vtwo";

    @Override
    public void onCreate() {
        super.onCreate();
        SharedObjects.context = this;

        // set log to true
        Logger.DEBUG_WITH_STACKTRACE = true;

        // initialize facebook configuration
        Permission[] permissions = new Permission[] {
                // Permission.PUBLIC_PROFILE,
                Permission.EMAIL,
                Permission.USER_EVENTS,
                Permission.USER_ACTIONS_MUSIC,
                Permission.USER_FRIENDS,
                Permission.USER_GAMES_ACTIVITY,
                Permission.USER_BIRTHDAY,
                Permission.USER_TAGGED_PLACES,
                Permission.USER_MANAGED_GROUPS,
                Permission.PUBLISH_ACTION };

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(FACEBOOK_APP_ID)
                .setNamespace(APP_NAMESPACE)
                .setPermissions(permissions)
                .setDefaultAudience(DefaultAudience.FRIENDS)
                .setAskForAllPermissionsAtOnce(false)
                // .setGraphVersion("v2.3")
                .build();

        SimpleFacebook.setConfiguration(configuration);
    }
}
