package com.company;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

public interface SystemConfigMBean {

    public void setThreadCount(int noOfThreads) ;
    public int getThreadCount();

    public void setSize(int size);
    public int getSize();

    // any method starting with get and set are considered
    // as attributes getter and setter methods, so I am
    // using do* for operation.
    public String doConfig();

}
