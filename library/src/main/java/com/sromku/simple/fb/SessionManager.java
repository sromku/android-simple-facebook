package com.sromku.simple.fb;

import android.app.Activity;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.sromku.simple.fb.listeners.OnNewPermissionsListener;
import com.sromku.simple.fb.utils.Logger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SessionManager {

    private final static Class<?> TAG = SessionManager.class;
    static SimpleFacebookConfiguration configuration;

    private WeakReference<Activity> mActivity;
    private final LoginManager mLoginManager;
    private final LoginCallback mLoginCallback = new LoginCallback();
    private final CallbackManager mCallbackManager = CallbackManager.Factory.create();

    public class LoginCallback implements FacebookCallback<LoginResult> {

        public OnLoginListener loginListener;
        boolean doOnLogin = false;
        boolean askPublishPermissions = false;

        @Override
        public void onSuccess(LoginResult loginResult) {
            if (loginListener != null) {

                if (doOnLogin) {
                    doOnLogin = false;
                    loginListener.onLogin(loginResult);
                    return;
                }

                if (askPublishPermissions) {
                    doOnLogin = true;
                    askPublishPermissions = false;
                    requestPublishPermissions();
                } else {
                    loginListener.onLogin(loginResult);
                }

            }
        }

        @Override
        public void onCancel() {
            loginListener.onFail("User canceled the permissions dialog");
        }

        @Override
        public void onError(FacebookException e) {
            loginListener.onException(e);
        }
    }

	public SessionManager(SimpleFacebookConfiguration configuration) {
		SessionManager.configuration = configuration;
        mLoginManager = LoginManager.getInstance();
        mLoginManager.registerCallback(mCallbackManager, mLoginCallback);
        mLoginManager.setDefaultAudience(configuration.getDefaultAudience());
        mLoginManager.setLoginBehavior(configuration.getLoginBehavior());
	}

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

	/**
	 * Login to Facebook
	 * 
	 * @param onLoginListener
	 */
	public void login(OnLoginListener onLoginListener) {
		if (onLoginListener == null) {
			Logger.logError(TAG, "OnLoginListener can't be null in -> 'login(OnLoginListener onLoginListener)' method.");
			return;
		}

        if (isLogin()) {
			Logger.logInfo(TAG, "You were already logged in before calling 'login()' method.");
            LoginResult loginResult = createLastLoginResult();
            onLoginListener.onLogin(loginResult);
			return;
		}

		if (hasPendingRequest()) {
			Logger.logWarning(TAG, "You are trying to login one more time, before finishing the previous login call");
            onLoginListener.onFail("Already has pending login request");
			return;
		}

        // just do the login
        loginImpl(onLoginListener);
	}

    private void loginImpl(OnLoginListener onLoginListener) {

        // user hasn't the access token with all read acceptedPermissions we need, thus we ask him to login
        mLoginCallback.loginListener = onLoginListener;

        // in case of marking in configuration the option of getting publish permission, just after read permissions
        if (configuration.hasPublishPermissions() && configuration.isAllPermissionsAtOnce()) {
            mLoginCallback.askPublishPermissions = true;
        }

        // login please, with all read permissions
        requestReadPermissions();
    }

    public void requestReadPermissions() {
        mLoginManager.logInWithReadPermissions(mActivity.get(), configuration.getReadPermissions());
    }

    public void requestPublishPermissions() {
        mLoginManager.logInWithPublishPermissions(mActivity.get(), configuration.getPublishPermissions());
    }

    private LoginResult createLastLoginResult() {
        return new LoginResult(getAccessToken(), getAccessToken().getPermissions(), getAccessToken().getDeclinedPermissions());
    }

	/**
	 * Logout from Facebook
	 */
	public void logout(OnLogoutListener onLogoutListener) {
		if (onLogoutListener == null) {
			Logger.logError(TAG, "OnLogoutListener can't be null in -> 'logout(OnLogoutListener onLogoutListener)' method");
			return;
		}

        mLoginManager.logOut();
        onLogoutListener.onLogout();
    }

	/**
	 * Indicate if you are logged in or not.
	 * 
	 * @return <code>True</code> if you is logged in, otherwise return
	 *         <code>False</code>
	 */
	public boolean isLogin() {
        AccessToken accessToken = getAccessToken();
        if (accessToken == null) {
            return false;
        }
        return !accessToken.isExpired();
	}

	/**
	 * Get access token of open session
	 *
	 */
	public AccessToken getAccessToken() {
		return AccessToken.getCurrentAccessToken();
	}

    public Set<String> getAcceptedPermissions() {
        AccessToken accessToken = getAccessToken();
        if (accessToken == null) {
            return new HashSet<String>();
        }
        return accessToken.getPermissions();
    }

    public Set<String> getNotAcceptedPermissions() {
        AccessToken accessToken = getAccessToken();
        if (accessToken == null) {
            return null;
        }
        return accessToken.getDeclinedPermissions();
    }

    void setActivity(Activity activity) {
        mActivity = new WeakReference<Activity>(activity);
    }

    public Activity getActivity() {
        return mActivity.get();
    }

    public LoginCallback getLoginCallback() {
        return mLoginCallback;
    }

    public CallbackManager getCallbackManager() {
        return mCallbackManager;
    }

    /**
     * This checks if user had accepted all publish permissions for being able to use PublishAction
     * @return
     */
	public boolean hasAccepted(String permission) {
		if (getAcceptedPermissions().contains(permission)) {
			return true;
		}
		return false;
	}

	/**
	 * Requests any new permission in a runtime. <br>
	 * <br>
	 *
	 * Useful when you just want to request the action and won't be publishing
	 * at the time, but still need the updated <b>access token</b> with the
	 * permissions (possibly to pass back to your backend).
	 *
	 * <br>
	 * <b>Must be logged in to use.</b>
	 *
	 * @param permissions
	 *            New permissions you want to have. This array can include READ
	 *            and PUBLISH permissions in the same time. Just ask what you
	 *            need.<br>
	 * @param onNewPermissionListener
	 *            The callback listener for the requesting new permission
	 *            action.
	 */
	public void requestNewPermissions(final Permission[] permissions, final OnNewPermissionsListener onNewPermissionListener) {

        if (onNewPermissionListener == null) {
            Logger.logWarning(TAG, "Must pass listener");
            return;
        }

        int flag = configuration.addNewPermissions(permissions);
        flag |= getNotGrantedReadPermissions().size() > 0 ? 1 : 0;
        flag |= getNotGrantedPublishPermissions().size() > 0 ? 2 : 0;
        if (flag == 0) {
            onNewPermissionListener.onFail("There is no new permissions in your request");
            return;
        }

        // in case of marking in configuration the option of getting publish permission, just after read permissions
        if (configuration.hasPublishPermissions() && configuration.isAllPermissionsAtOnce()) {
            mLoginCallback.askPublishPermissions = true;
        }

        mLoginCallback.loginListener = new OnLoginListener() {

            @Override
            public void onLogin(LoginResult loginResult) {
                onNewPermissionListener.onSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                onNewPermissionListener.onFail("Canceled by user");
            }

            @Override
            public void onFail(String reason) {
                onNewPermissionListener.onFail(reason);
            }

            @Override
            public void onException(Throwable throwable) {
                onNewPermissionListener.onException(throwable);
            }

        };

        if (flag == 1 || flag == 3) {
            // if new permissions have only READ or both READ & PUBLISH, then start from read
            requestReadPermissions();
        } else if (flag == 2) {
            // if new permissions have only PUBLISH then, request only publish
            requestPublishPermissions();
        }
	}

	public boolean hasPendingRequest() {
        // waiting for fix on FB side for pull request: https://github.com/facebook/facebook-android-sdk/pull/431
        // try {
        // 	Field f = mLoginManager.getClass().getDeclaredField("pendingLoginRequest");
        // 	f.setAccessible(true);
        //     Object request = f.get(mLoginManager);
        //     if (request != null) {
        // 		return true;
        // 	}
        // } catch (Exception e) {
        // 	// do nothing
        // }
		return false;
	}

	private List<String> getNotGrantedReadPermissions() {
		Set<String> grantedPermissions = getAcceptedPermissions();
		List<String> readPermissions = new ArrayList<String>(configuration.getReadPermissions());
		readPermissions.removeAll(grantedPermissions);
		return readPermissions;
	}

	private List<String> getNotGrantedPublishPermissions() {
        Set<String> grantedPermissions = getAcceptedPermissions();
		List<String> publishPermissions = new ArrayList<String>(configuration.getPublishPermissions());
		publishPermissions.removeAll(grantedPermissions);
		return publishPermissions;
	}

	public boolean isAllPermissionsGranted() {
		if (getNotGrantedReadPermissions().size() > 0 || getNotGrantedPublishPermissions().size() > 0) {
			return false;
		}
		return true;
	}

    public void clean() {
        mActivity.clear();
    }

}
