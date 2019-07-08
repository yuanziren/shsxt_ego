package com.shsxt.ego.sso.service;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ISsoService {

    public EgoResult userCheck(String param, Integer type);

    EgoResult saveUser(TbUser user);

    EgoResult loginCheck(String username, String password, HttpServletRequest request, HttpServletResponse response);

    EgoResult userInfo(String token);

    EgoResult userLogout(String token, HttpServletRequest request, HttpServletResponse response);
}
