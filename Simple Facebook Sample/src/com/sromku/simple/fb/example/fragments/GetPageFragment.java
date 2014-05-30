package com.sromku.simple.fb.example.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Page;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.example.utils.Utils;
import com.sromku.simple.fb.listeners.OnPageListener;

public class GetPageFragment extends BaseFragment{

	private final static String EXAMPLE = "Get page";
	
	private TextView mResult;
	private Button mGetButton;
	private TextView mMore;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(EXAMPLE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_example_action, container, false);
		mResult = (TextView) view.findViewById(R.id.result);
		mMore = (TextView) view.findViewById(R.id.load_more);
		mMore.setPaintFlags(mMore.getPaint().getFlags() | Paint.UNDERLINE_TEXT_FLAG);
		mGetButton = (Button) view.findViewById(R.id.button);
		mGetButton.setText(EXAMPLE);
		disableLoadMore();
		mGetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String pageId = "117713628271096";
				SimpleFacebook.getInstance().getPage(pageId, new OnPageListener() {

					@Override
					public void onThinking() {
						showDialog();
					}

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
					public void onComplete(Page response) {
						hideDialog();
						String str = Utils.toHtml(response);
						mResult.setText(Html.fromHtml(str));
					}
				});
			}
		});
		return view;
	}

	private void disableLoadMore() {
		mMore.setOnClickListener(null);
		mMore.setVisibility(View.INVISIBLE);
	}
}
