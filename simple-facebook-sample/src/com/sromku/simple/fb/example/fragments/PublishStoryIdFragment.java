package com.sromku.simple.fb.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Story;
import com.sromku.simple.fb.entities.Story.StoryAction;
import com.sromku.simple.fb.entities.Story.StoryObject;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.listeners.OnPublishListener;

public class PublishStoryIdFragment extends BaseFragment {

	private final static String EXAMPLE = "Publish story (hosted object)";

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

				// set object to be shared
				StoryObject storyObject = new StoryObject.Builder()
					.setId("679847442062963")
					.setNoun("food")
					.build();
				
				// set action to be done 
				StoryAction storyAction = new StoryAction.Builder()
					.setAction("eat")
					.addProperty("taste", "sweet")
					.build();
				
				// build story
				Story story = new Story.Builder()
					.setObject(storyObject)
					.setAction(storyAction)
					.build();
					
				// publish story
				SimpleFacebook.getInstance().publish(story, new OnPublishListener() {
					
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
