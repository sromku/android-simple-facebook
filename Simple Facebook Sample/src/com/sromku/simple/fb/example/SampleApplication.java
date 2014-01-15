package com.sromku.simple.fb.example;

import android.app.Application;

import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.utils.Logger;

public class SampleApplication extends Application {
    private static final String APP_ID = "625994234086470";
    private static final String APP_NAMESPACE = "sromkuapp";

    @Override
    public void onCreate() {
	super.onCreate();

	// set log to true
	Logger.DEBUG_WITH_STACKTRACE = true;

	// initialize facebook configuration
	Permission[] permissions = new Permission[] { 
		Permission.BASIC_INFO, 
	};

	SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
		.setAppId(APP_ID)
		.setNamespace(APP_NAMESPACE)
		.setPermissions(permissions)
		.setDefaultAudience(SessionDefaultAudience.FRIENDS)
		.setAskForAllPermissionsAtOnce(false)
		.build();

	SimpleFacebook.setConfiguration(configuration);
    }
}
