package com.pcq;

import javax.annotation.Resource;

/**
 * Hello world!
 *
 */
public class App 
{
    @Resource
    UserService userService;
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
