package com.company;

import javafx.scene.effect.InnerShadow;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.*;

public class SystemConfig implements SystemConfigMBean {

    private int ThreadCount;
    private int Cache;
    private Cache cache;
    private ArrayList<Class> classes;
    private ArrayList<Thread>threads;
    private ArrayList<Instance>instances;
    private Konsola konsola;

    public SystemConfig(int numThreads, int schema, Cache cache, ArrayList<Class> classes,ArrayList<Thread>threads,Konsola konsola,ArrayList<Instance>instances){
        this.ThreadCount=numThreads;
        this.Cache=schema;
        this.cache=cache;
        this.classes=classes;
        this.threads=threads;
        this.konsola=konsola;
        this.instances=instances;
    }
    @Override
    public void setThreadCount(int noOfThreads) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException, InterruptedException {

        if(noOfThreads>-1) {
            this.ThreadCount = noOfThreads;
        }
        Random random=new Random();
        SoftHashMap soft=new SoftHashMap();
        while(noOfThreads>threads.size())
        {
            int i=random.nextInt()%3;
            int id=instances.get(instances.size()-1).id;
            Instance instance=new Instance(2,id+1,soft,cache,classes.get(i),konsola);
            instances.add(instance);
            threads.add(new Thread(instance));
            threads.get(threads.size()-1).start();
        }

        while(noOfThreads<threads.size())
        {

            instances.get(instances.size()-1).run=false;
            threads.get(instances.size()-1).join();
            instances.remove(instances.size()-1);
            threads.remove(instances.size()-1);
        }
    }

    @Override
    public int getThreadCount() {
        return ThreadCount;
    }

    @Override
    public void setCache(int size) {

        if(size>=1) {
            this.Cache = size;
            cache.setSize(size);
        }
    }

    @Override
    public int getCache() {
        return Cache;
    }

    @Override
    public String doConfig() {
        int free=this.cache.size-this.cache.size();
        return "No of Threads="+this.ThreadCount+" and size of cache="+this.Cache+"\nzajete="+this.cache.size()+" wolne="+free+" pudla="+(float)this.cache.unlicz/(float)(this.cache.all)+" threads="+this.threads.size();
    }
}
