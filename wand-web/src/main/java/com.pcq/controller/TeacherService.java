package com.pcq.controller;

import com.pcq.annotation.*;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
@Service
public class TeacherService {
    @WandMethod(params = "a")
    public String teach(String a){
        return a;
    }

    @WandMethod(params = "b",desc = "点名用的哈哈")
    public String dianMing(String b,int id,Integer integer,boolean istrue,Boolean isTrue){
        return b;
    }
}
