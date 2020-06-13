package com.company;

public class Konsola {
    private Boolean konsola=true;


    public synchronized void print(String s)
    {
        System.out.println(s);

    }

    public synchronized void ss(String s)
    {
        System.out.println(s);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
