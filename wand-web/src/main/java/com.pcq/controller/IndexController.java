package com.pcq.controller;

import com.pcq.domain.AppDO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcq on 2017/9/30 0030.
 */

@Controller
public class IndexController {

    @RequestMapping("/index")
    @ResponseBody
    public List<AppDO> queryAll() {
        List<AppDO> list = new ArrayList<AppDO>();
        AppDO appDO = new AppDO();
        appDO.setId(1);
        appDO.setName("trippe");
        list.add(appDO);
        AppDO appDO2 = new AppDO();
        appDO.setId(2);
        appDO.setName("tripp");
        list.add(appDO2);
        return list;
    }


}
