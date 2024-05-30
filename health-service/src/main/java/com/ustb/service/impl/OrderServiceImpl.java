package com.ustb.service.impl;

import com.ustb.constant.MessageConstant;
import com.ustb.entity.Result;
import com.ustb.mapper.MemberMapper;
import com.ustb.mapper.OrderMapper;
import com.ustb.mapper.OrderSettingMapper;
import com.ustb.mapper.SetmealMapper;
import com.ustb.pojo.Member;
import com.ustb.pojo.Order;
import com.ustb.pojo.OrderSetting;
import com.ustb.service.OrderService;
import com.ustb.service.SetmealService;
import com.ustb.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderSettingMapper orderSettingMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private SetmealMapper setmealMapper;
    @Override
    public Result submitOrder(Map map) {
        //1.检查用户选择的预约日期是否被管理员已经设置
        String orderDate = (String)map.get("orderDate");
        OrderSetting orderSetting = orderSettingMapper.findOrderSettingByDate2(DateUtils.parseString2Date(orderDate));
        if(orderSetting == null){
            //用户选择的日期 管理员没有设置 不可预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER,null);
        }
        //2.用户选择的预约日期是否已经预约满
        if(orderSetting.getNumber()<=orderSetting.getReservations()){
            return new Result(false, MessageConstant.ORDER_FULL,null);
        }
        //3.检查同一天用户是否存在重复预约
        String telephone = (String)map.get("telephone");
        //3.1 根据手机号查询用户信息
        Member member = memberMapper.findByPhone(telephone);
        //3.2 判断是否是已经注册用户
        if(member!=null){
            //此用户已经是会员
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(DateUtils.parseString2Date(orderDate));
            order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
            List<Order> orders = orderMapper.findByCondition(order);
            if(orders!=null && orders.size()>0){
                //说明数据库中已经存在重复的预约信息
                return new Result(false, MessageConstant.HAS_ORDERED,null);
            }
        }else{
            //新用户
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String)map.get("idCard"));
            member.setSex((String)map.get("sex"));
            member.setRegTime(new Date());
            memberMapper.add(member);
        }
        //预约代码
        Order addOrder = new Order();
        addOrder.setMemberId(member.getId());
        addOrder.setOrderType((String)map.get("orderType"));
        addOrder.setOrderStatus((String)map.get("orderStatus"));
        addOrder.setOrderDate(DateUtils.parseString2Date(orderDate));
        addOrder.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        orderMapper.add(addOrder);
        //预约设置中已预约数量需要+1
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingMapper.editReservationsByDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,addOrder.getId());
    }

    @Override
    public Result findById(Integer id) {
        Order order = orderMapper.findById(id);
        String memberName = memberMapper.getNameById(order.getMemberId());
        String setmealName = setmealMapper.getNameById(order.getSetmealId());
        HashMap<String, String> map = new HashMap<>();
        map.put("member",memberName);
        map.put("setmeal",setmealName);
        map.put("orderDate",DateUtils.parseDate2String(order.getOrderDate()));
        map.put("orderType",order.getOrderType());
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
    }
}
