package com.phonereminder.ryutb.phonereminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phonereminder.ryutb.phonereminder.base.BaseDialogFragment;
import com.phonereminder.ryutb.phonereminder.control.AppEditText;
import com.phonereminder.ryutb.phonereminder.control.AppTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ryutb on 11/01/2018.
 */

public class AddingContantDialogFragment extends BaseDialogFragment {

    Unbinder mUnbinder;
    @BindView(R.id.tvName)
    AppTextView tvName;
    @BindView(R.id.tvPhone)
    AppTextView tvPhone;
    @BindView(R.id.etNote)
    AppEditText etNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.add_contact_fragment_layout, container, false);
        mUnbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
