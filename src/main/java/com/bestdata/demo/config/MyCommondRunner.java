package com.bestdata.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author wgsh
 * @Date wgshb on 2018/8/7 14:48
 */
@Component
public class MyCommondRunner implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(MyCommondRunner.class);

    @Value("${spring.web.loginurl}")
    private String loginUrl;

    @Value("${spring.web.googleExecutePath}")
    private String googleExecutePath;

    @Value("${spring.auto.openUrl}")
    private Boolean isOpen;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(isOpen + "=====" + googleExecutePath + "========== " + loginUrl);
        if(isOpen) {
            String cmd = googleExecutePath + " " + loginUrl;
            Runtime run = Runtime.getRuntime();

            try {
                run.exec(cmd);
                logger.debug("启动浏览器打开项目成功");
                System.out.println("==启动浏览器打开项目成功==");
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("==项目打开浏览器未遂==" + e.getMessage());
            }
        }
    }
}
