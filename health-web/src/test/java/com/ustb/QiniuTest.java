package com.ustb;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * @author 柳新宽
 * @create 2023-09-23  9:23
 * @describe
 */
public class QiniuTest {
    @Test
    public void upload(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "hgMPh87FdF_aokHYBJA3bjr0z3XEXGTqv2-iNXdc";
        String secretKey = "1-AHFV9-_4DSdwxh4C7iwp2Wn5eygmZ1TM46xH48";
        String bucket = "ustb-health";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "D:\\1.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "2.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                System.err.println(ex.response);
                try {
                    String body = ex.response.toString();
                    System.err.println(body);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
