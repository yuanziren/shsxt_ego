package com.shsxt.ego.search.service.impl;

import com.shsxt.ego.search.entity.GoodsVo;
import com.shsxt.ego.search.service.ISearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements ISearchService {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Page<GoodsVo> queryGoodsByKey(String key, Integer page) {
        HighlightBuilder.Field field = new HighlightBuilder.Field("goodsName")
                .preTags("<font style='color:red;'>")
                .postTags("</font>");
        QueryBuilder queryBuilder= QueryBuilders.multiMatchQuery(key,"goodsName");
        SearchQuery query =new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withHighlightFields(field)
                .withPageable(new PageRequest(page-1,10)).build();
        Page<GoodsVo> result=elasticsearchTemplate.queryForPage(query, GoodsVo.class, new SearchResultMapper() {
            @Override
            public <T> Page<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<GoodsVo> goodsVos =new ArrayList<GoodsVo>();
                SearchHits hits=response.getHits();
                for(SearchHit hit:hits){
                    GoodsVo goodsVo=new GoodsVo();
                    Map<String,Object> map=  hit.getSource();
                    goodsVo.setGoodsId(Long.parseLong(map.get("goodsId").toString()));
                    goodsVo.setGoodsNameH(hit.getHighlightFields().get("goodsName").fragments()[0].toString());// 高亮商品名称
                    goodsVo.setPrice(Long.parseLong(map.get("price").toString()));
                    goodsVo.setImage(map.get("image").toString());
                    goodsVos.add(goodsVo);
                }
                return (Page<T>) new PageImpl<GoodsVo>(goodsVos,pageable,hits.getTotalHits());
            }
        });
        return result;
    }
}
