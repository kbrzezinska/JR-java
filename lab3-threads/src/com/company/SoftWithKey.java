package com.company;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftWithKey extends SoftReference {

    public Object key;
    public SoftWithKey(Object referent,Object key, ReferenceQueue q) {
        super(referent, q);
        this.key=key;
    }

}
