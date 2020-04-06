package com.company;

import sorting.AbstractFloatSorter;
import sorting.FloatElement;
import sorting.IElement;

import java.io.File;
import java.io.IOException;
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

public class Console {

    static List<Class>classList;
    static  void loadClass()
    {


        final File file = new File("Classes/sorting");

        try {
            // Convert File to a URL
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls);

            System.out.println("załadowano :" );

            for (final File fileEntry : file.listFiles()) {
                String name = fileEntry.getName().replace(".class", "");
                Class c = cl.loadClass("sorting."+name);
                System.out.println( name);
                classList.add(c);


            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public synchronized void print(String s){System.out.println(s);}

//-XX:SoftRefLRUPolicyMSPerMB=0
    public static void main(String[] args){

        classList=new ArrayList<>();
        synchronized (classList) {
            loadClass();
        }

        Console console=new Console();
        ReferenceMap map=new ReferenceMap();
        HashMap<Long,List<IElement>>strong=new HashMap<>();
        WeakHashMap<Long,List<IElement>>weak=new WeakHashMap<>();

        for(int i=0;i<10;i++) {
            ThreadS thread = new ThreadS(i,classList, map, console);
           // ThreadS thread = new ThreadS(i,classList, strong, console,map);
          // ThreadS thread = new ThreadS(i,classList, weak, console,map);

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
            synchronized (map) {
                f = (float) map.m1 / (float) map.g1;
                f2=(float) map.m2 / (float) map.g2;
                map.g2=0;
                map.m2=0;

                console.print("Wynik m1/g1= " + f + " m2/g2= " + f2+" size= "+map.hashMap.size());
               // console.print("Wynik m1/g1= " + f + " m2/g2= " + f2+" size= "+strong.size());


            }



        }



    }
}
