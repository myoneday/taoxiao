package com.example.taoxiao.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.taoxiao.entity.rentgoods;
import com.example.taoxiao.entity.sellgoods;
import com.example.taoxiao.mapper.GoodsMapper;
import com.example.taoxiao.mapper.goodsImageMapper;

import com.example.taoxiao.FileUtils;

//设置访问的接口的文件

@RestController 
@RequestMapping("/goods")  
public class GoodsController {

		@Autowired
		private GoodsMapper goodsm;
		
		
		@RequestMapping("/newgoods")
		public String addgoods(String goodsname,String sellid, String type,double price, String description, Date uptime){
			JSONObject result = new JSONObject();
			if( goodsm.insert(goodsname,sellid,type,price,description,uptime))
				result.put("status", "true");
			else
				result.put("status", "false");
			return result.toJSONString();
		}
		@RequestMapping("/getgoodsINF")
		public List<sellgoods> select2(int id){//根据商品ID查找商品
			return goodsm.select2(id);
		}
		@RequestMapping("/getgoodsINFBytype")//根据类型返回商品信息
		public List<sellgoods> select3(String type){
			return goodsm.select3(type);
		}
		@RequestMapping("/getgoodsINFsellid")
		public List<sellgoods> select4(String sellid){//根据用户userid查看自己销售物品
			return goodsm.select4(sellid);
		}
		@RequestMapping("/delete")//删除商品信息
			public String delete(int id){
			JSONObject result = new JSONObject();
			if( goodsm.delete(id)&& FileUtils.delete(id))
				result.put("status", "true");
			else
				result.put("status", "false");
			// && FileUtils.delete(id)
			return result.toJSONString();
		}
		@RequestMapping("/update")//更新商品信息
		public String update(String goodsid,String goodsname,String sellid,String type,double price,String description,Date uptime){
			JSONObject result = new JSONObject();
			if( goodsm.update(goodsid,goodsname,sellid,type,price,description,uptime))
				result.put("status", "true");
			else
				result.put("status", "false");
			return result.toJSONString();
		}
		@RequestMapping("/Sreachsell")//搜索买卖的商品
		public List<sellgoods> Searchsell(String Text)
		{
			return goodsm.Searchsell(Text);
		}
		@RequestMapping("/Sreachrent")//搜索租借的商品
		public List<rentgoods> Searchrent(String Text){
			return goodsm.Searchrent(Text);
		}

}