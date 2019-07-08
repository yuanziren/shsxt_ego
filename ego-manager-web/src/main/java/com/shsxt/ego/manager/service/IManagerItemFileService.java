package com.shsxt.ego.manager.service;

import com.shsxt.ego.common.model.PictureResult;
import org.springframework.web.multipart.MultipartFile;

public interface IManagerItemFileService {

    public PictureResult uploadFile(MultipartFile mf);
}
