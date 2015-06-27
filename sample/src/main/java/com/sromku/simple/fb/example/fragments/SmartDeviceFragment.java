package com.sromku.simple.fb.example.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Device;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.example.utils.Utils;
import com.sromku.simple.fb.listeners.OnAuthorizationDeviceListener;
import com.sromku.simple.fb.listeners.OnConnectDeviceListener;

public class SmartDeviceFragment extends BaseFragment{

    private final static String EXAMPLE = "Smart Device - Connect";

    private TextView mConnectResult;
    private TextView mAuthResult;
    private Button mConnectButton;
    private Button mAuthButton;

    private Device mConenctedDevice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(EXAMPLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_device, container, false);
        mConnectResult = (TextView) view.findViewById(R.id.result_connect);
        mConnectButton = (Button) view.findViewById(R.id.button_connect);
        mAuthResult = (TextView) view.findViewById(R.id.result_poll);
        mAuthButton = (Button) view.findViewById(R.id.button_poll);

        // set connect device
        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Permission[] permissions = new Permission[] {
                        Permission.PUBLIC_PROFILE,
                        Permission.EMAIL
                };

                SimpleFacebook.getInstance().connectDevice(permissions, new OnConnectDeviceListener() {

                    @Override
                    public void onThinking() {
                        showDialog();
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        hideDialog();
                        mConnectResult.setText(throwable.getMessage());
                    }

                    @Override
                    public void onFail(String reason) {
                        hideDialog();
                        mConnectResult.setText(reason);
                    }

                    @Override
                    public void onComplete(Device device) {
                        hideDialog();
                        String str = Utils.toHtml(device);
                        mConnectResult.setText(Html.fromHtml(str));
                        mConenctedDevice = device;
                    }
                });
            }
        });

        // set authorization
        mAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConenctedDevice == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "You need to connect device first", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleFacebook.getInstance().pollDeviceAuthorization(mConenctedDevice.getAuthorizationCode(), new OnAuthorizationDeviceListener() {

                    @Override
                    public void onThinking() {
                        showDialog();
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        hideDialog();
                        mAuthResult.setText(throwable.getMessage());
                    }

                    @Override
                    public void onFail(String reason) {
                        hideDialog();
                        mAuthResult.setText(reason);
                    }

                    @Override
                    public void onComplete(String accessToken) {
                        hideDialog();
                        mAuthResult.setText("Access Token: " + accessToken);
                    }
                });
            }
        });


        return view;
    }

}
