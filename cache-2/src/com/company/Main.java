package com.company;


import javax.management.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import static java.lang.Thread.sleep;
//VM options -Xmx1024k
//jconsole -debug -J"-Djava.rmi.server.hostname=server.public.ip.address"
public class Main {
    public static ArrayList<Class>classes;
    private static final int DEFAULT_NO_THREADS=5;
    private static final int DEFAULT_CACHE=50;


    public static void  main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException, IOException, InterruptedException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {


        classes=new ArrayList<>();
        File folder = new File("Classes/com/company");
       // List<Class> classes = new ArrayList<Class>();
        URL url = folder.toURI().toURL();//cl.getResource(pack);

        URL[] classUrls = new URL[]{ url };
        ClassLoader ucl = new URLClassLoader(classUrls, Main.class.getClassLoader());
        for (final File fileEntry : folder.listFiles()) {
            String name = fileEntry.getName().replace(".class", "");
            System.out.println("name"+ name);
            Class c = ucl.loadClass("com.company." + name);

            classes.add(c);

        }

        SoftHashMap cache=new SoftHashMap();
        Konsola konsola=new Konsola();
        WeakHashMap<Long,Solution> weak=new WeakHashMap<Long,Solution>();
       HashMap<Long,Solution> strong=new HashMap<Long,Solution>();
       Cache cachee=new Cache(strong);cachee.setSize(DEFAULT_CACHE);
        int n=DEFAULT_NO_THREADS,k=2;
        ArrayList<Instance> instances = new ArrayList<Instance>();
        ArrayList<Thread>threads=new ArrayList<Thread>();


        //Get the MBean server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        //register the MBean
        SystemConfig mBean = new SystemConfig(DEFAULT_NO_THREADS, DEFAULT_CACHE,cachee,classes,threads,konsola,instances);
        ObjectName nameO = new ObjectName("com.company:type=SystemConfig");
        mbs.registerMBean(mBean, nameO);





        //Thread[] threads=new Thread[n];
      for (int i = 0; i < n; i++)
        {
            int j=i%3;
            instances.add(new Instance(2,i,cache,cachee,classes.get(j),konsola));
        }


       for (int i = 0; i < n; i++)
        {
           // threads[i]=new Thread(instances[i] );
            threads.add(new Thread(instances.get(i) ));
        }

        for (int i = 0; i < n; i++)
        {
            threads.get(i).start();


        }

        while (true)
        {
            String s;
           sleep(500);
          if(k==1)s=("size: "+cache.size()+" licz: "+cache.licz+" pudlo: "+cache.unlicz);
          else{s=("all="+cachee.all+" licz: "+cachee.licz+" pudlo-jest w cache: "+cachee.unlicz+" cache:"+cachee.size);};
          konsola.ss(s);

        }
    }


    static void loadClass() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, MalformedURLException {


        classes=new ArrayList<>();
        File folder = new File("Classes/com/company");
        //  listFilesForFolder(folder);



        List<Class> classes = new ArrayList<Class>();
        URL url = folder.toURI().toURL();//cl.getResource(pack);

        URL[] classUrls = new URL[]{ url };
        ClassLoader ucl = new URLClassLoader(classUrls, Main.class.getClassLoader());
        for (final File fileEntry : folder.listFiles()) {
            String name = fileEntry.getName().replace(".class", "");
            System.out.println("name"+ name);
            Class c = ucl.loadClass("com.company." + name);
            System.out.println(c.getName());

            classes.add(c);
            // ClassLoader classLoader = this.getClass().getClassLoader();
            // Class loadedMyClass = classLoader.loadClass(s);
            System.out.println("Loaded class name: " + c.getName());

            Constructor constructor = c.getConstructor();
            Object myClassObject = constructor.newInstance();
            Method[] methods = c.getMethods();
            for (Method mm : methods)

            {Parameter[] parameters=mm.getParameters();
                for (Parameter parameter : parameters) {

                    System.out.println(mm.getName());
                    System.out.println(parameter.getType());}}

           // Method method = c.getMethod("GetSolution", java.util.ArrayList.class);
            //System.out.println("Invoked method name: " + method.getName());
            //method.invoke(myClassObject,items);

           // m.add(method);
          //  o.add(myClassObject);

        }
    }
}
