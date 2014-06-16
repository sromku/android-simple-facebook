package com.sromku.simple.fb.example.fragments;

import java.util.List;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.actions.Cursor;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.entities.Profile.Properties;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.listeners.OnFriendsListener;
import com.sromku.simple.fb.utils.Utils;

public class GetFriendsFragment extends BaseFragment{

	private final static String EXAMPLE = "Get friends";
	
	private TextView mResult;
	private Button mGetButton;
	private TextView mMore;
	private String mAllPages = "";

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
		mGetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAllPages = "";
				mResult.setText(mAllPages);

				Properties properties = new Properties.Builder()
					.add(Profile.Properties.ID)
					.add(Profile.Properties.FIRST_NAME)
					.add(Profile.Properties.LAST_NAME)
					.add(Profile.Properties.BIRTHDAY)
					.add(Profile.Properties.AGE_RANGE)
					.add(Profile.Properties.EMAIL)
					.add(Profile.Properties.GENDER)
					.add(Profile.Properties.INSTALLED)
					.build();
				
				SimpleFacebook.getInstance().getFriends(properties, new OnFriendsListener() {

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
					public void onComplete(List<Profile> response) {
						hideDialog();
						// make the result more readable. 
						mAllPages += "<u>\u25B7\u25B7\u25B7 (paging) #" + getPageNum() + " \u25C1\u25C1\u25C1</u><br>";
						mAllPages += Utils.join(response.iterator(), "<br>", new Utils.Process<Profile>() {
							@Override
							public String process(Profile profile) {
								return "\u25CF " + profile.getName() + " \u25CF";
							}
						});
						mAllPages += "<br>";
						mResult.setText(Html.fromHtml(mAllPages));

						// check if more pages exist
						if (hasNext()) {
							enableLoadMore(getCursor());
						} else {
							disableLoadMore();
						}
					}
				});
			}
		});
		return view;
	}

	private void enableLoadMore(final Cursor<List<Profile>> cursor) {
		mMore.setVisibility(View.VISIBLE);
		mMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mAllPages += "<br>";
				cursor.next();
			}
		});
	}

	private void disableLoadMore() {
		mMore.setOnClickListener(null);
		mMore.setVisibility(View.INVISIBLE);
	}
}
