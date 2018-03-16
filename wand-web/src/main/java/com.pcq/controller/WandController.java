package com.pcq.controller;

import com.pcq.WMethod;
import com.pcq.domain.AppDO;
import com.pcq.domain.HostDO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcq on 2017/10/9 0009.
 */
@Controller
public class WandController {
    @RequestMapping("/appList")
    public ModelAndView queryAllApp() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "pcq");
        AppDO appDO = new AppDO();
        appDO.setName("pixiu");
        appDO.setOwner("pcq");
        List<AppDO> list = new ArrayList<AppDO>();
        list.add(appDO);
        modelAndView.addObject("list", list);
        modelAndView.setViewName("appList");
        return modelAndView;
    }

    @RequestMapping("/hostList")
    public ModelAndView queryAllHost(@RequestParam String app) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "pcq");
        HostDO hostDO = new HostDO();
        hostDO.setName("pixiu-production");
        hostDO.setIp("10.1.1.1");
        hostDO.setPort("7070");

        List<HostDO> list = new ArrayList<HostDO>();
        list.add(hostDO);
        modelAndView.addObject("list", list);
        modelAndView.setViewName("hostList");
        return modelAndView;
    }

    @RequestMapping("/methodList")
    public ModelAndView queryAllMethod(@RequestParam String ip, @RequestParam String port) {
        ModelAndView modelAndView = new ModelAndView();
        List<WMethod> methodList = new ArrayList<WMethod>();
        WMethod wMethod = new WMethod();
        wMethod.setClassName("com.pcq");
        wMethod.setMethodDesc("测试方法");
        wMethod.setMethodName("testFunction");
        WMethod wMethod2 = new WMethod();
        wMethod2.setClassName("com.pcq.testService");
        wMethod2.setMethodDesc("测试方法1");
        wMethod2.setMethodName("testFunction");
        methodList.add(wMethod);
        methodList.add(wMethod2);
        modelAndView.addObject("list", methodList);
//        modelAndView.addObject("list", list);
        modelAndView.setViewName("methodList");
        modelAndView.addObject("ip", ip);
        modelAndView.addObject("port", port);
        return modelAndView;
    }

//    @RequestMapping("/invoke")
//    public ModelAndView queryAllMethod(@RequestParam String ip, @RequestParam String port,) {
//
//        return modelAndView;
//    }
}
