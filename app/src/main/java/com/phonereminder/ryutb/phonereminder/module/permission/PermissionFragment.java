package com.phonereminder.ryutb.phonereminder.module.permission;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.phonereminder.ryutb.phonereminder.R;
import com.phonereminder.ryutb.phonereminder.base.BaseFragment;
import com.phonereminder.ryutb.phonereminder.util.PermissionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.phonereminder.ryutb.phonereminder.util.PermissionUtil.PERMISSIONS;
import static com.phonereminder.ryutb.phonereminder.util.PermissionUtil.PERMISSIONS_READ_CONTACT;
import static com.phonereminder.ryutb.phonereminder.util.PermissionUtil.PERMISSIONS_READ_PHONE_STATE;
import static com.phonereminder.ryutb.phonereminder.util.PermissionUtil.PERMISSION_ALL;
import static com.phonereminder.ryutb.phonereminder.util.PermissionUtil.PERMISSION_DRAW_OVERLAY;
import static com.phonereminder.ryutb.phonereminder.util.PermissionUtil.hasDrawOverlay;
import static com.phonereminder.ryutb.phonereminder.util.PermissionUtil.hasPermissions;

/**
 * Created by ryutb on 22/01/2018.
 */

public class PermissionFragment extends BaseFragment {

    @BindView(R.id.btnRequestDrawOverlay)
    Button mButton;
    @BindView(R.id.ckReadContact)
    CheckBox mCkReadContact;
    @BindView(R.id.ckReadPhoneState)
    CheckBox mCkReadPhoneState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.permission_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mCkReadContact.setChecked(PermissionUtil.hasPermissions(getContext(), PERMISSIONS_READ_CONTACT));
        mCkReadPhoneState.setChecked(PermissionUtil.hasPermissions(getContext(), PERMISSIONS_READ_PHONE_STATE));
        mButton.setEnabled(!hasDrawOverlay(getContext()));
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtil.requestDrawOverlay(PermissionFragment.this);
            }
        });
        if (!hasPermissions(getContext(), PERMISSIONS)) {
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults,
                new PermissionUtil.OnDrawOverlayCallback[]{
                        new PermissionUtil.OnDrawOverlayCallback() {
                            @Override
                            public void onGranted(boolean isGranted) {
                                mCkReadContact.setChecked(isGranted);
                            }
                        },
                        new PermissionUtil.OnDrawOverlayCallback() {
                            @Override
                            public void onGranted(boolean isGranted) {
                                mCkReadPhoneState.setChecked(isGranted);
                            }
                        }
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSION_DRAW_OVERLAY:
                PermissionUtil.onRequestDrawOverlayResult(getContext(), resultCode, data, new PermissionUtil.OnDrawOverlayCallback() {
                    @Override
                    public void onGranted(boolean isGranted) {
                        mButton.setEnabled(!isGranted);
                    }
                });
        }
    }
}
