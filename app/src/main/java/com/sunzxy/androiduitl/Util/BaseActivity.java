package com.sunzxy.androiduitl.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sunzxy.androiduitl.R;

/**
 * Created by zhengxiaoyong on 16/1/19.
 */
public class BaseActivity extends AppCompatActivity {
    private TextView mTitle;
    protected Toolbar mToolbar;
    private boolean mBackEnabled;
    private static final String ACTION_EXIT_APP = "com.sunzxy.myapp.exit";
    private BroadcastReceiver mExitBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null)
                return;
            if (ACTION_EXIT_APP.equals(intent.getAction())) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerExitReceiver();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null)
            return;
        setSupportActionBar(mToolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setDisplayShowTitleEnabled(mTitle == null);
        if (mBackEnabled) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    public void setBackEnabled(boolean enabled) {
        mBackEnabled = enabled;
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mTitle != null) {
            mTitle.setText(title);
            return;
        }
        if (mToolbar != null)
            mToolbar.setTitle(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterExitReceiver();
    }

    private void registerExitReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_EXIT_APP);
        LocalBroadcastManager.getInstance(this).registerReceiver(mExitBroadcastReceiver, filter);
    }

    private void unRegisterExitReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mExitBroadcastReceiver);
    }

    public void exitApp() {
        Intent exitIntent = new Intent();
        exitIntent.setAction(ACTION_EXIT_APP);
        LocalBroadcastManager.getInstance(this).sendBroadcastSync(exitIntent);
    }
}
