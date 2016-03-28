package com.xbting.ac.log;

import android.util.Log;

import com.xbting.ac.log.ACLog.LogLevel;

/**
 * 输出到控制台
 * <p/>
 * Created by xubt on 2016/3/28.
 */
public class PrintToConsole {

    private static PrintToConsole instance;

    public static PrintToConsole getInstance() {
        if (instance == null)
            instance = new PrintToConsole();
        return instance;
    }

    private void printLine(String tag, String msg, int level) {
        switch (level) {

            case LogLevel.VERBOSE:
                Log.v(tag, msg);
                break;
            case LogLevel.DEBUG:
                Log.d(tag, msg);
                break;
            case LogLevel.INFO:
                Log.i(tag, msg);
                break;
            case LogLevel.WARN:
                Log.w(tag, msg);
                break;
            case LogLevel.ERROR:
                Log.e(tag, msg);
                break;
            default:
                break;
        }
    }

    public void print(String tag, String msg, int level) {
        int index = 0;
        int maxLength = 2000;
        int lineNum = msg.length() / maxLength;

        if (lineNum > 0) {
            for (int i = 0; i < lineNum; i++) {
                String line = msg.substring(index, index + maxLength);
                printLine(tag, line, level);
                index += maxLength;
            }
            printLine(tag, msg.substring(index, msg.length()), level);
        } else {
            printLine(tag, msg, level);
        }
    }

    public void printJson(String tag, String msg, int level) {

    }

}
