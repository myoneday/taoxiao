package com.example.taoxiao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.taoxiao.mapper.goodsImageMapper;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

@Component
public class FileUtils {
	@Autowired
	private goodsImageMapper gimagemapper;
	private static goodsImageMapper gimage;
	@PostConstruct
	public void init() {
		gimage=gimagemapper;
	}
    public static boolean upload(MultipartFile file, String path, String fileName, int goodid){
    	String forward = "http://localhost:8080/";
    	String middle = FileNameUtils.getFileName(fileName);
    	String url = forward + middle;
        String realPath = path + "/" + middle;
        File dest = new File(realPath);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        if(gimage.select(goodid)!=null) {
        	if(gimage.delete(goodid)==false) {
        		return false;
        	}
        }
        gimage.insert(goodid,url);

        try {
            //保存文件
            file.transferTo(dest);
            //image.update(userid,url);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public static boolean delete(int goodid) {
    	String pf=gimage.select(goodid).getUrl().substring(22);
    	pf= "C:/Users/Administrator/Desktop/image/"+pf;
    	System.out.println(pf);
    	File file=new File(pf);
        if(file.exists()&&file.isFile()) {
            return file.delete();
        }
    	return false;
    }
}