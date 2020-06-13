package com.company;

import sorting.IElement;

import java.lang.ref.ReferenceQueue;
import java.util.*;
//Dzień dobry, mam pytanie do zadań z laboratorium z Javy, czy wystarczy stworzyć
// cache tylko dla soft references czy trzeba również stworzyć dla weak i strong references?

public class ReferenceMap extends AbstractMap {

    int size=0;

    public ReferenceQueue<SoftWithKey> referenceQueue;
    public HashMap<Long,SoftWithKey>hashMap;
    public LinkedList linkedList=new LinkedList();
    public int g1=0,g2=0,m1=0,m2=0;
    public ReferenceMap()
    {
        linkedList=new LinkedList();
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
//-XX:SoftRefLRUPolicyMSPerMB=0
        while (soft!=null) {

            hashMap.remove(soft.key);
            referenceQueue.remove();
          //  if( size==0) {
                CacheSize++;

              /*  if(CacheSize<=hh/2){
                    linkedList.addFirst(soft);
                    hashMap.put((Long) soft.key,soft);
                }
            }*/
            soft=(SoftWithKey) referenceQueue.poll();
        }

        if (size==0 && CacheSize>0){
            size=CacheSize;
            System.out.println(size+" "+hashMap.size()+" "+hh);
            System.exit(0);
            }
    }


    public synchronized void put(long seed,List<IElement>list) throws InterruptedException {
        g1++;
        g2++;
        CheckQueue();
        if(!hashMap.containsKey(seed))
        {
            CheckQueue();
            hashMap.put(seed,new SoftWithKey(list,seed,referenceQueue));
            m1++;
            m2++;

        }

    }

    public synchronized int getSize()
    {
        return size;
    }
    @Override
    public Set<Entry> entrySet() {
        throw new UnsupportedOperationException();    }
}
