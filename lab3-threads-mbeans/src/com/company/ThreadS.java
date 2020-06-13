package com.company;

import sorting.FloatElement;
import sorting.IElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static java.lang.Thread.sleep;

public class ThreadS extends Thread {

    int MAX=1000;                   //1000- 0.9 trafien szybko zapelniomy zawolniony cache
    int MIN=1;                      //100- 0.7-0.6
                                    //30 - 0.3-0.2

    public  List<IElement> list;
    public  List<Class>listOfAlgorithms;
    public ReferenceMap referenceMap;
    public Console console;
    private long seed;
    private Class aClass=null;
    private int id;
    public boolean run=true;
    private HashMap<Long,List<IElement>>strong=null;
    private WeakHashMap<Long,List<IElement>>weak=null;
    private Method method=null;
    private Object object;
    private Object[] longs;

    private int size;

    public ThreadS(int id,List<Class>listOfAlgorithms,ReferenceMap map,Console console)
    {
        list= new ArrayList<IElement>();
        this.listOfAlgorithms=listOfAlgorithms;
        this.referenceMap=map;
        this.console=console;
        this.id=id;

    }

    public ThreadS(int id,List<Class>listOfAlgorithms,HashMap<Long,List<IElement>>strong,Console console,ReferenceMap map,int size)
    {
        this.size=size;
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

    public void generateSeed()
    {
        //list=new ArrayList<IElement>();

        list.clear();
        Random random = new Random();
        seed =random.nextInt((MAX - MIN) + 1)+MIN;

        random.setSeed(seed);

        /*
        100 *(4B+4B)=800 Byte
        >400 nie moze być
         */
        for (int i=0;i<50;i++)
        {
            list.add(new FloatElement("111",random.nextFloat()));
        }

    }

    public  void chooseAlgorithm()
    {
        synchronized (listOfAlgorithms) {
            if (listOfAlgorithms.isEmpty()) return;

            aClass = listOfAlgorithms.get((int)seed%2);
           // console.print("class========="+aClass+" "+seed)
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


            if(strong!=null){
                synchronized (strong) {
                    if(console.DEFAULT_size<=strong.size()){

                        while (strong.size()>=console.DEFAULT_size)
                        {
                            longs=strong.keySet().toArray();

                            if (strong.containsKey(longs[0]))
                            {
                                strong.remove(longs[0]);
                                break;

                            }


                        }
                    }
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




        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while (run)
        {
            generateSeed();
           if(aClass==null) chooseAlgorithm();
           if(method==null) reflection();
           sort();
            console.print(id+" seed: "+seed+ " class: "+aClass+" "+" size limit: "+console.DEFAULT_size+" size: "+strong.size());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
