package com.shsxt.ego.sso.controller;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.pojo.TbUser;
import com.shsxt.ego.sso.service.ISsoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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



}
