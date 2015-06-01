package io.github.dadarom.facebook.jcommon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by leo on 15-5-29.
 */
public class LoggingUtil {

    public static Logger getClassLogger() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stacktrace[2];
        String name = element.getClassName();

        return LoggerFactory.getLogger(name);
    }


    public static void main(String[] args){
        Logger logger = LoggingUtil.getClassLogger();
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stacktrace[2];
        String name = element.getClassName();
        for(StackTraceElement strace : stacktrace){
            logger.info("{}", stacktrace);
        }
        logger.info("\nname:{}",name);

    }


}
