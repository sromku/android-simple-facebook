package com.sromku.simple.fb.example.fragments;

import java.util.List;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Page;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.listeners.OnPagesListener;
import com.sromku.simple.fb.utils.Utils;

public class GetLikesFragment extends Fragment{

	private TextView mResult;
	private Button mGetLikesButton;
	private TextView mMore;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_get_likes, container, false);
		mResult = (TextView) view.findViewById(R.id.result);
		mMore = (TextView) view.findViewById(R.id.load_more);
		mMore.setPaintFlags(mMore.getPaint().getFlags() | Paint.UNDERLINE_TEXT_FLAG);
		mGetLikesButton = (Button) view.findViewById(R.id.button);
		mGetLikesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SimpleFacebook.getInstance().getPages(new OnPagesListener() {
					@Override
					public void onException(Throwable throwable) {
						mResult.setText(throwable.getMessage());
					}
					
					@Override
					public void onFail(String reason) {
						mResult.setText(reason);
					}
					
					@Override
					public void onComplete(List<Page> response) {
						 String result = Utils.join(response.iterator(), "\n");
						 mResult.setText(result);
					}
				});
			}
		});
		return view;
	}
}
