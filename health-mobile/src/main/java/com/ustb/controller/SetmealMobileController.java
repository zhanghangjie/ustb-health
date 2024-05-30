package com.ustb.controller;

import com.ustb.entity.Result;
import com.ustb.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/setmeal/")
public class SetmealMobileController {

    @Resource
    private SetmealService setmealService;

    @RequestMapping("getSetmeal")
    public Result getSetmeal(){
        //查询所有的套餐信息
        return setmealService.getSetmeal();
    }

    @RequestMapping("findById")
    public Result findById(Integer id){
        return setmealService.findSetmealAndGroupAndItemById(id);
    }

    @RequestMapping("findSetmealById")
    public Result findSetmealById(Integer id){
        return setmealService.findSetmealById(id);
    }
}
