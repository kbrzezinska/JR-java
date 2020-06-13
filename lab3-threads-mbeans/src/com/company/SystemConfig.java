package com.company;

import sorting.IElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SystemConfig implements SystemConfigMBean {
    private int ThreadCount;
    public static   int size;
    private HashMap<Long, List<IElement>> strong=null;
    private ReferenceMap map;
    private List<Class> classList;
    private ArrayList<ThreadS>threads;
    private Console console;

    public SystemConfig(int numThreads, int  size, HashMap hashMap, List<Class> classes,ArrayList<ThreadS>threads,Console console,ReferenceMap referenceMap){
        this.size=size;
        this.map=referenceMap;
        this.classList=classes;
        this.threads=threads;
        this.console=console;
        this.strong=hashMap;
        setThreadCount(numThreads);

    }

    public SystemConfig(){}

    @Override
    public void setThreadCount(int noOfThreads) {
        if(noOfThreads>-1) {
            this.ThreadCount = noOfThreads;
        }


        while(threads==null ||noOfThreads>threads.size())
        {
            //stworz thread
            threads.add(new ThreadS(threads.size(),classList, strong, console,map,this.size));
            threads.get(threads.size()-1).start();
        }

        while(noOfThreads<threads.size())
        {

            threads.get(threads.size()-1).run=false;
            try {
                threads.get(threads.size()-1).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threads.remove(threads.size()-1);
        }
    }

    @Override
    public int getThreadCount() {
        return ThreadCount;
    }

    @Override
    public void setSize(int ssize) {

        console.DEFAULT_size=ssize;
        this.size=ssize;
        synchronized (strong) {

            while (size < strong.size()) {

                Object[] longs = strong.keySet().toArray();
                if (strong.containsKey(longs[0])) {
                    strong.remove(longs[0]);
                }
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String doConfig() {
        synchronized (strong) {
            float f = (float) map.m1 / (float) map.g1;
            return "Wynik m1/g1= " + f+"\n zapelnienie: "+(float)strong.size()/(float)size+" \n watki: "+getThreadCount();
        }
    }
}
