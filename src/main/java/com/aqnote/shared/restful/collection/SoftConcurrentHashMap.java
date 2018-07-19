package com.aqnote.shared.restful.collection;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类SoftConcurrentHashMap.java的实现描述: soft对象，避免该对象作为全局变量时导致内存溢出情况， 
 * 由于这个特性，该对象不能存储非常重要的信息，建议只作jvm级别缓存用
 * 
 * @author madding.lip Sep 17, 2013 4:36:28 PM
 */
@SuppressWarnings("rawtypes")
public class SoftConcurrentHashMap extends AbstractMap {

    private Map            map;
    private ReferenceQueue rq = new ReferenceQueue();

    public SoftConcurrentHashMap(){
        this.map = new ConcurrentHashMap();
    }

    class ValueReference extends SoftReference {

        private final Object key;

        @SuppressWarnings("unchecked")
        ValueReference(Object k, Object v){
            super(v, rq);
            this.key = k;
        }
    }

    private void processQueue() {
        ValueReference sv = null;
        while ((sv = (ValueReference) rq.poll()) != null) {
            map.remove(sv.key);
        }
    }

    public Object get(Object key) {
        ValueReference value = (ValueReference) map.get(key);
        if (value == null) return null;
        if (value.get() == null) {
            map.remove(value.key);
            return null;
        } else {
            return value.get();
        }
    }

    @SuppressWarnings("unchecked")
    public Object put(Object k, Object v) {
        processQueue();
        return map.put(k, new ValueReference(k, v));
    }

    public Set entrySet() {
        return map.entrySet();
    }

    public void clear() {
        processQueue();
        Set keys = map.keySet();
        for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
            Object name = iterator.next();
            map.remove(name);
        }
    }

    public int size() {
        processQueue();
        return map.size();
    }

    public Object remove(Object k) {
        processQueue();
        ValueReference value = (ValueReference) map.remove(k);
        if (value == null) return null;
        if (value.get() != null) {
            return value.get();
        }
        return null;
    }
}
