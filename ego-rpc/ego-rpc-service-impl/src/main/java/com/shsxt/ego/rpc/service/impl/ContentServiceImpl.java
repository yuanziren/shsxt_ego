package com.shsxt.ego.rpc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.ego.common.model.BigPicture;
import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.common.model.PageResult;
import com.shsxt.ego.rpc.mapper.db.dao.TbContentMapper;
import com.shsxt.ego.rpc.pojo.TbContent;
import com.shsxt.ego.rpc.query.ContentQuery;
import com.shsxt.ego.rpc.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ContentServiceImpl implements IContentService {
    @Autowired
    private TbContentMapper contentMapper;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String,Object> valueOperations;

    @Override
    public PageResult<TbContent> queryContentsByParams(ContentQuery contentQuery) {
        PageHelper.startPage(contentQuery.getPage(), contentQuery.getRows());
        List<TbContent> itemList = null;
        String key = "content::queryContentsByParams::cid::"+contentQuery.getCategoryId()+
                "::page::"+contentQuery.getPage()+"::rows::"+contentQuery.getRows();
        if (redisTemplate.hasKey(key)){
            itemList = (List<TbContent>) valueOperations.get(key);
            if (null != itemList && itemList.size() >0){
                valueOperations.set(key,itemList);
            }
        }else {
            itemList = contentMapper.queryContentsByParams(contentQuery);
        }
        PageInfo<TbContent> pageInfo =new PageInfo<>(itemList);
        PageResult<TbContent> pageResult =new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(pageInfo.getList());
        return pageResult;
    }

    @Override
    public List<BigPicture> queryContentsByCategoryId(Long cid) {
        List<BigPicture> pictures = null;
        List<TbContent> contents = null;
        String key = "content::queryContentsByCategoryId::cid::"+cid;
        if (redisTemplate.hasKey(key)){
            contents = (List<TbContent>) valueOperations.get(key);
        }else {
            contents = contentMapper.queryContentsByCategoryId(cid);
            if (null != contents && contents.size() >0){
                valueOperations.set(key,contents);
            }
        }

        if (null != contents && contents.size() >0){
            pictures = new ArrayList<>();
            List<BigPicture> finalPictures = pictures;
            contents.forEach(c->{
                BigPicture bigPicture = new BigPicture();
                bigPicture.setAlt("egos商城");
                bigPicture.setHref(c.getUrl());
                bigPicture.setSrc(c.getPic());
                bigPicture.setSrcb(c.getPic2());
                finalPictures.add(bigPicture);
            });
        }
        return pictures;
    }

    @Override
    public EgoResult delete(Long[] ids) {
        contentMapper.deleteBatch(ids);
        /**
         * 清除缓存key
         *     模式匹配广告内容key
         */
        Set<String> keys = redisTemplate.keys("content::*");
        redisTemplate.delete(keys);
        return new EgoResult();
    }

    public EgoResult save(TbContent content) {
        contentMapper.insertSelective(content);
        /**
         * 清除缓存key
         *     模式匹配广告内容key
         */
        Set<String> keys = redisTemplate.keys("content::*");
        redisTemplate.delete(keys);
        return new EgoResult();
    }

    public EgoResult update(TbContent content) {
        contentMapper.updateByPrimaryKeySelective(content);

        /**
         * 缓存同步问题
         *  清除多个缓存key
         */
       /* String key = "content::queryContentsByCategoryId::cid::" + content.getCategoryId();
        //清除缓存key
        redisTemplate.delete(key);*/

       //匹配指定key 缓存同步
        redisTemplate.delete(redisTemplate.keys("content::*"));
        return new EgoResult();
    }
}
