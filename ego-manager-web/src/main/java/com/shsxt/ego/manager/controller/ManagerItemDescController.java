package com.shsxt.ego.manager.controller;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.pojo.TbItemDesc;
import com.shsxt.ego.rpc.service.IItemDescService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ManagerItemDescController {

    @Resource
    private IItemDescService itemDescServiceProxy;

    @RequestMapping("/item/desc/{itemId}")
    @ResponseBody
    public EgoResult queryItemDescByItemId(@PathVariable Long itemId){
        TbItemDesc tbItemDesc = itemDescServiceProxy.queryItemDescByItemId(itemId);
        EgoResult egoResult = new EgoResult();
        if (null != tbItemDesc) {
            egoResult.setData(tbItemDesc);
        } else {
            egoResult.setStatus(500);
            egoResult.setMsg("记录不存在");
        }
        return egoResult;
    }
}
