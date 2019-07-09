package com.shsxt.ego.cart.interceptors;

import com.shsxt.ego.common.util.CookieUtils;
import com.shsxt.ego.common.util.JsonUtils;
import com.shsxt.ego.rpc.pojo.TbUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1.获取cookie token
         * 2.查询缓存中对应的用户信息
         *     存在  放行
         * 3.不存在
         *     重定向到登录页面
         */

        String token = CookieUtils.getCookieValue(request,"sso_token");
        System.out.println(token);
        String uri=request.getRequestURL().toString();
        System.out.println(uri);
        if(!StringUtils.isEmpty(token)){
            TbUser user= JsonUtils.jsonToPojo((String)redisTemplate.opsForValue().get(token), TbUser.class);
            System.out.println(user);
            if(null !=user){
                /**
                 * 已登录
                 */
                return true; // 放行  之后目标方法
            }else{
                /**
                 * 执行重定向
                 */
                response.sendRedirect("http://127.0.0.1:8083/login?redirect="+uri);
                return false;
            }
        }
        /**
         * 执行重定向
         */
        response.sendRedirect("http://127.0.0.1:8083/login?redirect="+uri);
        return false;
    }
}
