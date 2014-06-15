package com.sromku.simple.fb;

import java.util.ArrayList;
import java.util.List;

import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;

public class SimpleFacebookConfiguration {
	private String mAppId;
	private String mNamespace;
	private List<String> mReadPermissions = null;
	private List<String> mPublishPermissions = null;
	private SessionDefaultAudience mDefaultAudience = null;
	private SessionLoginBehavior mLoginBehavior = null;
	private boolean mHasPublishPermissions = false;
	boolean mAllAtOnce = false;
	private boolean mUseAppsecretProof = false;
	private String mAppSecret = null;

	private SimpleFacebookConfiguration(Builder builder) {
		this.mAppId = builder.mAppId;
		this.mNamespace = builder.mNamespace;
		this.mReadPermissions = builder.mReadPermissions;
		this.mPublishPermissions = builder.mPublishPermissions;
		this.mDefaultAudience = builder.mDefaultAudience;
		this.mLoginBehavior = builder.mLoginBehavior;
		this.mAllAtOnce = builder.mAllAtOnce;
		this.mUseAppsecretProof = builder.mUseAppsecretProof;
		this.mAppSecret = builder.mAppSecret;

		if (this.mPublishPermissions.size() > 0) {
			this.mHasPublishPermissions = true;
		}
	}

	/**
	 * Get facebook application id
	 * 
	 * @return
	 */
	public String getAppId() {
		return mAppId;
	}

	/**
	 * Get application namespace
	 * 
	 * @return
	 */
	public String getNamespace() {
		return mNamespace;
	}

	/**
	 * Get read permissions
	 * 
	 * @return
	 */
	public List<String> getReadPermissions() {
		return mReadPermissions;
	}

	/**
	 * Get publish permissions
	 * 
	 * @return
	 */
	public List<String> getPublishPermissions() {
		return mPublishPermissions;
	}

	/**
	 * Return <code>True</code> if 'PUBLISH' permissions are defined
	 * 
	 * @return
	 */
	boolean hasPublishPermissions() {
		return mHasPublishPermissions;
	}

	/**
	 * Get session login behavior
	 * 
	 * @return
	 */
	SessionLoginBehavior getSessionLoginBehavior() {
		return mLoginBehavior;
	}

	/**
	 * Get session default audience
	 * 
	 * @return
	 */
	SessionDefaultAudience getSessionDefaultAudience() {
		return mDefaultAudience;
	}

	/**
	 * Return <code>True</code> if appsecret_proof should be passed with graph
	 * api calls, otherwise return <code>False</code>
	 * 
	 * @return The app secret proof
	 * @see https://developers.facebook.com/docs/graph-api/securing-requests
	 */
	public boolean useAppsecretProof() {
		return mUseAppsecretProof;
	}

	/**
	 * Get the app secret
	 * 
	 * @return The app secret
	 */
	public String getAppSecret() {
		return mAppSecret;
	}

	/**
	 * Return <code>True</code> if all permissions - read and publish should be
	 * asked one after another in the same time after logging in.
	 */
	boolean isAllPermissionsAtOnce() {
		return mAllAtOnce;
	}

	/**
	 * Add new permissions in a runtime
	 * 
	 * @param permissions
	 */
	void addNewPermissions(Permission[] permissions) {
		for (Permission permission : permissions) {
			switch (permission.getType()) {
			case READ:
				if (!mReadPermissions.contains(permission.getValue())) {
					mReadPermissions.add(permission.getValue());
				}
				break;
			case PUBLISH:
				if (!mPublishPermissions.contains(permission.getValue())) {
					mPublishPermissions.add(permission.getValue());
				}
				break;
			default:
				break;
			}
		}

		if (this.mPublishPermissions.size() > 0) {
			this.mHasPublishPermissions = true;
		}
	}

