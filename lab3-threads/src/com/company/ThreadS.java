package com.company;

import sorting.FloatElement;
import sorting.IElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static java.lang.Thread.sleep;

public class ThreadS extends Thread {

    int MAX=290;                   //
    int MIN=1;                      //90- 0.46
                                    //200 -0.65
                                    //290 -0.75-0.76
                                    //40 - 0.25-0.26

    public  List<IElement> list;
    public  List<Class>listOfAlgorithms;
    public ReferenceMap referenceMap;
    public Console console;
    private long seed;
    private Class aClass=null;
    private int id;
    private HashMap<Long,List<IElement>>strong=null;
    private WeakHashMap<Long,List<IElement>>weak=null;
    private Method method=null;
    private Object object;



    public ThreadS(int id,List<Class>listOfAlgorithms,ReferenceMap map,Console console)
    {
        list= new ArrayList<IElement>();
        this.listOfAlgorithms=listOfAlgorithms;
        this.referenceMap=map;
        this.console=console;
        this.id=id;

    }

    public ThreadS(int id,List<Class>listOfAlgorithms,HashMap<Long,List<IElement>>strong,Console console,ReferenceMap map)
    {
         list= new ArrayList<IElement>();
        this.listOfAlgorithms=listOfAlgorithms;
        this.console=console;
        this.id=id;
        this.strong=strong;
        this.referenceMap=map;

    }
    public ThreadS(int id, List<Class>listOfAlgorithms, WeakHashMap<Long,List<IElement>>weak, Console console,ReferenceMap map)
    {
        list= new ArrayList<IElement>();
        this.listOfAlgorithms=listOfAlgorithms;
        this.console=console;
        this.id=id;
        this.weak=weak;
        this.referenceMap=map;

    }
    public ThreadS()
    {


    }

    public void generateSeed()  {
         list=new ArrayList<IElement>();
       // list.clear();
        Random random = new Random();
        seed =random.nextInt((MAX - MIN) + 1)+MIN;

        random.setSeed(seed);
        /*
        500 *(21B+4B)=7500 Byte
         */
        for (int i=0;i<500;i++)
        {
            list.add(new FloatElement(new StringBuilder("123456789012345678901").toString(),random.nextFloat()));
        }

    }

    public  void chooseAlgorithm()
    {
        synchronized (listOfAlgorithms) {
            if (listOfAlgorithms.isEmpty()) return;

            aClass = listOfAlgorithms.get((int)seed%2);
        }
    }


    public void reflection()
    {
       if(aClass==null)return;;
        try {

            Constructor constructor = aClass.getConstructor();
             object = (Object) constructor.newInstance();
             method = aClass.getMethod("solve2", List.class);


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void sort()
    {

        try {
            list= (List<IElement>) method.invoke(object,new Object[]{list});

           if(strong==null && weak==null) {
               referenceMap.put(seed, list);
           }
            if(strong!=null){
                synchronized (strong) {
                    referenceMap.g1++;
                    referenceMap.g2++;
                   // sleep(100);

                    if (!strong.containsKey(seed)){
                        strong.put(seed, list);
                        referenceMap.m1++;
                        referenceMap.m2++;
                    }

                }
            }
            if(weak!=null){

                synchronized (weak) {
                    referenceMap.g1++;
                    referenceMap.g2++;

                    if (!weak.containsKey(seed)){
                        weak.put(seed, list);
                        referenceMap.m1++;
                        referenceMap.m2++;
                    }
                }
            }




        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while (true)
        {
            generateSeed();
            if(aClass==null) chooseAlgorithm();
            if(method==null) reflection();
            sort();

            console.print(id+" seed: "+seed+ " class: "+aClass+" ");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
