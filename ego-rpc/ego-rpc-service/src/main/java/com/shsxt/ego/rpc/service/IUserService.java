package com.shsxt.ego.rpc.service;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.pojo.TbUser;

public interface IUserService {
    public TbUser queryUserByUserId(Long userId);

    EgoResult userCheck(String param, Integer type);

    EgoResult saveUser(TbUser user);

    public TbUser queryUserByuserName(String userName);
}
