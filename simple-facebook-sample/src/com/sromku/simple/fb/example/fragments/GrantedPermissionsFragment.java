package com.sromku.simple.fb.example.fragments;

import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.utils.Utils;

public class GrantedPermissionsFragment extends BaseFragment {

	private final static String EXAMPLE = "Show granted permissions";

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

				String res = "";
				List<String> grantedPermissions = SimpleFacebook.getInstance().getGrantedPermissions();
				res += Utils.join(grantedPermissions.iterator(), "<br>", new Utils.Process<String>() {
					@Override
					public String process(String permission) {
						return "\u25CF " + permission + " \u25CF";
					}
				});
				res += "<br>";
				mResult.setText(Html.fromHtml(res));

				// check if all permissions granted
				boolean allPermissionsGranted = SimpleFacebook.getInstance().isAllPermissionsGranted();
				Toast.makeText(getActivity(), allPermissionsGranted? "All permissions granted" : "Not all permissions were granted by user", Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	}

}