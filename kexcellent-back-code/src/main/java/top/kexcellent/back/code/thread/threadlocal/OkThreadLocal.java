/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.thread.threadlocal;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author kanglele
 * @version $Id: OkThreadLocal, v 0.1 2023/3/9 9:40 kanglele Exp $
 */
public class OkThreadLocal<T> extends ThreadLocal<T> {

    private static final ThreadLocal<WeakHashMap<OkThreadLocal<Object>,Object>> holder = new ThreadLocal<WeakHashMap<OkThreadLocal<Object>,Object>>() {
        @Override
        protected WeakHashMap<OkThreadLocal<Object>, Object> initialValue() {
            return new WeakHashMap<>();
        }

        protected WeakHashMap<OkThreadLocal<Object>, Object> childValue(WeakHashMap<OkThreadLocal<Object>, Object> parentValue) {
            return new WeakHashMap<>(parentValue);
        }
    };

    public static Object capture() {
        HashMap<OkThreadLocal<Object>,Object> capture = new HashMap<OkThreadLocal<Object>,Object>();
        for(OkThreadLocal<Object> threadLocal : holder.get().keySet()){
            capture.put(threadLocal,threadLocal.copyValue());
        }
        return capture;
    }

    private T copyValue() {
        return this.get();
    }

    public static Object replay(Object captured) {
        HashMap<OkThreadLocal<Object>,Object> backup = new HashMap<OkThreadLocal<Object>,Object>();
        for(OkThreadLocal<Object> threadLocal : holder.get().keySet()){
            backup.put(threadLocal,threadLocal.get());
            if(!((HashMap<OkThreadLocal<Object>,Object>)captured).containsKey(threadLocal)){
                threadLocal.remove();
            }
        }

        setThreadLocalValue((HashMap<OkThreadLocal<Object>,Object>)captured);
        return backup;
    }

    private static void setThreadLocalValue(HashMap<OkThreadLocal<Object>,Object> value) {
        for(Map.Entry<OkThreadLocal<Object>,Object> entry : value.entrySet()){
            OkThreadLocal<Object> threadLocal =  entry.getKey();
            threadLocal.set(entry.getValue());
        }
    }

    public static void restore(Object backup) {
        for(OkThreadLocal<Object> threadLocal : holder.get().keySet()){
            if(!((HashMap<OkThreadLocal<Object>,Object>)backup).containsKey(threadLocal)){
                threadLocal.remove();
            }
        }
        setThreadLocalValue((HashMap<OkThreadLocal<Object>,Object>)backup);
    }

    @Override
    public void set(T value) {
        if(null==value) remove();
        super.set(value); //调用 ThreadLocal 的 set
        addThisToHolder();  // 把当前的 OkThreadLocal 对象塞入 hold 中。
    }

    @Override
    public T get() {
        T value = super.get();
        if (value != null) addThisToHolder();
        return value;
    }

    private void addThisToHolder() {
        if(!holder.get().containsKey(this)){
            holder.get().put((OkThreadLocal<Object>) this,null);
        }
    }


}
