package com.sromku.simple.fb.utils;

import java.util.Locale;

public class Errors {
	public static enum ErrorMsg {
		LOGIN("You are not logged in"),
		CANCEL_WEB_LOGIN("User canceled the login web dialog"),
		PERMISSIONS_PUBLISH("Publish permission: '%s' wasn't set by SimpleFacebookConfiguration"),
		CANCEL_PERMISSIONS_PUBLISH("Publish permissions: '%s' weren't accepted by user");

		private String mMsg;

		private ErrorMsg(String msg) {
			mMsg = msg;
		}

		public String message() {
			return mMsg;
		}
	}

	/**
	 * Get final error message
	 * 
	 * @param errorMsg
	 * @param params
	 * @return
	 */
	public static String getError(ErrorMsg errorMsg, Object... params) {
		if (params == null) {
			return errorMsg.message();
		}
		else {
			return String.format(Locale.US, errorMsg.message(), params);
		}
	}

	public static String getError(ErrorMsg errorMsg) {
		return errorMsg.message();
	}
}
