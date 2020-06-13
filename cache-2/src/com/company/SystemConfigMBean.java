package com.company;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

public interface SystemConfigMBean {


    public void setThreadCount(int noOfThreads) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException, InterruptedException;
    public int getThreadCount();

    public void setCache(int size);
    public int getCache();

    // any method starting with get and set are considered
    // as attributes getter and setter methods, so I am
    // using do* for operation.
    public String doConfig();
}
