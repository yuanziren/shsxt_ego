package com.shsxt;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.shsxt.ego.rpc.service.ISmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:spring/applicationContext-dao.xml",
        "classpath:spring/applicationContext-service.xml",
        "classpath:spring/applicationContext-tx.xml",
        "classpath:spring/applicationContext-redis.xml"} )
public class TestSms {

    @Resource
    private ISmsService smsService;

    @Test
    public void test01(){

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAIftPLNzhgsHmx", "VgLO26MfrjUiEohAZVJ5YfLsjl8p3J");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "18895693163");
        request.putQueryParameter("SignName", "ego商城");
        request.putQueryParameter("TemplateCode", "SMS_168725412");
        request.putQueryParameter("TemplateParam", "{\"code\":\"1935\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test02(){
        smsService.sendSmsToPhone("18895693163",2000);
    }
}
