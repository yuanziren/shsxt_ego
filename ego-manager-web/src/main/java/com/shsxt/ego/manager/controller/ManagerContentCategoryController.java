package com.shsxt.ego.manager.controller;

import com.shsxt.ego.common.model.EgoResult;
import com.shsxt.ego.manager.service.IManagerContentCategoryService;
import com.shsxt.ego.rpc.dto.TreeDto;
import com.shsxt.ego.rpc.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ManagerContentCategoryController {

    @Autowired
    private IManagerContentCategoryService iManagerContentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<TreeDto> queryContentCategoryListByParentId(@RequestParam(defaultValue = "0") Long id) {
        return iManagerContentCategoryService.queryContentCategoryListByParentId(id);
    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    public EgoResult save(TbContentCategory tbContentCategory) {
        return iManagerContentCategoryService.save(tbContentCategory);
    }

    @RequestMapping("/content/category/update")
    @ResponseBody
    public EgoResult update(TbContentCategory tbContentCategory) {
        return iManagerContentCategoryService.update(tbContentCategory);
    }

    @RequestMapping("/content/category/delete")
    @ResponseBody
    public EgoResult delete(Long id) {
        return iManagerContentCategoryService.delete(id);
    }

}
