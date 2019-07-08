package com.shsxt.ego.manager.controller;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.common.model.PageResult;
import com.shsxt.ego.manager.service.IManagerItemService;
import com.shsxt.ego.rpc.pojo.TbItem;
import com.shsxt.ego.rpc.query.ItemQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ManagerItemController {
    @Resource
    private IManagerItemService managerItemService;


    @RequestMapping("item/list")
    @ResponseBody
    public PageResult<TbItem> itemList(ItemQuery itemQuery){
        return managerItemService.itemList(itemQuery);
    }

    /**
     * 商品上架
     * @param ids
     * @return
     */
    @RequestMapping("item/reshelf")
    @ResponseBody
    public EgoResult reshelf(Long[] ids){
        return managerItemService.reshelf(ids);
    }

    /**
     * 商品下架
     * @param ids
     * @return
     */
    @RequestMapping("item/instock")
    @ResponseBody
    public EgoResult instock(Long[] ids){
        return managerItemService.instock(ids);
    }

    @RequestMapping("item/delete")
    @ResponseBody
    public EgoResult delete(Long[] ids){
        return managerItemService.deleteItemBatch(ids);
    }

    @RequestMapping("item/save")
    @ResponseBody
    public EgoResult saveItem(TbItem item,String desc,String itemParams){
        return managerItemService.saveItem(item,desc,itemParams);
    }

}
