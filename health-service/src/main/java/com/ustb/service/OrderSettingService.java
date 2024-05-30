package com.ustb.service;


import com.ustb.entity.Result;
import com.ustb.pojo.OrderSetting;
import org.springframework.web.multipart.MultipartFile;

public interface OrderSettingService {
    Result add(MultipartFile excelFile);

    Result findOrderSettingByDate(String date);

    Result addOrUpdateByDate(OrderSetting orderSetting);
}
