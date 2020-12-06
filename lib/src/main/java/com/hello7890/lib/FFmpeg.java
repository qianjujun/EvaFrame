package com.hello7890.lib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FFmpeg {


    public static void main(String[] args) throws IOException, InterruptedException {

        List<String> command = Cmd.convert("mov");
        excute(command);

    }


    public static void excute(List<String> command) throws IOException, InterruptedException {
        System.out.println("=================start");

        if(command==null||command.isEmpty()){
            return;
        }

        if(!"ffmpeg".equals(command.get(0))&&!"ffprobe".equals(command.get(0))){
            command.add(0,"ffmpeg");
        }

        System.out.println(command);
        long time = System.currentTimeMillis();
        Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();

        new PrintStream(videoProcess.getErrorStream()).start();
        new PrintStream(videoProcess.getInputStream()).start();
        videoProcess.waitFor();
        System.out.println("=================end"+"  "+(System.currentTimeMillis()-time));
    }
}

class PrintStream extends Thread {
    java.io.InputStream __is = null;

    public PrintStream(java.io.InputStream is) {
        __is = is;
    }

    public void run() {
        try {
            while (this != null) {
                int _ch = __is.read();
                if (_ch != -1)
                    System.out.print((char) _ch);
                else break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
