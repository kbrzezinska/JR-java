package com.company;

import java.util.ArrayList;
import java.util.Random;

public class RandomSearch {

    public ArrayList<Item> bestt;
    public int waga_plecaka=10;

    public int waga=0;
    public double wartosc=0;
    public int n;

    public void GetSolution(ArrayList<Item>items,int ww)
    {
        waga_plecaka=ww;
        bestt=new ArrayList();
        ArrayList<Item> o=new ArrayList();
        ArrayList<Item> b=new ArrayList();
        for(int i=0;i<items.size();i++)
        {
            o.add(items.get(i));
        }


        int l;
        double bw=0;

        n=items.size();
        Random r=new Random();
        for(int j=0;j<100;j++){

            wartosc=0;
            waga=0;
            bestt.clear();
            for(int i=0;i<n&& waga<=waga_plecaka;i++)
            {

                l=r.nextInt(items.size());
                if(waga+items.get(l).weight<=waga_plecaka)
                {
                    waga+=items.get(l).weight;
                    wartosc+=items.get(l).value;
                    bestt.add(items.get(l));

                    items.remove(l);

                }
            }
            if(wartosc>=bw)
            {b.clear();
                for(int i=0;i<bestt.size();i++)
                {
                    b.add(bestt.get(i));
                };

                bw=wartosc;
            }

            items.clear();
            for(int i=0;i<o.size();i++)
            {
                items.add(o.get(i));
            };//System.out.println("kk"+o.size());

        }
        bestt.clear();
        for(int i=0;i<b.size();i++)
        {
            bestt.add(b.get(i));
        };
    }
}
