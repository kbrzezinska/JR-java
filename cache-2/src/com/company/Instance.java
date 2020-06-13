package com.company;


import sun.awt.Mutex;

import java.io.File;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class Instance implements Runnable{

    public AbstractMap cache;    //key,value
        //key,value
   public Cache cachee;
    public int mod=10;
    public ArrayList<Item>items;
    public long seed;
    public int id;
    private Method m;
    private Object o;
    private double solutionValue;
    private Class classes;
    private Konsola konsola;
    public boolean run=true;
    private int i=0;

    public Instance(int i,int id,AbstractMap cache, Cache cachee, Class c,Konsola konsola) throws NoSuchMethodException, IllegalAccessException, InstantiationException, MalformedURLException, InvocationTargetException, ClassNotFoundException
    {
        this.i=i;
        this.konsola=konsola;
        this.id=id;
      if(i==1)  this.cache=cache ;
      else  {this.cachee=cachee ;}
        this.classes=c;
        loadClass();

    }

    public void generator(long seed)
    {
        seed=seed;
        Random random=new Random();
        random.setSeed(seed);
        int r=random.nextInt(100);

        items=new ArrayList<>();
        double value;
        int weight;

        for(int i=0;i<r%mod;i++)
        {
            value=(random.nextDouble()*10)%mod;
            if(value<0)value=value*(-1);

            // System.out.println(value);

            weight=random.nextInt()%mod;
            if(weight<0)weight=weight*(-1);
            items.add(new Item(weight,(double)value));

        }
        // System.out.println("gen");
    }

    public void loadClass() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, MalformedURLException {



       // File folder = new File("Classes/com/company");
        //  listFilesForFolder(folder);



       /* List<Class> classes = new ArrayList<Class>();
        URL url = folder.toURI().toURL();//cl.getResource(pack);

        URL[] classUrls = new URL[]{ url };
        ClassLoader ucl = new URLClassLoader(classUrls, Main.class.getClassLoader());
        for (final File fileEntry : folder.listFiles()) {
            String name = fileEntry.getName().replace(".class", "");
            System.out.println(name);
            */
            Class c = classes;//ucl.loadClass("com.company." + name);
          //  System.out.println(c.getName());

            // ClassLoader classLoader = this.getClass().getClassLoader();
            // Class loadedMyClass = classLoader.loadClass(s);
          //  System.out.println("Loaded class name: " + c.getName());

            Constructor constructor = c.getConstructor();
            Object myClassObject = constructor.newInstance();

            Class[] cArg = new Class[2];
            cArg[0] = java.util.ArrayList.class;
            cArg[1] = int.class;
            Method method = c.getMethod("GetSolution",cArg);

        m=method;
        o=myClassObject;

    }

    public void solving() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchFieldException {


        //Object myClassObject=loadClass("com.com.company.Item.BruteForce");

        m.invoke(o,items,30);
        Field field= o.getClass().getField("bestt");
        //System.out.println(field.getName());
        // System.out.println(field.getType());
        //System.out.println(myClassObject.getClass().getName());


        Object fieldVal =field.get(o);
        ArrayList<Item> arrayListField = (ArrayList<Item>)fieldVal;
        int sizeBestt =arrayListField.size();
        double maxValue=0;

        for(int i=0;i<sizeBestt;i++)
        {
            maxValue=maxValue+arrayListField.get(i).value;
            // System.out.println(arrayListField.get(i).value);
        }

        solutionValue=maxValue;
        Solution solution=new Solution(arrayListField,maxValue);
       if(i==1) cache.put(seed,solution);
       else{cachee.put(seed,solution);}
        // System.out.println("sol");

    }

    public synchronized void printf()
    {
        String s=id+" Watek: ziarno: "+seed+" algorytm: "+o.getClass().getName()+" rozwiazanie: "+solutionValue;
        konsola.print(s);

    }
    @Override
    public void run() {


        while(run) {

            try {
                Random random=new Random();
                seed=random.nextInt(100);
                generator(seed);
                //usypiamy wątek na 100 milisekund
                // Thread.sleep(1000);
                solving();
                // Thread.sleep(1000);
                printf();

                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Main {
    }
}
