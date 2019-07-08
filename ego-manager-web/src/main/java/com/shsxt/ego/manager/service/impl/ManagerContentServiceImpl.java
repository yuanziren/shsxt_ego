package com.shsxt.ego.manager.service.impl;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.common.model.PageResult;
import com.shsxt.ego.manager.service.IManagerContentService;
import com.shsxt.ego.rpc.pojo.TbContent;
import com.shsxt.ego.rpc.query.ContentQuery;
import com.shsxt.ego.rpc.service.IContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ManagerContentServiceImpl implements IManagerContentService {

    @Resource
    private IContentService iContentServiceProxy;

    @Override
    public PageResult<TbContent> queryContentsByParams(ContentQuery contentQuery) {
        return iContentServiceProxy.queryContentsByParams(contentQuery);
    }

    @Override
    public EgoResult delete(Long[] ids) {

        return iContentServiceProxy.delete(ids);
    }

    @Override
    public EgoResult save(TbContent content) {

        content.setCreated(new Date());
        content.setUpdated(new Date());
        return iContentServiceProxy.save(content);
    }

    @Override
    public EgoResult update(TbContent content) {
        content.setUpdated(new Date());
        return iContentServiceProxy.update(content);
    }
}