	public static class Builder {
		private String mAppId = null;
		private String mNamespace = null;
		private List<String> mReadPermissions = new ArrayList<String>();
		private List<String> mPublishPermissions = new ArrayList<String>();
		private SessionDefaultAudience mDefaultAudience = SessionDefaultAudience.FRIENDS;
		private SessionLoginBehavior mLoginBehavior = SessionLoginBehavior.SSO_WITH_FALLBACK;
		private boolean mAllAtOnce = false;
		private boolean mUseAppsecretProof = false;
		private String mAppSecret = null;

		public Builder() {
		}

		/**
		 * Set facebook App Id. <br>
		 * The application id is located in the dashboard of the app in admin
		 * panel of facebook
		 * 
		 * @param appId
		 */
		public Builder setAppId(String appId) {
			mAppId = appId;
			return this;
		}

		/**
		 * Set application namespace
		 * 
		 * @param namespace
		 * @return
		 */
		public Builder setNamespace(String namespace) {
			mNamespace = namespace;
			return this;
		}

		/**
		 * Set the array of permissions you want to use in your application
		 * 
		 * @param permissions
		 */
		public Builder setPermissions(Permission[] permissions) {
			for (Permission permission : permissions) {
				switch (permission.getType()) {
				case READ:
					mReadPermissions.add(permission.getValue());
					break;
				case PUBLISH:
					mPublishPermissions.add(permission.getValue());
					break;
				default:
					break;
				}
			}

			return this;
		}

		/**
		 * @param defaultAudience
		 *            The defaultAudience to set.
		 * @see SessionDefaultAudience
		 */
		public Builder setDefaultAudience(SessionDefaultAudience defaultAudience) {
			mDefaultAudience = defaultAudience;
			return this;
		}

		/**
		 * @param loginBehavior
		 *            The loginBehavior to set.
		 * @see SessionLoginBehavior
		 */
		public Builder setLoginBehavior(SessionLoginBehavior loginBehavior) {
			mLoginBehavior = loginBehavior;
			return this;
		}

		/**
		 * If your app has both: read and publish permissions, then this
		 * configuration can be very useful. When you first time login the popup
		 * with read permissions that the user should accept is appeared. After
		 * this you can decide, if you want the dialog of publish permissions to
		 * appear or not. <br>
		 * <br>
		 * <b>Note:</b>Facebook requests not to ask the user for read and then
		 * publish permissions at once, thus the default value will be
		 * <code>false</code> for this flag.
		 * 
		 * @param allAtOnce
		 * @return {@link Builder}
		 */
		public Builder setAskForAllPermissionsAtOnce(boolean allAtOnce) {
			mAllAtOnce = allAtOnce;
			return this;
		}

		/**
		 * Set <code>True</code> if appsecret_proof should be passed with graph
		 * api calls, otherwise set <code>False</code>. <b>Set app secret</b>
		 * {@link #setAppSecret(String)} to be able to use this feature.<br>
		 * <br>
		 * The default value is <code>False</code>
		 */
		public Builder useAppsecretProof(boolean use) {
			mUseAppsecretProof = use;
			return this;
		}

		/**
		 * Set the app secret string. The app secret is shown in your app
		 * dashboard settings. <br>
		 * <b>It is highly suggested not to save this string hard coded in your
		 * app</b>
		 * 
		 * @param appSecret
		 */
		public Builder setAppSecret(String appSecret) {
			mAppSecret = appSecret;
			return this;
		}

		/**
		 * Build the configuration for storage tool.
		 * 
		 * @return
		 */
		public SimpleFacebookConfiguration build() {
			return new SimpleFacebookConfiguration(this);
		}

	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[ ").append("mAppId:").append(mAppId).append(", ").append("mNamespace:").append(mNamespace).append(", ").append("mDefaultAudience:").append(mDefaultAudience.name())
				.append(", ").append("mLoginBehavior:").append(mLoginBehavior.name()).append(", ").append("mReadPermissions:").append(mReadPermissions.toString()).append(", ")
				.append("mPublishPermissions:").append(mPublishPermissions.toString()).append(" ]");
		return stringBuilder.toString();
	}
}
