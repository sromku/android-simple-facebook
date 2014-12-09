package com.sromku.simple.fb.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.widget.LikeView;
import com.facebook.widget.LikeView.OnErrorListener;
import com.sromku.simple.fb.example.R;

public class PublishLikeButtonFragment extends BaseFragment {

	private final static String EXAMPLE = "Like button";

	private TextView mResult;
	private LikeView mLikeView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(EXAMPLE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_like_view, container, false);
		mResult = (TextView) view.findViewById(R.id.result);
		mLikeView = (LikeView) view.findViewById(R.id.like_view);
		mLikeView.setObjectId("1501124800143936");
		mLikeView.setOnErrorListener(new OnErrorListener() {
			@Override
			public void onError(Bundle errorBundle) {
				mResult.setText("Some error happened");
			}
		});
		return view;
	}

}
