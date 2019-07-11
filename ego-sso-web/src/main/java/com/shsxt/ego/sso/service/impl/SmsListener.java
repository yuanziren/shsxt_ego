package com.shsxt.ego.sso.service.impl;

import com.shsxt.ego.rpc.service.ISmsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SmsListener {

    @Resource
    private ISmsService smsServiceProxy;

    public void listen(String msg){
        String mg[] =msg.split("&");
        String phone=mg[0];
        Integer type=Integer.parseInt(mg[1]);
        /**
         * 发送短信
         */
        smsServiceProxy.sendSmsToPhone(phone,type);
    }



}
