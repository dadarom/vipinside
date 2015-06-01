package io.github.dadarom.facebook.jcommon;

import com.facebook.concurrency.ErrorLoggingRunnable;
import com.google.common.base.Function;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by leo on 15-5-29.
 * NonBlocked Retry
 */
public class RetryUntilSuccess {

    @Value("retryIntervalMillis")
    private String retryIntervalMillis;

    Logger LOG = LoggingUtil.getClassLogger();

    private final ScheduledExecutorService retryExecutor =
            Executors.newSingleThreadScheduledExecutor(
                    new NamedThreadFactory("RetryUntilSuccess-Thread")
            );
    private final AtomicBoolean isScheduled = new AtomicBoolean(false);

    public void retry(final Function func) {
        if (isScheduled.compareAndSet(false, true)) {
            retryExecutor.execute(new ErrorLoggingRunnable(new Runnable() {
                @Override
                public void run() {
                    try {
                        func.apply(null);
                        return; // Success, don't reschedule
                    } catch (Throwable e) {
                        // Try again after sleeping...
                        LOG.error("Failed to connect to ZooKeeper", e);
                    } finally {
                        isScheduled.set(false);
                    }
                    if (isScheduled.compareAndSet(false, true)) {
                        retryExecutor.schedule(
                                this, 1000 /*retryIntervalMillis*/, TimeUnit.MILLISECONDS
                        );
                    }
                }
            }));
        }
    }

    public void shutdown() {
        retryExecutor.shutdown();
    }
}
