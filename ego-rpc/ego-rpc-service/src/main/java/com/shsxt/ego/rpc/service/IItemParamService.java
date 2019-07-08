package com.shsxt.ego.rpc.service;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.common.model.PageResult;
import com.shsxt.ego.rpc.pojo.TbItemParam;
import com.shsxt.ego.rpc.query.ItemParamQuery;

public interface IItemParamService {

    public PageResult<TbItemParam> queryItemListByParams(ItemParamQuery itemParamQuery);

    TbItemParam queryItemParamByItemCatId(Long itemCatId);

    EgoResult saveItemParam(TbItemParam itemParam);

    EgoResult deleteItemParamBatch(Long[] ids);
}
