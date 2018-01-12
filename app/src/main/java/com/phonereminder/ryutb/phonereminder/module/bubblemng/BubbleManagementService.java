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
import android.widget.Toast;

import com.phonereminder.ryutb.phonereminder.R;
import com.phonereminder.ryutb.phonereminder.bubbles.BubbleLayout;
import com.phonereminder.ryutb.phonereminder.bubbles.BubblesManager;
import com.phonereminder.ryutb.phonereminder.bubbles.OnInitializedCallback;

/**
 * Created by ryutb on 10/01/2018.
 */

public class BubbleManagementService extends Service {

    private BubblesManager bubblesManager;
    private BroadcastReceiver mPhoneCallReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BubbleManagementService","BubbleManagementService");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(state)) {
                addNewBubble();
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
        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) { }
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
}
