package com.sromku.simple.fb.example.fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

	private ProgressDialog mProgressDialog;
	
	protected void showDialog() {
		if (mProgressDialog == null) {
			setProgressDialog();
		}
		mProgressDialog.show();
	}
	
	protected void hideDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
	private void setProgressDialog() {
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setTitle("Thinking...");
		mProgressDialog.setMessage("Doing the action...");
	}
	
}
