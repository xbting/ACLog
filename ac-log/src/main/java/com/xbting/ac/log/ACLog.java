package com.xbting.ac.log;


import android.content.Context;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by xubt on 2016/3/25.
 */
public class ACLog {
    private static ACLog mLog;
    private static Context mContext;
    /**
     * 是否显示日志
     */
    public static boolean DEBUG = false;
    /**
     * Whether to save to file
     */
    public static boolean WRITE_TO_File = false;
    /**
     * 日志文件目录
     */
    static String LOG_PATH = "/sdcard/ACLog/";
    /**
     *
     */
    public static final int JSON_INDENT = 4;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");


    /**
     * Initialize the log util
     */
    public static void init(Context context) {
        init(context, null);
    }

    /**
     * Initialize the log
     * default  log directory to save:/sdCard/Android/data/app_package/file/
     *
     * @param path
     */
    public static void init(Context context, String path) {
        mContext = context;
        if (path != null) {
            LOG_PATH = path;
        } else {
            LOG_PATH = mContext.getExternalFilesDir(null) + "/Log";
        }
        File destDir = new File(LOG_PATH);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }


    private synchronized static ACLog getInstance() {
        if (mLog == null) {
            mLog = new ACLog();
        }
        return mLog;
    }

    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg) {
        log(tag, msg, LogLevel.VERBOSE);
    }


    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        log(tag, msg, LogLevel.DEBUG);
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        log(tag, msg, LogLevel.INFO);
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        log(tag, msg, LogLevel.WARN);
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        log(tag, msg, LogLevel.ERROR);
    }

    public static void json(String msg) {
        json(null, msg);
    }

    public static void json(String tag, String msg) {
        log(tag, msg, LogLevel.JSON);
    }

    public static void wtf(String msg) {
        json(null, msg);
    }

    public static void wtf(String tag, String msg) {
        log(tag, msg, LogLevel.WTF);
    }

    private static void log(String tag, String msg, int level) {
        ACLogTag logTag = getInstance().getLogTag();
        if (TextUtils.isEmpty(tag)) {
            tag = logTag.mTag;
        }
        StringBuffer msgBuf = new StringBuffer();
        msgBuf.append(logTag.mInfo);
        msgBuf.append(msg);
        switch (level) {
            case LogLevel.VERBOSE:
            case LogLevel.DEBUG:
            case LogLevel.INFO:
            case LogLevel.WARN:
            case LogLevel.ERROR:
                if (DEBUG) {
                    PrintToConsole.getInstance().print(tag, msgBuf.toString(), level);
                }
                if (WRITE_TO_File) {
                    PrintToFile.getInstance().print(tag, null, msgBuf.toString());
                }
                break;
            case LogLevel.JSON:
                if (DEBUG) {
                    PrintToConsole.getInstance().printJson(tag, msg, logTag.mInfo);
                }
                if (WRITE_TO_File) {
                    PrintToFile.getInstance().print(tag, null, msgBuf.toString());
                }
                break;
        }


    }

    /**
     * 获取日志标签
     *
     * @return
     */
    private ACLogTag getLogTag() {

        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(getClass().getName())) {
                continue;
            }

            ACLogTag tag = new ACLogTag();
            tag.mTag = st.getFileName();
            StringBuffer stuf = new StringBuffer();
            stuf.append("[ (");
            stuf.append(st.getFileName());
            stuf.append(":");
            stuf.append(st.getLineNumber());
            stuf.append(")/");
            stuf.append(st.getMethodName());
            stuf.append(" ] ");
            tag.mInfo = stuf.toString();
            return tag;
        }
        return null;
    }

    /**
     * 日志级别
     */
    public class LogLevel {
        public static final int VERBOSE = 1;
        public static final int DEBUG = 2;
        public static final int INFO = 3;
        public static final int WARN = 4;
        public static final int ERROR = 5;
        public static final int JSON = 6;
        public static final int WTF = 7;
    }


}
