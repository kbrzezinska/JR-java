package com.company;

import sorting.AbstractFloatSorter;
import sorting.IElement;

import javax.management.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

import static java.lang.Thread.sleep;

//jconsole -debug -J"-Djava.rmi.server.hostname=server.public.ip.address"


public class Console {
    private static final int DEFAULT_NO_THREADS=1;
    public int  DEFAULT_size=10;

    static List<Class>classList;
    static synchronized void loadClass()
    {


        final File file = new File("Classes/sorting");

        try {
            // Convert File to a URL
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls);

            System.out.println("załadowano :");

            for (final File fileEntry : file.listFiles()) {
                String name = fileEntry.getName().replace(".class", "");
                Class c = cl.loadClass("sorting." + name);
                System.out.println(name);
                classList.add(c);


            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }


    public synchronized void print(String s){System.out.println(s);}

//-XX:SoftRefLRUPolicyMSPerMB=0
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {

        classList=new ArrayList<>();
        loadClass();


        Console console=new Console();
        ReferenceMap map=new ReferenceMap();
        HashMap<Long,List<IElement>>strong=new HashMap<>();

        int  DEFAULT_size=10;

        ArrayList<ThreadS>threads=new ArrayList<>();
        //Get the MBean server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        //register the MBean
        SystemConfig mBean = new SystemConfig(DEFAULT_NO_THREADS, DEFAULT_size,strong,classList,threads,console,map);
        ObjectName nameO = new ObjectName("com.company:type=MyMBean");
        mbs.registerMBean(mBean, nameO);





        //WeakHashMap<Long,List<IElement>>weak=new WeakHashMap<>();
       // System.out.println(map.);

     /*   for(int i=0;i<30;i++) {
          //  ThreadS thread = new ThreadS(i,classList, map, console);
            ThreadS thread = new ThreadS(i,classList, strong, console,map);
            //ThreadS thread = new ThreadS(i,classList, weak, console,map);


            thread.start();
        }
        while (true)
        {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            float f,f2;
            synchronized (strong) {
                f = (float) map.m1 / (float) map.g1;
                f2=(float) map.m2 / (float) map.g2;
                console.print("Wynik m1/g1= " + f + " m2/g2= " + f2);
                map.g2=0;
                map.m2=0;

            }



        }
*/


    }
}
