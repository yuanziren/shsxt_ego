package com.shsxt.ego.rpc.service.impl;

import com.shsxt.ego.rpc.dto.TreeDto;
import com.shsxt.ego.rpc.mapper.db.dao.TbItemCatMapper;
import com.shsxt.ego.rpc.pojo.TbItemCat;
import com.shsxt.ego.rpc.service.IItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemCatServiceImpl implements IItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String,Object> valueOperations;

    @Override
    public List<TreeDto> queryItemCatsByParentId(Long id) {
        return itemCatMapper.queryItemCatsByParentId(id);
    }

    @Override
    public List<TbItemCat> queryAllItemCats() {
        /**
         * 1.从redis 缓存获取缓存数据
         *    缓存存在  获取缓存 返回
         * 2.缓存不存在
         *     从数据库查询数据
         *        存在 获取数据 将查询数据放入缓存
         */
        String key ="itemCat::allItemCats";
        List<TbItemCat> itemCats=null;
        if(redisTemplate.hasKey(key)){
            System.out.println("从缓存获取list 集合");
            itemCats= (List<TbItemCat>) valueOperations.get(key);
        }else{
            itemCats= itemCatMapper.queryAllItemCats();
            if(null  !=itemCats && itemCats.size()>0){
                valueOperations.set(key,itemCats);
            }
        }
        return itemCats;
    }
}
