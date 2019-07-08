package com.shsxt.ego.manager.service.impl;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.common.model.PageResult;
import com.shsxt.ego.common.util.IDUtils;
import com.shsxt.ego.manager.service.IManagerItemService;
import com.shsxt.ego.rpc.pojo.TbItem;
import com.shsxt.ego.rpc.pojo.TbItemDesc;
import com.shsxt.ego.rpc.pojo.TbItemParamItem;
import com.shsxt.ego.rpc.query.ItemQuery;
import com.shsxt.ego.rpc.service.IItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ManagerItemServiceImpl implements IManagerItemService {
    @Resource
    private IItemService itemServiceProxy;
    @Override
    public PageResult<TbItem> itemList(ItemQuery itemQuery) {
        return itemServiceProxy.queryItemsListByParams(itemQuery);
    }

    @Override
    public EgoResult reshelf(Long[] ids) {
        return itemServiceProxy.updateItemStatusBatch(ids,1);
    }

    @Override
    public EgoResult instock(Long[] ids) {
        return itemServiceProxy.updateItemStatusBatch(ids,2);
    }

    @Override
    public EgoResult deleteItemBatch(Long[] ids) {
        return itemServiceProxy.deleteItemBatch(ids);
    }

    @Override
    public EgoResult saveItem(TbItem tbItem, String itemDesc,String paramData) {

        // 设置商品id uuid
        Long itemId = IDUtils.genItemId();
        Date time = new Date();
        tbItem.setId(itemId);
        tbItem.setCreated(time);
        tbItem.setUpdated(time);
        tbItem.setStatus((byte) 1);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(itemDesc);
        tbItemDesc.setCreated(time);
        tbItemDesc.setUpdated(time);

        /**
         * 商品规格记录 待实现
         */
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setParamData(paramData);
        tbItemParamItem.setUpdated(time);
        tbItemParamItem.setCreated(time);
        return itemServiceProxy.saveItem(tbItem, tbItemDesc,tbItemParamItem);
    }
}
