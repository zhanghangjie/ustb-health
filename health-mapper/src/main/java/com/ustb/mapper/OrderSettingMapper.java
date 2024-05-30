package com.ustb.mapper;

import com.ustb.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface OrderSettingMapper {

    Long findCountByDate(Date date);

    void updateNumberByDate(@Param("date") Date date, @Param("nums") int nums);

    void add(@Param("date") Date date, @Param("nums") int nums);

    //由于传入两个参数，所以要注解Param
    List<OrderSetting> findOrderSettingByDate(@Param("startDate")String startDate,@Param("endDate") String endDate);

    OrderSetting findOrderSettingByDate2(Date orderDate);

    void editReservationsByDate(OrderSetting orderSetting);
}
