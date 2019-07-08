package com.shsxt.ego.rpc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.common.model.PageResult;
import com.shsxt.ego.rpc.mapper.db.dao.TbItemParamMapper;
import com.shsxt.ego.rpc.pojo.TbItemParam;
import com.shsxt.ego.rpc.query.ItemParamQuery;
import com.shsxt.ego.rpc.service.IItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemParamServiceImpl implements IItemParamService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public PageResult<TbItemParam> queryItemListByParams(ItemParamQuery itemParamQuery) {
        PageHelper.startPage(itemParamQuery.getPage(),itemParamQuery.getRows());
        List<TbItemParam> itemParamList= tbItemParamMapper.queryItemListByParams(itemParamQuery);
        PageInfo<TbItemParam> pageInfo =new PageInfo<>(itemParamList);
        PageResult<TbItemParam> pageResult =new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(pageInfo.getList());
        return pageResult;
    }

    @Override
    public TbItemParam queryItemParamByItemCatId(Long itemCatId) {
        return tbItemParamMapper.queryItemParamByItemCatId(itemCatId);
    }

    @Override
    public EgoResult saveItemParam(TbItemParam itemParam) {
        tbItemParamMapper.insertSelective(itemParam);
        return new EgoResult();
    }

    @Override
    public EgoResult deleteItemParamBatch(Long[] ids) {
        tbItemParamMapper.deleteItemParamBatch(ids);
        return new EgoResult();
    }

}
