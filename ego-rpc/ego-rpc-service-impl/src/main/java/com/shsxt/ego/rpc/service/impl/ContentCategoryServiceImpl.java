package com.shsxt.ego.rpc.service.impl;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.dto.TreeDto;
import com.shsxt.ego.rpc.mapper.db.dao.TbContentCategoryMapper;
import com.shsxt.ego.rpc.pojo.TbContentCategory;
import com.shsxt.ego.rpc.service.IContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements IContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TreeDto> queryContentCategoryListByParentId(Long id) {

        return tbContentCategoryMapper.queryContentCategoryListByParentId(id);
    }

    @Override
    public EgoResult save(TbContentCategory tbContentCategory) {
        // 查询parentId对应节点 如果该节点下不存在节点设置 is_parent=1
        // 查询parent_id下是否存在子节点
        int count = tbContentCategoryMapper.countContentCategoryByParentId(tbContentCategory.getParentId());
        if (count == 0){
            // 更新父节点is_parent
            TbContentCategory parentContentCategory = new TbContentCategory();
            parentContentCategory.setParentId(tbContentCategory.getParentId());
            parentContentCategory.setIsParent(true);
            parentContentCategory.setUpdated(new Date());
            tbContentCategoryMapper.updateByPrimaryKeySelective(parentContentCategory);
        }
        tbContentCategoryMapper.insertSelective(tbContentCategory);
        return new EgoResult();
    }

    @Override
    public EgoResult update(TbContentCategory tbContentCategory) {
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        return new EgoResult();
    }

    @Override
    public EgoResult delete(Long id) {

        /**
         * 1.查询待删除的节点是否为父节点  如果为父节点 不可删除
         * 2.删除当前节点后
         *    父节点不存在子节点  父节点is_parent=0
         */
        EgoResult egoResult = null;
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        if (tbContentCategory.getIsParent()){
            egoResult.setStatus(500);
            egoResult.setMsg("当前节点为父节点，不可直接删除!");
            return egoResult;
        }
        tbContentCategory.setUpdated(new Date());
        tbContentCategory.setStatus(2);
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);// 删除当前节点
        int count = tbContentCategoryMapper.countContentCategoryByParentId(tbContentCategory.getParentId());
        if (count == 0){
            // 父节点is_parent=0
            TbContentCategory parenetContentCategory = new TbContentCategory();
            parenetContentCategory.setId(tbContentCategory.getParentId());
            parenetContentCategory.setIsParent(false);
            parenetContentCategory.setUpdated(new Date());
            tbContentCategoryMapper.updateByPrimaryKeySelective(parenetContentCategory);
        }
        return new EgoResult();
    }
}
