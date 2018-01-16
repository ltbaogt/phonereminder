package com.phonereminder.ryutb.phonereminder.module.bubblemng;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.phonereminder.ryutb.phonereminder.R;
import com.phonereminder.ryutb.phonereminder.bubbles.BubbleLayout;
import com.phonereminder.ryutb.phonereminder.bubbles.BubblesManager;
import com.phonereminder.ryutb.phonereminder.bubbles.OnInitializedCallback;
import com.phonereminder.ryutb.phonereminder.libs.richpath.animator.RichPathAnimator;
import com.phonereminder.ryutb.phonereminder.libs.richpath.richpath.RichPath;
import com.phonereminder.ryutb.phonereminder.libs.richpath.richpath.RichPathView;
import com.phonereminder.ryutb.phonereminder.model.ReminderItem;
import com.phonereminder.ryutb.phonereminder.util.SharePrefUtil;

import java.util.List;

/**
 * Created by ryutb on 10/01/2018.
 */

public class BubbleManagementService extends Service {

    private BubblesManager bubblesManager;
    private BroadcastReceiver mPhoneCallReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BubbleManagementService", "BubbleManagementService");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            phoneNumber = phoneNumber == null ? "" : phoneNumber;
            if (TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(state)) {
                List<ReminderItem> list = SharePrefUtil.getReminderListItem(getApplicationContext());
                if (list != null) {
                    for (ReminderItem item : list) {
                        if (item.getPhone().equalsIgnoreCase(phoneNumber)) {
                            addNewBubble();
                        }
                    }
                }
            } else {
                Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeBubblesManager();
        IntentFilter filter = new IntentFilter();
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(mPhoneCallReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mPhoneCallReceiver);
    }

    private void initializeBubblesManager() {
        bubblesManager = new BubblesManager.Builder(getApplicationContext())
                .setTrashLayout(R.layout.bubble_trash_layout)
                .setInitializationCallback(new OnInitializedCallback() {
                    @Override
                    public void onInitialized() {
                    }
                })
                .build();
        bubblesManager.initialize();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        addNewBubble();
        return super.onStartCommand(intent, flags, startId);
    }

    private void addNewBubble() {
        BubbleLayout bubbleView = (BubbleLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.bubble_layout, null);
        animateNotification(bubbleView.findViewById(R.id.ic_notifications));
        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) {
            }
        });
        bubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {

            @Override
            public void onBubbleClick(BubbleLayout bubble) {
                String str = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ABC", "---");
                Toast.makeText(getApplicationContext(), "Clicked !" + str,
                        Toast.LENGTH_SHORT).show();
            }
        });
        bubbleView.setShouldStickToWall(true);
        bubblesManager.addBubble(bubbleView, 60, 20);
    }

    public void animateNotification(View view) {
        if (view == null || !(view instanceof RichPathView)) return;
        final RichPath top = ((RichPathView)view).findRichPathByIndex(0);
        final RichPath bottom = ((RichPathView)view).findRichPathByIndex(1);

        RichPathAnimator.animate(top)
                .interpolator(new DecelerateInterpolator())
                .rotation(0, 20, -20, 10, -10, 5, -5, 2, -2, 0)
                .duration(4000)
                .andAnimate(bottom)
                .interpolator(new DecelerateInterpolator())
                .rotation(0, 10, -10, 5, -5, 2, -2, 0)
                .startDelay(50)
                .duration(4000)
                .repeatCountSet(RichPathAnimator.INFINITE)
                .start();
    }
}
