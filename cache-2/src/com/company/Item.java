package com.company;

import java.util.ArrayList;

public class Item {
    public int weight;
    public double value;

    public Item(int weight,double value)
    {
        this.weight=weight;
        this.value=value;
    }

    public static class BruteForce {


        public int n;
        // public  Instance inst;
        //  public ArrayList<Item> items;
        public ArrayList<Item> p;
        public ArrayList<Item> bestt;
        public int waga=0;
        public double wartosc=0;
        public int waga_plecaka=10;




        public void GetSolution(ArrayList<Item> items,int ww) {

            n = items.size();
            p = new ArrayList();
            bestt = new ArrayList();
            waga_plecaka=ww;

            double bw = 0;
            for (int i = 0; i <= 2 << n; i++) {

                for (int j = 0; j < n; j++) {
                    if ((2 << j & i) == 0) {
                        if (waga + items.get(j).weight <= waga_plecaka) {
                            //System.out.print(inst.items.get(j).getWaga()+ " ");
                            wartosc += items.get(j).value;
                            waga += items.get(j).weight;
                            p.add(items.get(j));
                        }
                    }
                }
                if (bw <= wartosc) {
                    bw = wartosc;
                    bestt.clear();

                    for (int l = 0; l < p.size(); l++) {
                        bestt.add(p.get(l));
                    }
                    ;

                }
                //System.out.println("kkk");System.out.println("");
                p.clear();
                wartosc = 0;
                waga = 0;

            }
           /* System.out.println(bestt.size());

            for(int i=0;i<bestt.size();i++)
            {
                System.out.println(bestt.get(i).weight);
                System.out.println(bestt.get(i).value);
            }*/
        }
    }
}
