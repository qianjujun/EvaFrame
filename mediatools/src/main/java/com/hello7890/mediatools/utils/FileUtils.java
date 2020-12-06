package com.hello7890.mediatools.utils;

import android.os.Environment;

import com.qianjujun.frame.utils.ContextProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class FileUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
    public static File getExportFile(){
        File root = getCacheFile("compress");
        File file = new File(root, getMp4NameByTime());
        return file;
    }


    public static String getMp4NameByTime(){
        return sdf.format(System.currentTimeMillis())+".mov";
    }


    public static File getProgramFile() {
        File root = Environment.getExternalStorageDirectory();
        File program = new File(root, "MediaTools");
        if (!program.exists()) {
            program.mkdir();
        }

        return program;

        //return ContextProvider.getContext().getFilesDir();
    }

    public static File getCacheFile(String name) {
        File file = new File(getProgramFile(), name);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
