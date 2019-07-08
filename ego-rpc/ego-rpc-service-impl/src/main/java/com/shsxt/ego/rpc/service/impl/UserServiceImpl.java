package com.shsxt.ego.rpc.service.impl;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.mapper.db.dao.TbUserMapper;
import com.shsxt.ego.rpc.pojo.TbUser;
import com.shsxt.ego.rpc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private TbUserMapper userMapper;
    @Override
    public TbUser queryUserByUserId(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public EgoResult userCheck(String param, Integer type) {
        EgoResult egoResult=new EgoResult();
        TbUser user= userMapper.userCheck(param,type);
        if(user !=null){
            egoResult.setData(false);
        }else{
            egoResult.setData(true);
        }
        return egoResult;
    }

}
