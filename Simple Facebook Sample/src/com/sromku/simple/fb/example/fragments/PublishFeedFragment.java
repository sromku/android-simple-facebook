package com.sromku.simple.fb.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.listeners.OnPublishListener;

public class PublishFeedFragment extends BaseFragment {

	private final static String EXAMPLE = "Publish feed - no dialog";

	private TextView mResult;
	private Button mButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(EXAMPLE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_example_action, container, false);
		mResult = (TextView) view.findViewById(R.id.result);
		view.findViewById(R.id.load_more).setVisibility(View.GONE);
		mButton = (Button) view.findViewById(R.id.button);
		mButton.setText(EXAMPLE);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final Feed feed = new Feed.Builder().setMessage("Clone it out...").setName("Simple Facebook SDK for Android").setCaption("Code less, do the same.")
						.setDescription("Login, publish feeds and stories, invite friends and more....")
						.setPicture("https://raw.github.com/sromku/android-simple-facebook/master/Refs/android_facebook_sdk_logo.png").setLink("https://github.com/sromku/android-simple-facebook")
						.build();

				SimpleFacebook.getInstance().publish(feed, new OnPublishListener() {

					@Override
					public void onException(Throwable throwable) {
						hideDialog();
						mResult.setText(throwable.getMessage());
					}

					@Override
					public void onFail(String reason) {
						hideDialog();
						mResult.setText(reason);
					}

					@Override
					public void onThinking() {
						showDialog();
					}

					@Override
					public void onComplete(String response) {
						hideDialog();
						mResult.setText(response);
					}
				});

			}
		});
		return view;
	}

}
