package com.shsxt.ego.rpc.service.impl;

import com.shsxt.ego.rpc.mapper.db.dao.TbItemDescMapper;
import com.shsxt.ego.rpc.pojo.TbItemDesc;
import com.shsxt.ego.rpc.service.IItemDescService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ItemDescServiceImpl implements IItemDescService {

    @Resource
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public TbItemDesc queryItemDescByItemId(Long itemId) {
        return tbItemDescMapper.selectByPrimaryKey(itemId);
    }
}
