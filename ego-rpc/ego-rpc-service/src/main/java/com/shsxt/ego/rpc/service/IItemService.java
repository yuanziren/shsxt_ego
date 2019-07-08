package com.shsxt.ego.rpc.service;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.pojo.TbItemDesc;
import com.shsxt.ego.rpc.pojo.TbItemParamItem;
import com.shsxt.ego.rpc.query.ItemQuery;
import com.shsxt.ego.common.model.PageResult;
import com.shsxt.ego.rpc.pojo.TbItem;

public interface IItemService {

    public PageResult<TbItem> queryItemsListByParams(ItemQuery itemQuery);

    public EgoResult updateItemStatus(TbItem tbItem);

    /**
     * 商品状态更新
     * @param ids 待更新商品id
     * @param type 更新类型
     * @return
     */
    public EgoResult updateItemStatusBatch(Long[] ids,int type);

    public EgoResult deleteItemBatch(Long[] ids);

    public EgoResult saveItem(TbItem item, TbItemDesc itemDesc, TbItemParamItem tbItemParamItem);
}
