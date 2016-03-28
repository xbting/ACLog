package com.xbting.example.aclog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xbting.ac.log.ACLog;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_MSG = "This is a log message";
    private static final String TAG = "ACLog";
    private static String JSON;
    private static String STRING_LONG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ACLog.init(getApplication());
        ACLog.DEBUG = false;
        ACLog.WRITE_TO_File = true;
        init();
    }

    private void init() {
        JSON = getResources().getString(R.string.json);
        STRING_LONG = getString(R.string.string_long);
    }

    public void logWithMsg(View view) {
        ACLog.v(LOG_MSG);
        ACLog.d(LOG_MSG);
        ACLog.i(JSON);
        ACLog.w(STRING_LONG);
        ACLog.e(LOG_MSG);
        ACLog.json(TAG,JSON);

    }

    public void logWithTag(View view) {
        ACLog.v(TAG,LOG_MSG);
        ACLog.d(TAG,LOG_MSG);
        ACLog.i(TAG,LOG_MSG);
        ACLog.w(TAG,LOG_MSG);
        ACLog.e(TAG,LOG_MSG);
    }


}
