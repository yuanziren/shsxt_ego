package com.shsxt.ego.sso.service.impl;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.service.IUserService;
import com.shsxt.ego.sso.service.ISsoService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SsoServiceImpl implements ISsoService {

    @Resource
    public IUserService userServiceProxy;

    @Override
    public EgoResult userCheck(String param, Integer type) {
        return userServiceProxy.userCheck(param,type);
    }
}
