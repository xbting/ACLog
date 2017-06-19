package com.xbting.ac.log;

import android.util.Log;

import com.xbting.ac.log.ACLog.LogLevel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 输出到控制台
 *
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

    /**
     * 打印默认格式日志
     *
     * @param tag
     * @param msg
     * @param level
     */
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

    /**
     * 打印json格式日志
     *
     * @param tag
     * @param msg
     * @param headString
     */
    public void printJson(String tag, String msg, String headString) {
        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(ACLog.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(ACLog.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + ACLog.LINE_SEPARATOR + message;
        String[] lines = message.split(ACLog.LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag,  line);
        }
        printLine(tag, false);
    }


    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "***********************************************************************************************************************************************");
        } else {
            Log.d(tag, "***********************************************************************************************************************************************");
        }
    }

}
