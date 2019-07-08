package com.shsxt.ego.manager.controller;

import com.shsxt.ego.common.model.PictureResult;
import com.shsxt.ego.manager.service.IManagerItemFileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
@Controller
public class ManagerFileController {

    @Resource
    private IManagerItemFileService iManagerItemFileService;

    @RequestMapping("pic/upload")
    @ResponseBody
    public PictureResult uploadFile(HttpServletRequest request) {
        MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
        MultipartFile mf = mhsr.getFile("uploadFile");
        return iManagerItemFileService.uploadFile(mf);
    }
}
