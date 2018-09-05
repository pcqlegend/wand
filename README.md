# WAND

## Overview
 Like [swagger](https://swagger.io/) ,wand provide a visual and simple tool to improve out efficiency of our development and test in SrpingFrameWork web application. Without this tool ,we should test our each interface with a controller,and then use browser to test. But when the method is post and  you have too many parameter ,this operation may be upsetting. If there is a ui can provide user to test interface with simple form ,the work may be very easier and more effective. Wand just is the tool you need.
## QuickStart
### First: import maven  pom.xml in your project

````
<dependency>
	<groupId>com.pcq</groupId>
	<artifactId>wand-stand-alone-sdk</artifactId>
	<version>1.3.0</version>
</dependency>
````
### Second: inject WandServlet
For Spring Boot
````
@SpringBootApplication
public class DemoApplication {

    @Bean
    public ServletRegistrationBean testServletRegistration() {
        WandStandAloneServlet servlet = new WandStandAloneServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean(servlet);
        registration.addUrlMappings("/wand/*");
        Map<String, String> params = new HashMap<>();
        params.put("secretKey", "wand");
        registration.setInitParameters(params);
        return registration;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
````
### Third: add wand method Annotation for your interface
````
    @WandMethod(desc = "test", params = "user_name&user_id")
````
````
@Service
public class TestService {
    
    @WandMethod(desc = "test", params = "user_name&user_id")
    public User queryUser(String name, Integer id) {
        User user = new User();
        user.setId("123");
        user.setName("admin");
        return user;
    }
}
````
### Fourth: visit url 
````
http://localhost:8080/wand/methodList?secretKey=wand
````



## Latest releases

