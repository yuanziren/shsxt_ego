package com.shsxt.ego.rpc.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.service.ISmsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements ISmsService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public EgoResult sendSmsToPhone(String phone, Integer type) {

        EgoResult result = new EgoResult();
        if(type.equals(1000)){
            /**
             * 注册成功通知短信
             */
            doSendSms(phone,"SMS_168725412",null);
        }else if(type.equals(2000)){
            /**
             * 登录短信
             */
            String code="1562";
            doSendSms(phone,"SMS_168725412",code);
            String key="sms::phone::"+phone+"::type::SMS_168725412";
            redisTemplate.opsForValue().set(key,code,300, TimeUnit.SECONDS);
        }else  if(type.equals(3000)){
            /**
             * 找回密码
             */
            String code="1562";
            String key="sms::phone::"+phone+"::type::SMS_168725412";
            doSendSms(phone,"SMS_168725412",code);
            redisTemplate.opsForValue().set(key,code,300, TimeUnit.SECONDS);
        }else{
            System.out.println("短信类型非法!");
            result.setStatus(500);
            result.setMsg("短信类型非法!");
        }
        return result;
    }

    private void doSendSms(String phone, String templateCode, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAIftPLNzhgsHmx", "VgLO26MfrjUiEohAZVJ5YfLsjl8p3J");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "ego商城");
        request.putQueryParameter("TemplateCode", templateCode);
        if(!StringUtils.isEmpty(code)){
            request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        }
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
