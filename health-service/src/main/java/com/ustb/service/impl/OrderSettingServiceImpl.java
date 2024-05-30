package com.ustb.service.impl;

import com.ustb.constant.MessageConstant;
import com.ustb.entity.Result;
import com.ustb.mapper.OrderSettingMapper;
import com.ustb.pojo.OrderSetting;
import com.ustb.service.OrderSettingService;
import com.ustb.util.POIUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {
    @Resource
    private OrderSettingMapper orderSettingMapper;
    @Override
    public Result add(MultipartFile excelFile) {
        List<String[]> list = null;
        try {
            list = POIUtils.readExcel(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL,null);
        }
        if(list!=null && list.size()>0){
            for (String[] strings : list) {
                System.out.println(strings[0]);
                System.out.println(strings[1]);
                Date date = new Date(strings[0]);
                int nums = Integer.parseInt(strings[1]);
                //判断当前日期是否已经设置了预约数量
                Long count = orderSettingMapper.findCountByDate(date);
                if(count>0){
                    //修改
                    orderSettingMapper.updateNumberByDate(date,nums);
                }else{
                    //新增
                    orderSettingMapper.add(date,nums);
                }
            }
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS,null);
    }
    
    @Override
    public Result findOrderSettingByDate(String date) {
        String startDate = date+"-01";
        String endDate = date+"-31";
        List<OrderSetting> list = null;
        
        try{
            list = orderSettingMapper.findOrderSettingByDate(startDate,endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL,null);
        }
        //List的泛型是HashMap,Map后面是KV结构<Object,Object>，类似于json结构(leftobj里的结构)
        //leftobj里的内容是 String:Integer,String:Integer,String:Integer
        List<HashMap<String,Integer>> listData = new ArrayList<>();
        if(list!=null&&list.size()>0){
            //遍历数据库中查询出的list集合
            for (OrderSetting orderSetting : list) {
                //创建每一天的数据map
                HashMap<String, Integer> map = new HashMap<>();
                //获取每月的第几天
                map.put("date", orderSetting.getOrderDate().getDate());
                //获取每天的总预约量
                map.put("number", orderSetting.getNumber());
                //获取每天已经预约的数量
                map.put("reservations", orderSetting.getReservations());
                listData.add(map);
            }
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,listData);
        }
        //该月没有预约数据
        return new Result(true,"当前月份没有进行预约设置",null);
    }

    @Override
    public Result addOrUpdateByDate(OrderSetting orderSetting) {
        //根据时间在数据库查找OrderSetting是否存在，判断是增加数据还是修改数据
        //获取单独日期的信息不是批量信息，创建findOrderSettingByDate2()方法
        OrderSetting order = orderSettingMapper.findOrderSettingByDate2(orderSetting.getOrderDate());
        if(order!=null){
            //当天设置过预约数据,本次操作属于修改数据
            if(order.getReservations()<=orderSetting.getNumber()){
                //已经预约数量小于当前设置的预约总数 可以修改
                orderSettingMapper.updateNumberByDate(orderSetting.getOrderDate(),orderSetting.getNumber());
            }else{
                //不可修改
                return new Result(false,MessageConstant.ORDERSETTING_FAIL,null);
            }
        }else{
            //数据库为空，属于新增预约数据
            orderSettingMapper.add(orderSetting.getOrderDate(),orderSetting.getNumber());
        }
        //修改设置成功
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,null);
    }
}
