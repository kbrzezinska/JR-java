package com.company;

import sorting.IElement;

import java.lang.ref.ReferenceQueue;
import java.util.*;

//-XX:SoftRefLRUPolicyMSPerMB=0

public class ReferenceMap extends AbstractMap {


    public int size=0;
    public ReferenceQueue<SoftWithKey> referenceQueue;
    public HashMap<Long,SoftWithKey>hashMap;
    public int g1=0,g2=0,m1=0,m2=0;
    public ReferenceMap()
    {
        referenceQueue=new ReferenceQueue<SoftWithKey>();
        hashMap=new HashMap<Long, SoftWithKey>();
    }





/*
usuwa z hashmap elementy , ktore gc umiescil w queue, czyli usunał
 */
    public synchronized void CheckQueue() throws InterruptedException {
        int CacheSize=0;

        int hh=hashMap.size();

        SoftWithKey soft=(SoftWithKey) referenceQueue.poll();

        while (soft!=null) {

            hashMap.remove(soft.key);
            //referenceQueue.remove();
                CacheSize++;

            soft=(SoftWithKey) referenceQueue.poll();

        }

    }


    public synchronized void put(long seed,List<IElement>list) throws InterruptedException {
        g1++;
        g2++;
        System.gc();

        CheckQueue();
        if(!hashMap.containsKey(seed))
        {

            hashMap.put(seed,new SoftWithKey(list,seed,referenceQueue));
            m1++;
            m2++;

        }

    }

    @Override
    public Set<Entry> entrySet() {
        throw new UnsupportedOperationException();    }
}
