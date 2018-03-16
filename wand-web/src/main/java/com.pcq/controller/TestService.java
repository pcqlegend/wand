package com.pcq.controller;

import com.pcq.mapper.AppDOMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
@Controller
public class TestService {
    @RequestMapping(value = "/testModel",method = RequestMethod.GET)
    public String testModel(Model model, @RequestParam("name") String name){
        System.out.println(name);
        model.addAttribute("user",name);
        return "testModel";
    }
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        ModelAndView modelAndView=new ModelAndView("user");
        User user=new User();
        user.setName("pcq");
        user.setAge(28);
        modelAndView.addObject("testModelAndView",user);
        return modelAndView;
    }
    @RequestMapping("/testJson")
    @ResponseBody
    public User test3(){
        User user=new User();
        user.setName("pcq");
        user.setAge(28);
        return user;
    }

    @Resource
    AppDOMapper appDOMapper;
    @RequestMapping("/testMapper")
    @ResponseBody
    public User testMapper(){
        appDOMapper.query(1);
        return null;
    }

}
