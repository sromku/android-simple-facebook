package com.sromku.simple.fb.example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sromku.simple.fb.example.utils.SharedObjects;

import android.graphics.Color;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExamplesAdapter extends BaseAdapter {

	private static final int TITLE_VIEW = 0;
	private static final int EXAMPLE_VIEW = 1;

	private final List<Example> mExamples;
	private final Set<Integer> mTitles;
	private boolean mLoggedIn;

	public ExamplesAdapter(List<Example> examples) {
		mExamples = examples;
		mTitles = new HashSet<Integer>();
		for (int position = 0; position < examples.size(); position++) {
			if (examples.get(position).getFragment() == null) {
				mTitles.add(position);
			}
		}
	}

	@Override
	public int getCount() {
		return mExamples.size();
	}

	@Override
	public Object getItem(int position) {
		return mExamples.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (mTitles.contains(position)) {
			return TITLE_VIEW;
		}
		return EXAMPLE_VIEW;
	}

	@Override
	public boolean isEnabled(int position) {
		if (getItemViewType(position) == TITLE_VIEW) {
			return false;
		}
		if (!mLoggedIn && mExamples.get(position).isRequireLogin()) {
			return false;
		}
		return super.isEnabled(position);
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		if (view == null) {
			TextView textView = new TextView(SharedObjects.context);
			textView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, SharedObjects.context.getResources().getDimensionPixelSize(R.dimen.example_list_height)));
			textView.setTextColor(SharedObjects.context.getResources().getColor(R.color.black));
			textView.setSingleLine();
			if (getItemViewType(position) == EXAMPLE_VIEW) {
				textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedObjects.context.getResources().getDimensionPixelSize(R.dimen.example_list_text_size));
			} else {
				textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, SharedObjects.context.getResources().getDimensionPixelSize(R.dimen.example_list_title_size));
			}
			textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			int pix10dp = SharedObjects.context.getResources().getDimensionPixelSize(R.dimen.padding_10dp);
			textView.setPadding(pix10dp, 0, pix10dp, 0);
			view = textView;
		}

		Example example = mExamples.get(position);
		TextView textView = (TextView) view;
		if (getItemViewType(position) == EXAMPLE_VIEW) {
			textView.setText(Html.fromHtml("  \u25B6  " + example.getTitle()));
		} else {
			textView.setText(Html.fromHtml(example.getTitle()));
		}
		if (mLoggedIn || (!mLoggedIn && !example.isRequireLogin())) {
			textView.setTextColor(Color.BLACK);
			textView.setEnabled(true);
		} else {
			textView.setTextColor(Color.GRAY);
			textView.setEnabled(false);
		}
		return view;
	}

	public void setLogged(boolean logged) {
		mLoggedIn = logged;
		notifyDataSetChanged();
	}

}
