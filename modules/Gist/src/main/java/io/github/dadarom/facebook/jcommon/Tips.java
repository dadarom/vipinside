package io.github.dadarom.facebook.jcommon;

import com.facebook.concurrency.NamedThreadFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by leo on 15-5-29.
 */
public class Tips {

    private final ExecutorService watchExecutor = Executors.newSingleThreadExecutor(
            new NamedThreadFactory("ZkApplication-watch")
    );

    private volatile boolean isStarted = false;

    private CountDownLatch initLatch = new CountDownLatch(1);

    public synchronized void start() {
        if (isStarted) {
            throw new IllegalStateException("Should only be started once");
        }

        /***
         * init && startup()
         */

        isStarted = true;
        // Allow watch signals to pass only after initialization
        initLatch.countDown();
    }


    /***
     * 注意这里没有用timeout await
     */
    public void a(){
        watchExecutor.execute(new com.facebook.concurrency.ErrorLoggingRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    initLatch.await(); // Wait until we have been fully initialized
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                /***
                 * do something else...
                 */
            }
        }));
    }

    /***
     * start()最后initLatch.countDown(),[initLatch.await()]可以限制connectionWather内,需待init完毕后(万一watch的执行快于start()内的init())
     */
    public void b(){
        initLatch.countDown();
        try {
            initLatch.await();
            System.out.println("await end..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Tips tips = new Tips();
        tips.b();

    }
}
