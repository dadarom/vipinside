package io.github.dadarom;

import com.google.common.base.Function;
import io.github.dadarom.facebook.jcommon.LoggingUtil;
import io.github.dadarom.facebook.jcommon.RetryUntilSuccess;
import org.junit.Test;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/**
 * Created by leo on 15-5-29.
 */
public class RetryUntilSuccessTest {
    Logger LOG = LoggingUtil.getClassLogger();

    @Test
    public void a(){
        final long cur = System.currentTimeMillis();
        RetryUntilSuccess retry = new RetryUntilSuccess();
        retry.retry(new Function() {
            @Nullable
            @Override
            public Object apply(Object o) {
                if(System.currentTimeMillis() - cur < 10000){
                   /* try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LOG.info("sleep 500ms...");*/
                    int a = 1/0;//trigger exception

                }else{
                    try {
                        Thread.sleep(1000);
                        LOG.info("sleep 1s...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });
        LOG.info("wait until sleep???");
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("RetryUntilSuccess is in another thread.");
    }
}
