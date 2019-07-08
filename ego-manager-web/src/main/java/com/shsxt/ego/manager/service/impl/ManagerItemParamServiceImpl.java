package com.shsxt.ego.manager.service.impl;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.common.model.PageResult;
import com.shsxt.ego.manager.service.IManagerItemParamService;
import com.shsxt.ego.rpc.pojo.TbItemParam;
import com.shsxt.ego.rpc.query.ItemParamQuery;
import com.shsxt.ego.rpc.service.IItemParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ManagerItemParamServiceImpl implements IManagerItemParamService {

    @Resource
    private IItemParamService itemParamServiceProxy;

    @Override
    public PageResult<TbItemParam> queryItemParamListByParams(ItemParamQuery itemParamQuery) {
        return itemParamServiceProxy.queryItemListByParams(itemParamQuery);
    }

    @Override
    public EgoResult queryItemParamByItemCatId(Long itemCatId) {
        TbItemParam tbItemParam =itemParamServiceProxy.queryItemParamByItemCatId(itemCatId);
        EgoResult egoResult = new EgoResult();
        if (null != tbItemParam) {
            egoResult.setData(tbItemParam);
            egoResult.setMsg("该模板已存在");
        }
        return egoResult;
    }

    @Override
    public EgoResult saveItemParam(Long itemCatId, String paramData) {

        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(itemCatId);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        return itemParamServiceProxy.saveItemParam(itemParam);
    }

    @Override
    public EgoResult deleteItemParamBatch(Long[] ids) {
        return itemParamServiceProxy.deleteItemParamBatch(ids);
    }


}
