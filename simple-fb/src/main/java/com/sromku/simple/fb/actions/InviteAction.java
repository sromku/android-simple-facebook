package com.sromku.simple.fb.actions;

import android.app.Activity;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.listeners.OnInviteListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

import java.util.ArrayList;

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
        if (sessionManager.isLogin()) {
            openInviteDialog(sessionManager.getActivity(), mOnInviteListener);
        }
        else {
            String reason = Errors.getError(ErrorMsg.LOGIN);
            Logger.logError(InviteAction.class, reason, null);
            mOnInviteListener.onFail(reason);
        }
    }

    private void openInviteDialog(Activity activity, final OnInviteListener onInviteListener) {

        // build request
        GameRequestContent.Builder builder = new GameRequestContent.Builder();
        builder.setMessage(mMessage);
        builder.setData(mData);
        builder.setTo(mTo);
        if (mSuggestions != null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String suggested : mSuggestions) {
                arrayList.add(suggested);
            }
            builder.setSuggestions(arrayList);
        }

        GameRequestContent gameRequestContent = builder.build();

        // set dialog
        GameRequestDialog dialog = new GameRequestDialog(activity);
        if (dialog.canShow(gameRequestContent)) {
            dialog.registerCallback(sessionManager.getCallbackManager(), new FacebookCallback<GameRequestDialog.Result>() {

                @Override
                public void onSuccess(GameRequestDialog.Result result) {
                    String requestId = result.getRequestId();
                    if (mOnInviteListener != null) {
                        mOnInviteListener.onComplete(result.getRequestRecipients(), requestId);
                    }
                }

                @Override
                public void onCancel() {
                    if (mOnInviteListener != null) {
                        mOnInviteListener.onFail("Canceled by user");
                    }
                }

                @Override
                public void onError(FacebookException e) {
                    if (mOnInviteListener != null) {
                        mOnInviteListener.onFail(e.getMessage());
                    }
                }
            });
            dialog.show(gameRequestContent);
        }

    }

}
