package com.shsxt.ego.search.service;

import com.shsxt.ego.search.entity.GoodsVo;
import org.springframework.data.domain.Page;

public interface ISearchService {
    public Page<GoodsVo> queryGoodsByKey(String key,Integer page);
}
