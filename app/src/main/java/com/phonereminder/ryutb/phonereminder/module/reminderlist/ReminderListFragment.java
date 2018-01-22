package com.phonereminder.ryutb.phonereminder.module.reminderlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phonereminder.ryutb.phonereminder.R;
import com.phonereminder.ryutb.phonereminder.base.BaseFragment;
import com.phonereminder.ryutb.phonereminder.model.ReminderItem;
import com.phonereminder.ryutb.phonereminder.util.SharePrefUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryutb on 11/01/2018.
 */

public class ReminderListFragment extends BaseFragment {

    @BindView(R.id.reminderList)
    RecyclerView reminderList;
    Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.reminder_list_fragment_layout, container, false);
        ButterKnife.bind(this, fragmentView);
        List<ReminderItem> list = SharePrefUtil.getInstace(getContext()).getReminderListItem(getContext().getApplicationContext());
        mAdapter = new Adapter(list);
        reminderList.setAdapter(mAdapter);
        return fragmentView;
    }

    public void updateList() {
        List<ReminderItem> list = SharePrefUtil.getInstace(getContext()).getReminderListItem(getContext());
        mAdapter.updateList(list);
    }

}
