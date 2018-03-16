package com.pcq.controller;

import com.pcq.annotation.WandMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
@Controller
public class StudentService {
    @RequestMapping("/query")
    @ResponseBody
    @WandMethod(desc = "查询")
    public String query(){
        return "123";

    }
}
