package io.github.dadarom.facebook.jcommon;

import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 15-5-29.
 * Blocked Retry
 */
public class RetryCounterFactory {
    private final int maxRetries;
    private final int retryIntervalMillis;

    private RetryCounterFactory(int maxRetries, int retryIntervalMillis) {
        this.maxRetries = maxRetries;
        this.retryIntervalMillis = retryIntervalMillis;
    }

    public RetryCounter create() {
        return new RetryCounter(
                maxRetries, retryIntervalMillis, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) throws Exception {

        RetryCounterFactory factory = new RetryCounterFactory(5, 10);
        RetryCounter counter = factory.create();
        while (true) {
            try {
                /***
                 * do something...
                 */
                return;
            } catch (Exception e) {
                if (!counter.shouldRetry()) {
                    throw e;
                }

                counter.sleepUntilNextRetry();
                counter.useRetry();
            }
        }
    }

    private static class RetryCounter {
        private final int maxRetries;
        private int retriesRemaining;
        private final int retryIntervalMillis;
        private final TimeUnit timeUnit;

        private RetryCounter(int maxRetries, int retryIntervalMillis, TimeUnit timeUnit) {
            this.maxRetries = maxRetries;
            this.retriesRemaining = maxRetries;
            this.retryIntervalMillis = retryIntervalMillis;
            this.timeUnit = timeUnit;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void sleepUntilNextRetry() throws InterruptedException {
            timeUnit.sleep(retryIntervalMillis);
        }

        public boolean shouldRetry() {
            return retriesRemaining > 0;
        }

        public void useRetry() {
            retriesRemaining--;
        }

    }

}
