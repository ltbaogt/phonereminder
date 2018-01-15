package com.phonereminder.ryutb.phonereminder.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.phonereminder.ryutb.phonereminder.R;
import com.phonereminder.ryutb.phonereminder.module.bubblemng.BubbleManagementService;
import com.phonereminder.ryutb.phonereminder.module.contactlist.ContactListFragment2;
import com.phonereminder.ryutb.phonereminder.module.reminderlist.ReminderListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.startService)
    Button btnStartService;
    @BindView(R.id.stopService)
    Button btnStopService;

    ContactListFragment2 mContactListFragment;
    ReminderListFragment mReminderListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBubbleMngService();
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBubbleMngService();
            }
        });

        mContactListFragment = new ContactListFragment2();
        mReminderListFragment = new ReminderListFragment();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ABC", "ABC---").apply();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contactList, mContactListFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.reminderList, mReminderListFragment)
                .commit();
    }

    public void updateReminderList() {
        if (mReminderListFragment != null) mReminderListFragment.updateList();
    }
    private void startBubbleMngService() {
        Intent intent = new Intent(getApplicationContext(), BubbleManagementService.class);
        startService(intent);
    }

    private void stopBubbleMngService() {
        Intent intent = new Intent(getApplicationContext(), BubbleManagementService.class);
        stopService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
