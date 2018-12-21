package com.example.taoxiao.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.example.taoxiao.FileUtils;
import com.example.taoxiao.entity.goodsimage;
import com.example.taoxiao.mapper.goodsImageMapper;

@RequestMapping("/image")
@RestController
public class goodsImageController {
	private final ResourceLoader resourceLoader;

    @Autowired
    public goodsImageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${web.upload-path}")
    private String path;
    
    private FileUtils fileutil;
    @Autowired
    private goodsImageMapper gimage;
    /**
     *
     * @param file 要上传的文件
     * @return
     */
    //图片上传
    @RequestMapping("/fileUpload")
    public String upload(@RequestParam("fileName") MultipartFile file,int goodid){
    	//fileutil = new FileUtils();
    	JSONObject result = new JSONObject();
    	if(gimage.select(goodid)!=null) {
    		gimage.delete(goodid);
    	}
        if (FileUtils.upload(file, path, file.getOriginalFilename(),goodid)){
            // 上传成功，给出页面提示
        	result.put("status", "successs");
        }else {
        	result.put("status", "false");
        }
        return result.toJSONString();
    }
    //获取图片路径
    @RequestMapping("/getphoto")
    public String showphoto(int goodid) {
        goodsimage img=gimage.select(goodid);
        JSONObject result = new JSONObject();
        result.put("url", img.getUrl());
        return result.toJSONString();
    }
}