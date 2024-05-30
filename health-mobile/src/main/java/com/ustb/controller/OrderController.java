package com.ustb.controller;

import com.ustb.constant.MessageConstant;
import com.ustb.entity.Result;
import com.ustb.pojo.Order;
import com.ustb.service.OrderService;
import com.ustb.util.SMSUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/order/")
public class OrderController {

    @Resource
    private OrderService orderService;

    @RequestMapping("submitOrder")
    /*由于order实体类中并不包含传入的this.orderInfo中的数据(手机号，验证码，身份证号，体检日期),无法用具体的对象接收
    这一组数据,所以要用到map来接收,请求体格式的数据类型是json，所以用RequestBody注解*/
    public Result submitOrder(@RequestBody Map map){
        //判断验证码是否正确
        //取出手机号
        String telephone = (String)map.get("telephone");
        //取出用户上传的验证码
        String validateCodeForUser = (String)map.get("validateCode");
        //根据手机号取出数据源的验证码
        String validateCodeForDS = SMSUtil.map.get(telephone);
        if(validateCodeForDS== null || validateCodeForDS.equals("")){
            //手机号填写错误
            return new Result(false, MessageConstant.TELEPHONE_VALIDATECODE_NOTNULL,null);
        }
        if(!validateCodeForDS.equals(validateCodeForUser)){
            //验证码输入有误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR,null);
        }
        //验证码校验成功
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        //默认状态下预约状态为未到诊
        map.put("orderStatus",Order.ORDERSTATUS_NO);
        return orderService.submitOrder(map);
    }

    @RequestMapping("findById")
    public Result findById(Integer id){
        return orderService.findById(id);
    }
}
