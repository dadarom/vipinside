package io.github.dadarom.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

/**
 * Created by leo on 15-5-27.
 */
@Service
@ImportResource("classpath:applicationContext.xml")
public class Application {

    @Value("${zk}")
    private String zkStr;

    private static Logger logger = LoggerFactory.getLogger(Application.class.getName());

    public static void main(String[] args){
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        Application app = context.getBean(Application.class);
        app.start(args);
    }

    private void start(String[] args){
        logger.info("App start with args:{}", args.toString());
    }

}
