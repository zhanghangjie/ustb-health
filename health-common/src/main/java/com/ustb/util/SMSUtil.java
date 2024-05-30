package com.ustb.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SMSUtil {
    private static final String ACCESS_KEY = "LTAI5tKdL7k9sDAfp9phaMty";
    private static final String SECRET_KEY = "nYaLrCkvdysNu28J4Bfn0FgWz5LCNJ";
    public static final String TEMPLATE_CODE_VALIDATE_CODE = "SMS_248905322";
    public static HashMap<String,String> map = new HashMap<>();
    //发送短信的方法
    public static void sendMessage(String templateCode,String phone,String param) throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(ACCESS_KEY)
                // 您的 AccessKey Secret
                .setAccessKeySecret(SECRET_KEY);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        com.aliyun.dysmsapi20170525.Client client = null;
        try {
            client = new com.aliyun.dysmsapi20170525.Client(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("胖胖冬")
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phone)
                .setTemplateParam(param);
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(sendSmsRequest, runtime);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }
}