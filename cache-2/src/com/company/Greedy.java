package com.company;

import java.util.ArrayList;

public class Greedy {

    public ArrayList<Double> sr;
    public ArrayList<Item>bestt;
    public int waga_plecaka=10;

    public int waga=0;
    public double wartosc=0;
    public int n;

    public void GetSolution(ArrayList<Item>items,int ww)

    {
        waga_plecaka=ww;
        waga=0;wartosc=0;
        sr=new ArrayList();
        bestt=new ArrayList();
        for(int i=0;i<items.size();i++)
        {
            double u=(double)(items.get(i).value)/(double)(items.get(i).weight);

            sr.add(u);
        }

        double best=0;
        int l=0;

        n=items.size();

        for(int j=0;j<n && waga<=waga_plecaka;j++)
        { best=0;

            for(int i=0;i<items.size();i++)
            {
                if(best<=sr.get(i))
                {
                    best=sr.get(i);l=i;
                }
            }


            if(waga+items.get(l).weight<=waga_plecaka)
            {
                waga+=items.get(l).weight;

                wartosc+=items.get(l).value;
                bestt.add(items.get(l));


            }

            items.remove(l);
            sr.remove(l);

        }


    }

}

