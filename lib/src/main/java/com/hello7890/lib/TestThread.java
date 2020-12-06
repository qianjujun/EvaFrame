package com.hello7890.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThread {
    private static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(4,8,1, TimeUnit.SECONDS,
            new PriorityBlockingQueue<>(100));
    public static void main(String[] args) {


        new Thread(new CheckTask()).start();


        List<Integer> list = new ArrayList<>();
        for(int i = 0;i<100;i++){
            list.add(i);
        }

        //Collections.shuffle(list);
        System.out.println(list);
        for(Integer integer:list){
            executor.execute(new Task(integer));
        }
    }

    private static Random random = new Random();
    public static class Task implements Runnable,Comparable<Task>{

        private int level;




        @Override
        public int compareTo(Task o) {
            //return level - o.level;
            return 0;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500+random.nextInt(800));
                queue.put(level);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this+"  "+Thread.currentThread().getName() + "   size:"+queue.size());
        }

        @Override
        public String toString() {
            return "Task{" +
                    "level=" + level +
                    '}';
        }

        public Task(int level) {
            this.level = level;
        }
    }


    public static class CheckTask implements Runnable{

        private Map<Integer,List<Integer>> cache = new HashMap<>();

        @Override
        public void run() {
            while (true){
                try {
                    int result = queue.take();



                    if(result>=50){
                       // System.out.println("Over:"+result);
                    }else {
                        int temp = result % 10;
                        List<Integer> list;
                        if(cache.containsKey(temp)){
                            list = cache.get(temp);
                        }else {
                            list = new ArrayList<>();
                            cache.put(temp,list);
                        }

                        list.add(result);
                        //System.out.println(list);
                        if(list.size()<5){
                        }else {
                            cache.remove(temp);
                            int total = 0;
                            for(Integer data:list){
                                total += data;
                            }
                            executor.execute(new Task(total+1000));
                        }

                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
