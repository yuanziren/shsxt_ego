package com.shsxt.ego.manager.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.shsxt.ego.common.model.PictureResult;
import com.shsxt.ego.manager.service.IManagerItemFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ManagerItemFileServiceImpl implements IManagerItemFileService {

    private String ak = "3K77OJ214syThkwIbX8skAMHcEYmZJuqGcTSEEW1";
    private String sk = "DpQoXLAfJWJJblp-kdBm4t573Tzf_cEmq6xLoFW3";
    private String bucket = "20190701";
    private String basePath = "http://pty9die0h.bkt.clouddn.com/";

    @Override
    public PictureResult uploadFile(MultipartFile mf) {
        PictureResult result = new PictureResult();
        try {
            if (null != mf && mf.getSize() > 0) {
                // copy文件到指定目录
                // mf.transferTo();
                //构造一个带指定Zone对象的配置类
                Configuration cfg = new Configuration(Zone.zone0());
                //...其他参数参考类注释
                UploadManager uploadManager = new UploadManager(cfg);
                //默认不指定key的情况下，以文件内容的hash值作为文件名
                String fileName = mf.getOriginalFilename();
                String ext = fileName.substring(fileName.lastIndexOf("."));
                String key = System.currentTimeMillis() + ext;
                Auth auth = Auth.create(ak, sk);
                String upToken = auth.uploadToken(bucket);
                Response response = uploadManager.put(mf.getInputStream(), key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                result.setError(0);
                result.setMessage("ok");
                result.setUrl(basePath + key);
            } else {
                result.setError(1);
                result.setMessage("error");
                result.setUrl("");
            }
        } catch (IOException e) {
            result.setError(1);
            result.setMessage("error");
            result.setUrl("");
        }
        return result;
    }
}
