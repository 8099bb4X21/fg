package org.telegram.messenger.forkgram;

import android.os.Environment;

import org.telegram.messenger.MessagesController;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ForkDebugLog {

    private static PrintWriter writer;
    private static String currentDate;
    private static boolean enabled;

    public static void init(boolean enabled) {
        ForkDebugLog.enabled = enabled;
        if (enabled) {
            try {
                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "forkgram");
                dir.mkdirs();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                currentDate = date;
                File logFile = new File(dir, "trace-" + date + ".log");
                writer = new PrintWriter(new FileWriter(logFile, true));
                log("=== Log started ===");
            } catch (Exception e) {
                writer = null;
            }
        } else {
            close();
        }
    }

    public static void log(String message) {
        if (!enabled || writer == null) return;
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).format(new Date());
        writer.println(timestamp + " " + message);
        writer.flush();
    }

    private static void close() {
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }
}
