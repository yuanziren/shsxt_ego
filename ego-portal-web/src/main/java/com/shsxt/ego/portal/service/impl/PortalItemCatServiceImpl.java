package com.shsxt.ego.portal.service.impl;

import com.shsxt.ego.common.model.CatNode;
import com.shsxt.ego.portal.service.IPortalItemCatService;
import com.shsxt.ego.rpc.pojo.TbItemCat;
import com.shsxt.ego.rpc.service.IItemCatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortalItemCatServiceImpl implements IPortalItemCatService {

    @Resource
    private IItemCatService iItemCatServiceProxy;



    @Override
    public Map<String ,Object> getAllItemCats() {
        List<TbItemCat> list = iItemCatServiceProxy.queryAllItemCats();
        /**
         * 分类数据处理
         */
        List result = getChildren(0L,list);
        Map<String ,Object> map = new HashMap<>();
        map.put("data",result);
        return map;
    }

    private List getChildren(long parentId, List<TbItemCat> list) {
        List result =new ArrayList();
        for(TbItemCat itemCat:list){
            if(itemCat.getParentId().equals(parentId)){
                if(itemCat.getIsParent()){
                    /**
                     * 一级类目 或者二级类目
                     */
                    CatNode catNode = new CatNode();
                    if(itemCat.getParentId().equals(0L)){
                        /**
                         * 一级分类
                         */
                        catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
                    }else{
                        /**
                         * 二级分类
                         */
                        catNode.setName(itemCat.getName());
                    }
                    catNode.setUrl("/products/"+itemCat.getId()+".html");
                    catNode.setList(getChildren(itemCat.getId(),list));
                    result.add(catNode);
                }else{
                    // 三级节点
                    result.add("/products/" + itemCat.getId() + ".html|" + itemCat.getName());
                }
            }
        }
        return result;
    }
}
