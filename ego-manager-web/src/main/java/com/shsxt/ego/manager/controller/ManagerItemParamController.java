package com.shsxt.ego.manager.controller;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.common.model.PageResult;
import com.shsxt.ego.manager.service.IManagerItemParamService;
import com.shsxt.ego.rpc.pojo.TbItemParam;
import com.shsxt.ego.rpc.query.ItemParamQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ManagerItemParamController {

    @Resource
    private IManagerItemParamService managerItemParamService;

    @RequestMapping("item/param/list")
    @ResponseBody
    public PageResult<TbItemParam> queryItemParamListByParams(ItemParamQuery itemParamQuery){
        return managerItemParamService.queryItemParamListByParams(itemParamQuery);
    }

    @RequestMapping({"item/param/query/{itemCatId}","item/param/query/itemcatid/{itemCatId}"})
    @ResponseBody
    public EgoResult queryItemParamByItemCatId(@PathVariable Long itemCatId){

        return managerItemParamService.queryItemParamByItemCatId(itemCatId);

    }

    @RequestMapping("item/param/save/{itemCatId}")
    @ResponseBody
    public EgoResult saveItemParam(@PathVariable Long itemCatId,String paramData){
        return managerItemParamService.saveItemParam(itemCatId,paramData);
    }

    @RequestMapping("item/param/delete")
    @ResponseBody
    public EgoResult deleteItemParam(Long[] ids){
        return managerItemParamService.deleteItemParamBatch(ids);
    }



}
