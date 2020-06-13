package com.company;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.*;

public class Cache {

    private Map<Long, Solution> map;

    private ArrayList<Long> seeds;
    public int licz = 0, unlicz = 0, size = 1,all=0;

    public Cache(Map<Long, Solution> m) {
        this.map = m;
        this.seeds = new ArrayList<Long>();
    }

    //sprubuj z stringami later
    public synchronized void put(Long seed, Solution solution) {
       /* Set<Long>key=weak.keySet();
        for(long k:key)
        {
            if((Object)(k) ==null)
            {
                hash.remove(k);
                System.out.println("GC");
            }

        }*/
        // weak.put(new StringBuilder(seed.toString()),solution);
        while (map.size() >= size && seeds.size() > 0) {
            map.remove(seeds.get(0));
            seeds.remove(0);
           // licz--;
        }
        all++;
        if (!map.containsKey(seed)) {
            map.put(seed, solution);
            seeds.add(seed);
            //weak.put(seed,solution);
            licz++;
            return;
        }
        unlicz++;
    }

    public synchronized int size() {
        return map.size();
    }

    public synchronized void setSize(int s) {
        this.licz=0;this.unlicz=0;this.size = s;all=0;
        map.clear();seeds.clear();
    }


}
