package com.shsxt.ego.portal.service;

import com.shsxt.ego.common.model.BigPicture;

import java.util.List;

public interface IPortalContentService {

    public List<BigPicture> queryContentsByCategoryId(Long cid);
}
