package org.telegram.messenger.forkgram;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ForkDebugLog {

    private static PrintWriter writer;
    private static boolean enabled;
    private static File logFile;

    public static synchronized void init(boolean enabled) {
        ForkDebugLog.enabled = enabled;
        if (enabled) {
            if (writer != null) {
                close();
            }
            open();
        } else {
            close();
        }
    }

    private static void open() {
        try {
            File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (downloadDir == null) return;
            File dir = new File(downloadDir, "forkgram");
            dir.mkdirs();
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
            logFile = new File(dir, "trace-" + date + ".log");
            writer = new PrintWriter(new FileWriter(logFile, true));
            logInternal("=== Log started ===");
        } catch (Exception e) {
            writer = null;
            logFile = null;
        }
    }

    public static synchronized void log(String message) {
        if (!enabled) return;
        if (writer == null || logFile != null && !logFile.exists()) {
            if (writer != null) {
                close();
            }
            open();
        }
        if (writer != null) {
            logInternal(message);
        }
    }

    private static void logInternal(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).format(new Date());
        writer.println(timestamp + " " + message);
        writer.flush();
    }

    private static void close() {
        if (writer != null) {
            writer.close();
            writer = null;
        }
        logFile = null;
    }
}
