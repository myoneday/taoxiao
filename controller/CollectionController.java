package com.example.taoxiao.controller;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.taoxiao.entity.Collection;
import com.example.taoxiao.entity.sellgoods;
import com.example.taoxiao.mapper.CollectionMapper;
import com.example.taoxiao.mapper.GoodsMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/collection")
public class CollectionController {
	@Autowired
	private CollectionMapper cmapper;
	@Autowired
	private GoodsMapper goodms;
	
	@RequestMapping("/getcollection")
	public String getcollection(String UserId){
	    List<Collection> str=cmapper.getcollection(UserId);
		JSONArray json = new JSONArray();
		for(Collection t : str){
            List<sellgoods> str1=goodms.select2(t.getGoodsId());
            JSONObject jo = new JSONObject();
			jo.put("goodid", str1.get(0).getGoodid());
			jo.put("goodname", str1.get(0).getGoodname());
			jo.put("price", str1.get(0).getPrice());
			jo.put("description",str1.get(0).getDescription());
			jo.put("userid",str1.get(0).getSellid());
		    json.add(jo);
		}
		return json.toJSONString();
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