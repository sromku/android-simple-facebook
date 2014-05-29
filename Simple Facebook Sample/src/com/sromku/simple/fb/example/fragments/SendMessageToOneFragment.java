package com.sromku.simple.fb.example.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.listeners.OnInviteListener;
import com.sromku.simple.fb.utils.Utils;

public class SendMessageToOneFragment extends Fragment {

	private Button mInviteAllButton;
	private TextView mResult;
	private final static String INVITE_MESSAGE = "I invite you to join me";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle("Invite all");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_invite_all, container, false);
		mResult = (TextView) view.findViewById(R.id.result);
		mInviteAllButton = (Button) view.findViewById(R.id.button);
		mInviteAllButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OnInviteListener onInviteListener = new OnInviteListener() {
					@Override
					public void onFail(String reason) {
						mResult.setText(reason);
					}
					
					@Override
					public void onException(Throwable throwable) {
						mResult.setText(throwable.getMessage());
					}
					
					@Override
					public void onComplete(List<String> invitedFriends, String requestId) {
						String print = "<u><b>Requet Id</b></u><br>" + requestId + "<br><br>";
						print += String.format("<u><b>Invited Ids (%d)</b></u><br>", invitedFriends.size());
						print += Utils.join(invitedFriends.iterator(), "<br>");
						mResult.setText(Html.fromHtml(print));
					}
					
					@Override
					public void onCancel() {
						mResult.setText(Html.fromHtml("<u><b>Result</b></u>	<br>Canceled invitation"));
					}
				};
				SimpleFacebook.getInstance(getActivity()).invite(INVITE_MESSAGE, onInviteListener, null);
			}
		});
		return view;
	}
}
