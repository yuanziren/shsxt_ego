package com.shsxt.ego.sso.controller;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.pojo.TbUser;
import com.shsxt.ego.sso.service.ISsoService;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SsoController {

    @Resource
    private ISsoService ssoService;

    @RequestMapping("user/check/{param}/{type}")
    @ResponseBody
    public EgoResult userCheck(@PathVariable String param,@PathVariable Integer type){
        return ssoService.userCheck(param,type);
    }
    @RequestMapping("user/register")
    @ResponseBody
    public EgoResult userRegister(TbUser user){
        return ssoService.saveUser(user);
    }

    @RequestMapping("user/login")
    @ResponseBody
    public EgoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response){
        return ssoService.loginCheck(username, password, request, response);
    }

    @RequestMapping("user/token/{token}")
    @ResponseBody
    public MappingJacksonValue userInfo(@PathVariable String token, @RequestParam(required = false) String  callback){
        EgoResult result=ssoService.userInfo(token);
        MappingJacksonValue value =new MappingJacksonValue(result);
        System.out.println(value);
        if(!StringUtils.isEmpty(callback)){
            value.setJsonpFunction(callback);
        }
        return value;
    }


    @RequestMapping("user/logout/{token}")
    @ResponseBody
    public MappingJacksonValue userLogout(@PathVariable String token, @RequestParam(required = false) String  callback,HttpServletRequest request,HttpServletResponse response){
        EgoResult result=ssoService.userLogout(token,request,response);
        MappingJacksonValue value =new MappingJacksonValue(result);
        if(!StringUtils.isEmpty(callback)){
            value.setJsonpFunction(callback);
        }
        return value;
    }
}
