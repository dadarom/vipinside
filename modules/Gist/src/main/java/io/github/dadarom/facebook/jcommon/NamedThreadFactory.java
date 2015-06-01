package io.github.dadarom.facebook.jcommon;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 15-5-29.
 */
public class NamedThreadFactory implements ThreadFactory {
    private final String baseName;
    private final AtomicInteger threadNum = new AtomicInteger(0);

    public NamedThreadFactory(String baseName) {
        this.baseName = baseName;
    }

    @Override
    public synchronized Thread newThread(Runnable r) {
        Thread t = Executors.defaultThreadFactory().newThread(r);

        t.setName(baseName + "-" + threadNum.getAndIncrement());

        return t;
    }
}
