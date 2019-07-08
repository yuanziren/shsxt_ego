package com.shsxt.ego.rpc.service;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.rpc.dto.TreeDto;
import com.shsxt.ego.rpc.pojo.TbContentCategory;

import java.util.List;

public interface IContentCategoryService {

    public List<TreeDto> queryContentCategoryListByParentId(Long id);

    EgoResult save(TbContentCategory tbContentCategory);

    EgoResult update(TbContentCategory tbContentCategory);

    EgoResult delete(Long id);
}
