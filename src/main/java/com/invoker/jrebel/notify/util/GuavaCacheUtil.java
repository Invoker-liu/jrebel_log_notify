package com.invoker.jrebel.notify.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2021/1/19-14:51
 *
 * @author <a href="mailto:javajason@163.com">Jason(LiuJianJun)</a>
 */
public class GuavaCacheUtil {
    final AtomicInteger evictionCount = new AtomicInteger();
    final AtomicInteger applyCount = new AtomicInteger();
    final AtomicInteger totalSum = new AtomicInteger();

    public static final String NOTIFY_KEY = "JREBEL:NOTIFY:KEY:%s";

    public static final int INIT_LOAD = 0;
    public static final int ALLOW_LIMIT = 2;


    public static final CacheLoader<String, AtomicInteger> LOADER =
            new CacheLoader<>() {
                @Override
                public AtomicInteger load(String key) throws Exception {
                    return new AtomicInteger(INIT_LOAD);
                }
            };

    public static final LoadingCache<String, AtomicInteger> NOTIFY_CACHE =
            CacheBuilder.newBuilder()
                        .concurrencyLevel(4)
                        .expireAfterWrite(10, TimeUnit.SECONDS)
                        .build(LOADER);

    public static boolean doNotify(String name) throws ExecutionException {
        AtomicInteger atomicInteger = NOTIFY_CACHE.get(String.format(NOTIFY_KEY, name));
        if (atomicInteger.get() < ALLOW_LIMIT) {
            atomicInteger.incrementAndGet();
            return false;
        }
        if (atomicInteger.get() == ALLOW_LIMIT) {
            atomicInteger.incrementAndGet();
            return true;
        }
        if (atomicInteger.get() > ALLOW_LIMIT) {
            atomicInteger.incrementAndGet();
            return false;
        }
        return false;
    }

}
