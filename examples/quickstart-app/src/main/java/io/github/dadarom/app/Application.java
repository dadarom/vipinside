package io.github.dadarom.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by leo on 15-5-27.
 */
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class.getName());

    public static void main(String[] args){
        logger.info("App start with args:{}", args.toString());
    }

}
