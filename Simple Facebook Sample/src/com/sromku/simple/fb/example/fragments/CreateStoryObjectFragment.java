package com.sromku.simple.fb.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Privacy;
import com.sromku.simple.fb.entities.Privacy.PrivacySettings;
import com.sromku.simple.fb.entities.Story.StoryObject;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.listeners.OnCreateStoryObject;

public class CreateStoryObjectFragment extends BaseFragment {

	private final static String EXAMPLE = "Create open graph object";

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
				
				// set privacy
				Privacy privacy = new Privacy.Builder()
					.setPrivacySettings(PrivacySettings.FRIENDS_OF_FRIENDS)
					.build();
				
				// build story object
				StoryObject storyObject = new StoryObject.Builder()
					.setDescription("The apple is the pomaceous fruit of the apple tree, Malus domestica of the rose family. It is one of the most widely cultivated tree fruits.")
					.setImage("http://romkuapps.com/github/simple-facebook/apple.jpg")
					.setNoun("food")
					.setTitle("Apple")
					.setUrl("https://github.com/sromku/android-simple-facebook")
					.setPrivacy(privacy)
					.addProperty("calories", 52)
					.build();
				
				// create story object
				SimpleFacebook.getInstance().create(storyObject, new OnCreateStoryObject() {

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
