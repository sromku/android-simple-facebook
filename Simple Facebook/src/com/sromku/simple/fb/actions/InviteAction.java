package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.listeners.OnInviteListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

public class InviteAction extends AbstractAction {

	private OnInviteListener mOnInviteListener;
	private String mMessage;
	private String mData;
	private String mTo;
	private String[] mSuggestions;

	private static final String PARAM_MESSAGE = "message";
	private static final String PARAM_DATA = "data";
	private static final String PARAM_TO = "to";
	private static final String PARAM_SUGGESTIONS = "suggestions";

	public InviteAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setMessage(String message) {
		mMessage = message;
	}

	public void setData(String data) {
		mData = data;
	}

	public void setTo(String to) {
		mTo = to;
	}

	public void setSuggestions(String[] suggestions) {
		mSuggestions = suggestions;
	}

	public void setOnInviteListener(OnInviteListener onInviteListener) {
		mOnInviteListener = onInviteListener;
	}

	@Override
	protected void executeImpl() {
		if (sessionManager.isLogin(true)) {
			Bundle params = new Bundle();
			if (mMessage != null) {
				params.putString(PARAM_MESSAGE, mMessage);
			}
			if (mData != null) {
				params.putString(PARAM_DATA, mData);
			}
			if (mTo != null) {
				params.putString(PARAM_TO, mTo);
			}
			else if (mSuggestions != null) {
				params.putString(PARAM_SUGGESTIONS, TextUtils.join(",", mSuggestions));
			}
			openInviteDialog(sessionManager.getActivity(), params, mOnInviteListener);
		}
		else {
			String reason = Errors.getError(ErrorMsg.LOGIN);
			Logger.logError(InviteAction.class, reason, null);
			mOnInviteListener.onFail(reason);
		}
	}

	private void openInviteDialog(Activity activity, Bundle params, final OnInviteListener onInviteListener) {
		final Dialog dialog = new WebDialog.RequestsDialogBuilder(activity, Session.getActiveSession(), params).setOnCompleteListener(new WebDialog.OnCompleteListener() {
			@Override
			public void onComplete(Bundle values, FacebookException error) {
				if (error != null) {
					Logger.logError(InviteAction.class, "Failed to invite", error);
					if (error instanceof FacebookOperationCanceledException) {
						onInviteListener.onCancel();
					}
					else {
						if (onInviteListener != null) {
							onInviteListener.onException(error);
						}
					}
				}
				else {
					Object object = values.get("request");
					if (object == null) {
						onInviteListener.onCancel();
					}
					else {
						List<String> invitedFriends = fetchInvitedFriends(values);
						onInviteListener.onComplete(invitedFriends, object.toString());
					}
				}
			}
		}).build();
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.show();
	}

	/**
	 * Fetch invited friends from response bundle
	 * 
	 * @param values
	 * @return list of invited friends
	 */
	@SuppressLint("DefaultLocale")
	private static List<String> fetchInvitedFriends(Bundle values) {
		List<String> friends = new ArrayList<String>();

		int size = values.size();
		int numOfFriends = size - 1;
		if (numOfFriends > 0) {
			for (int i = 0; i < numOfFriends; i++) {
				String key = String.format("to[%d]", i);
				String friendId = values.getString(key);
				if (friendId != null) {
					friends.add(friendId);
				}
			}
		}
		return friends;
	}

}
