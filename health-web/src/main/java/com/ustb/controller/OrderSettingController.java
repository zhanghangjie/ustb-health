package com.ustb.controller;


import com.ustb.entity.Result;
import com.ustb.pojo.OrderSetting;
import com.ustb.service.OrderSettingService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("ordersetting/")
public class OrderSettingController{
    @Resource
    private OrderSettingService orderSettingService;
    @RequestMapping("upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){
        return orderSettingService.add(excelFile);

    }

    @RequestMapping("findOrderSettingByDate")
    public Result findOrderSettingByDate(String date){
        return orderSettingService.findOrderSettingByDate(date);
    }

    @RequestMapping("addOrUpdateByDate")
    //利用OrderSetting接受用户传送过来的数据(可预约人数+已预约人数),OrderSetting是一个对象,用户传入的是json数据
    public Result addOrUpdateByDate(@RequestBody OrderSetting orderSetting){
        return orderSettingService.addOrUpdateByDate(orderSetting);
    }
}
