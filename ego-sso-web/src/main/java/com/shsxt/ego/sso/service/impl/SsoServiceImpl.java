package com.shsxt.ego.sso.service.impl;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.common.util.CookieUtils;
import com.shsxt.ego.common.util.JsonUtils;
import com.shsxt.ego.rpc.pojo.TbUser;
import com.shsxt.ego.rpc.service.IUserService;
import com.shsxt.ego.sso.service.ISsoService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Service
public class SsoServiceImpl implements ISsoService {

    @Resource
    public IUserService userServiceProxy;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public EgoResult userCheck(String param, Integer type) {
        return userServiceProxy.userCheck(param,type);
    }

    @Override
    public EgoResult saveUser(TbUser user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        return userServiceProxy.saveUser(user);
    }

    @Override
    public EgoResult loginCheck(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        EgoResult result = new EgoResult();
        TbUser user = userServiceProxy.queryUserByuserName(username);
        if (null == user) {
            result.setStatus(500);
            result.setMsg("该用户不存在！");
            return result;
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!user.getPassword().equals(password)){
            result.setStatus(500);
            result.setMsg("密码不正确");
            return result;
        }
        /**
         *  用户信息保存
         *      使用redis存储
         *      key方式
         *      userId 不安全
         *      随机唯一的字符串 uuid token(令牌机制) jwt(生成token令牌)
         */
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, JsonUtils.objectToJson(user));

        /**
         *  将token响应给客户端
         */
        CookieUtils.setCookie(request,response,"sso_token",token);
        result.setMsg("用户登录成功");
        result.setData(token);
        return result;
    }

    @Override
    public EgoResult userInfo(String token) {
        TbUser user=JsonUtils.jsonToPojo((String)redisTemplate.opsForValue().get(token),TbUser.class);
        EgoResult result =new EgoResult();
        result.setData(user);
        return result;
    }

    @Override
    public EgoResult userLogout(String token,HttpServletRequest request,HttpServletResponse response) {
        redisTemplate.delete(token);
        CookieUtils.deleteCookie(request,response,token);
        return new EgoResult();
    }
}
