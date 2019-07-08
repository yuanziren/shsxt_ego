package com.shsxt.ego.manager.service.impl;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.manager.service.IManagerContentCategoryService;
import com.shsxt.ego.rpc.dto.TreeDto;
import com.shsxt.ego.rpc.pojo.TbContentCategory;
import com.shsxt.ego.rpc.service.IContentCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ManagerContentCategoryServiceImpl implements IManagerContentCategoryService {

    @Resource
    private IContentCategoryService iContentCategoryServiceProxy;


    @Override
    public List<TreeDto> queryContentCategoryListByParentId(Long id) {
        return iContentCategoryServiceProxy.queryContentCategoryListByParentId(id);
    }

    @Override
    public EgoResult save(TbContentCategory tbContentCategory) {

        Date time = new Date();
        tbContentCategory.setCreated(time);
        tbContentCategory.setUpdated(time);
        tbContentCategory.setIsParent(false);// 叶子节点
        tbContentCategory.setSortOrder(1);
        return iContentCategoryServiceProxy.save(tbContentCategory);
    }

    @Override
    public EgoResult update(TbContentCategory tbContentCategory) {
        return iContentCategoryServiceProxy.update(tbContentCategory);
    }

    @Override
    public EgoResult delete(Long id) {
        return iContentCategoryServiceProxy.delete(id);
    }
}
