package com.company;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * SoftHashMap
 */

public final class SoftHashMap extends AbstractMap {
    private Map hash = new HashMap();
    private final ReferenceQueue queue = new ReferenceQueue();
    public int licz=0,unlicz=0,sc=0;

    public SoftHashMap() {
    }

    public Object get(Object key) {
        Object res = null;
        SoftReference sr = (SoftReference)hash.get(key);
        if ( sr != null ) {
            res = sr.get();
            if ( res == null )
                hash.remove(key);
        }
        return res;
    }
//przechowuje tylko te referencje które zostaly usuniete przez grabbage kolektor,wiec te ktore sie w niej znajduja nie maga byc w chache
    private void processQueue() {
        for ( ;; ) {
            SoftValue sv = (SoftValue)queue.poll();
            if ( sv != null )
            { hash.remove(sv.key);}

            else
                return;
        }
    }

    public synchronized Object put(Object key, Object value) {
        if(!hash.containsKey(key)) {
         //   processQueue();
            //
            licz++;
            return hash.put(key, new SoftValue(value, key, queue));
        }
        unlicz++;
        return null;
    }

    public Object remove(Object key) {
        processQueue();
        return hash.remove(key);
    }

    public void clear() {
        processQueue();
        hash.clear();
    }

    public synchronized int size() {
      //  processQueue();
        return hash.size();
    }

    public Set entrySet() {
        /** @todo Figure this out */
        throw new UnsupportedOperationException();
    }


    /**
     * SoftValue
     */

    private static class SoftValue extends SoftReference {
        private final Object key;

        private SoftValue(Object k, Object key, ReferenceQueue q) {
            super(k, q);
            this.key = key;
        }
    }
}