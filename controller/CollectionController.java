package com.example.taoxiao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class CollectionController {
	@Autowired
	private CollectionMapper cmapper;
	
	
	@RequestMapping("/getcollection")
	public String getcollection(String UserId){
		JSONObject result = new JSONObject();
	    List<Collection> str=cmapper.getcollection(UserId);
	    result.put("collection",str);//添加返回的字段
		return result.toJSONString();
	}
	@RequestMapping("/setcollection")
	public String setcollection(String UserId, String GoodsId, Date Uptime)
	{
		JSONObject result = new JSONObject();
		if( cmapper.setcollection(UserId,GoodsId,Uptime))
			result.put("status", "true");
		else
			result.put("status", "false");
		return result.toJSONString();
	}
	@RequestMapping("/delcollection")
	public String delcollection(String UserId,String GoodsId)
	{
		JSONObject result = new JSONObject();
		if( cmapper.delcollection(UserId,GoodsId))
			result.put("status", "true");
		else
			result.put("status", "false");
		return result.toJSONString();
	}

}
