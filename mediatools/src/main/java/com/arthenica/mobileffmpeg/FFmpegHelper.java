package com.arthenica.mobileffmpeg;

import android.util.Log;

import java.util.Arrays;

public class FFmpegHelper {
    public static int execute(final String[] arguments,long id){
        return Config.ffmpegExecute(id, arguments);
    }

    public static int execute(final String arguments,long id){
        String[] order = FFmpeg.parseArguments(arguments);
        Log.d("qianjujun", "execute() called with: arguments = [" + arguments + "], id = [" + Arrays.toString(order) + "]");
        return Config.ffmpegExecute(id, order);
        //return 1;
    }
}
