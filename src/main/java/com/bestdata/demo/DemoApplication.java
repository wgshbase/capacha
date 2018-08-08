package com.bestdata.demo;

import com.bestdata.demo.config.MyCommondRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAutoConfiguration
// public class DemoApplication {
public class DemoApplication {

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        /*try {
            new MyCommondRunner().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
