package com.shsxt.ego.search.controller;

import com.shsxt.ego.common.util.JsonUtils;
import com.shsxt.ego.search.service.ISearchService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class SearchController {

    @Resource
    private ISearchService searchService;


    @RequestMapping(value = "/item/search", produces = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
    @ResponseBody
    public String queryGoodsByKey(String key, @RequestParam(defaultValue = "1") Integer page, HttpServletRequest request) {
        String json = null;
        try {
            json = JsonUtils.objectToJson(searchService.queryGoodsByKey(new String(key.getBytes("ISO-8859-1"), "UTF-8"), page));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return json;
    }


    @RequestMapping("/{url}")
    public String page(@PathVariable String url, @RequestParam(name = "q") String key,
                       @RequestParam(name = "page", defaultValue = "1") Integer p, HttpServletRequest request) {
        Page page = null;
        try {
            key = new String(key.getBytes("ISO-8859-1"), "UTF-8");
            page = searchService.queryGoodsByKey(key, p);
            request.setAttribute("itemList", page.getContent());
            request.setAttribute("page", p);
            request.setAttribute("maxpage", page.getTotalPages());
            request.setAttribute("query", key);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url;
    }

}
