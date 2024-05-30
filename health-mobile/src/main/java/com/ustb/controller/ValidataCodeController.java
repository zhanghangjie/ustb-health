package com.ustb.controller;

import com.ustb.constant.MessageConstant;
import com.ustb.entity.Result;
import com.ustb.util.SMSUtil;
import com.ustb.util.ValidateCodeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validateCode/")
public class ValidataCodeController {

    @RequestMapping("sendmessage")
    public Result sendmessage(String telephone){
        //发送验证码 生成6位数，并通过valueOf强制转化为String
        String  code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
        String param = "{\"code\":\""+code+"\"}";
        try {
            SMSUtil.sendMessage(SMSUtil.TEMPLATE_CODE_VALIDATE_CODE,telephone,param);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL,null);
        }
        //验证码存储在那里   企业级解决方案:redis   临时HashMap代替
        //根据用户输入的手机号，从map中取出code，与用户上传的code验证码进行比对，成功则验证码成功
        SMSUtil.map.put(telephone,code);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS,null);
    }
}
