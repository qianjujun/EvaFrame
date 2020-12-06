package com.hello7890.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Cmd {

    public static final String CODECS = "-codecs";
    public static final String FORMATS = "-formats";
    public static final String FILTERS = "-filters";

    public static List<String> getInfo(String cmd) {
        List<String> command = new ArrayList<>();
        command.add(cmd);
        return command;
    }


    public static List<String> convert(String input, String output,boolean copy) {
        List<String> command = new ArrayList<>();
        command.add("-y");
        command.add("-i");
        command.add(input);
        if(copy){
            command.add("-vcodec");
            command.add("copy");
            command.add("-acodec");
            command.add("copy");
            command.add("-vbsf");
            command.add("h264_mp4toannexb");

        }

        command.add("-threads");
        command.add("4");


        //command.add("-acodec");
        //command.add("copy");
        //-threads 1
        command.add(output);
        return command;
    }

    public static List<String> convert(String format) {
        String input = "C:\\Users\\TF\\Desktop\\testvideo\\2.mp4";
        String output = "C:\\Users\\TF\\Desktop\\testvideo\\test." + format;

        return convert(new File(input).getAbsolutePath(), new File(output).getAbsolutePath(),false);
    }

    public static  List<String> convertWithName(String name,boolean copy){
        String input = "C:\\Users\\TF\\Desktop\\testvideo\\2.mp4";
        String output = "C:\\Users\\TF\\Desktop\\testvideo\\" + name;

        return convert(new File(input).getAbsolutePath(), new File(output).getAbsolutePath(),copy);
    }


    public static List<String> getInfo(){
        String input = "C:\\Users\\TF\\Desktop\\testvideo\\2.mp4";
        List<String> cmd = new ArrayList<>();
        cmd.add("ffmpeg");
        cmd.add("-v");
        cmd.add("quiet");
        cmd.add("-print_forma");
        cmd.add("json");
        cmd.add("-show_streams");
        cmd.add("-show_format");
        cmd.add(new File(input).getAbsolutePath());
        return cmd;
    }


    public static List<String> interuptVideo(){
        String input = "C:\\Users\\TF\\Desktop\\testvideo\\2.mp4";
        String output = "C:\\Users\\TF\\Desktop\\testvideo\\split.mp4";
        List<String> cmd = new ArrayList<>();
        cmd.add("-ss");
        cmd.add("00:00:00");
        cmd.add("-t");
        cmd.add("00:00:05");
        cmd.add("-i");
        cmd.add(new File(input).getAbsolutePath());
        cmd.add("-vcodec");
        cmd.add("copy");
        cmd.add("-acodec");
        cmd.add("copy");
        cmd.add("-y");
        cmd.add(new File(output).getAbsolutePath());
        return cmd;
    }


    // ffmpeg -f concat -safe 0 -i c.txt -c copy c5.mp4







    public static class Work implements Runnable{

        @Override
        public void run() {

        }
    }



}
