package com.shsxt.ego.portal.service.impl;

import com.shsxt.ego.common.model.BigPicture;
import com.shsxt.ego.portal.service.IPortalContentService;
import com.shsxt.ego.rpc.service.IContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PortalContentServiceImpl implements IPortalContentService {
    @Resource
    private IContentService contentServiceProxy;

    @Override
    public List<BigPicture> queryContentsByCategoryId(Long cid) {
        return contentServiceProxy.queryContentsByCategoryId(cid);
    }
}
