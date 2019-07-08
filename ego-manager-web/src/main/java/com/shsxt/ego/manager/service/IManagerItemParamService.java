package com.shsxt.ego.manager.service;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.common.model.PageResult;
import com.shsxt.ego.rpc.pojo.TbItemParam;
import com.shsxt.ego.rpc.query.ItemParamQuery;

public interface IManagerItemParamService {

    public PageResult<TbItemParam> queryItemParamListByParams(ItemParamQuery itemParamQuery);

    EgoResult queryItemParamByItemCatId(Long itemCatId);

    EgoResult saveItemParam(Long itemCatId, String paramData);

    EgoResult deleteItemParamBatch(Long[] ids);
}
