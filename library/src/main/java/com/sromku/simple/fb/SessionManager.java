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
import java.lang.reflect.Field;
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

        private OnLoginListener mOnLoginListener;

        public void setLoginListener(OnLoginListener listener) {
            mOnLoginListener = listener;
        }

        @Override
        public void onSuccess(LoginResult loginResult) {
            if (mOnLoginListener != null) {
                mOnLoginListener.onLogin(loginResult);
            }
        }

        @Override
        public void onCancel() {
            mOnLoginListener.onFail("User canceled the permissions dialog");
        }

        @Override
        public void onError(FacebookException e) {
            mOnLoginListener.onException(e);
        }
    };

	public SessionManager(SimpleFacebookConfiguration configuration) {
		SessionManager.configuration = configuration;
        mLoginManager = LoginManager.getInstance();
        mLoginManager.registerCallback(mCallbackManager, mLoginCallback);
        mLoginManager.setDefaultAudience(configuration.getDefaultAudience());
        mLoginManager.setLoginBehavior(configuration.getLoginBehavior());
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
			onLoginListener.onLogin(createLastLoginResult());
			return;
		}

		if (hasPendingRequest()) {
			Logger.logWarning(TAG, "You are trying to login one more time, before finishing the previous login call");
			return;
		}

        mLoginCallback.setLoginListener(onLoginListener);
        Set<String> permissions = null;
        AccessToken accessToken = getAccessToken();
        if (accessToken != null) {
            permissions = getAccessToken().getPermissions();
        }

        // check what exactly we need to request
        List<String> readPermissions = configuration.getReadPermissions();
        if (permissions == null || !permissions.containsAll(readPermissions)) {

            // user hasn't the access token with all read permissions we need, thus we ask him to login
            mLoginManager.logInWithReadPermissions(getActivity(), readPermissions);

        } else {
            onLoginListener.onLogin(createLastLoginResult());
        }
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

        mLoginCallback.setLoginListener(null);
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
	 * <b>Must be logged to use.</b>
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
        // TODO - REVIVE
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
	}

	public boolean hasPendingRequest() {
		try {
			Field f = mLoginManager.getClass().getDeclaredField("pendingLoginRequest");
			f.setAccessible(true);
            Object request = f.get(mLoginManager);
            if (request != null) {
				return true;
			}
		} catch (Exception e) {
			// do nothing
		}
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
