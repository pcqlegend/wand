package com.pcq;

import com.pcq.mapper.AppDOMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by pcq on 2017/9/30 0030.
 */
public class MyBatisTes {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");
        AppDOMapper appDOMapper=(AppDOMapper) context.getBean("appDOMapper");

    }
}
