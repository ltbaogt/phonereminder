package com.phonereminder.ryutb.phonereminder.module.reminderlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phonereminder.ryutb.phonereminder.R;
import com.phonereminder.ryutb.phonereminder.libs.swipe.SwipeRevealLayout;
import com.phonereminder.ryutb.phonereminder.libs.swipe.ViewBinderHelper;
import com.phonereminder.ryutb.phonereminder.model.ReminderItem;
import com.phonereminder.ryutb.phonereminder.util.SharePrefUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ryutb on 12/01/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final ViewBinderHelper mBinderHelper = new ViewBinderHelper();
    private List<ReminderItem> mList;

    public Adapter(List<ReminderItem> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.remminder_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String formatString = String.format("%1$s - %2$s", mList.get(position).getPhone(), mList.get(position).getNote());
        mBinderHelper.bind((SwipeRevealLayout) holder.itemView.findViewById(R.id.swipeRevealLayout), formatString);
        holder.tvName.setText(formatString);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateList(List<ReminderItem> list) {
        mList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;

        @OnClick(R.id.btnDelete)
        void deleteReminder() {
            ReminderItem removeItem = mList.get(getAdapterPosition());
            SharePrefUtil.getInstace(itemView.getContext()).removeReminderItem(itemView.getContext(), removeItem);
            mList.remove(removeItem);
            notifyDataSetChanged();
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
