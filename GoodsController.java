package com.example.taoxiao;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//设置访问的接口的文件

@RestController 
@RequestMapping("/goods")  
public class GoodsController {

		@Autowired
		private GoodsMapper goodsm;
		
		@RequestMapping("/newgoods")
		public String addgoods(String goodsname,String sellid, String kinds,double price, String description, Date uptime){
			JSONObject result = new JSONObject();
			if( goodsm.insert(goodsname,sellid,kinds,price,description,uptime))
				result.put("status", "true");
			else
				result.put("status", "false");
			return result.toJSONString();
		}
		@RequestMapping("/getgoodsINF")
		public String select2(String id){//根据商品ID查找商品
			JSONObject result = new JSONObject();
			List<sellgoods> str=goodsm.select2(id);
			result.put("sreach",str);
			return result.toJSONString();

		}
		@RequestMapping("/delete")//删除商品信息
			public String delete(String id){
			JSONObject result = new JSONObject();
			if( goodsm.delete(id))
				result.put("status", "true");
			else
				result.put("status", "false");
			return result.toJSONString();
		}
		@RequestMapping("/update")//更新商品信息
				public String update(String goodsid,String goodsname,String sellid,String kinds,double price,String description,Date uptime){
			JSONObject result = new JSONObject();
			if( goodsm.update(goodsid,goodsname,sellid,kinds,price,description,uptime))
				result.put("status", "true");
			else
				result.put("status", "false");
			return result.toJSONString();
		}
		@RequestMapping("/Sreachsell")//搜索买卖的商品
				public String Searchsell(String Text)
		{
			JSONObject result = new JSONObject();
			List<sellgoods> str=goodsm.Searchsell(Text);
			result.put("sreach",str);
			return result.toJSONString();
		}
		public String Searchsell(String type,String Text)
		{
			JSONObject result = new JSONObject();
			List<sellgoods> str=goodsm.Searchsell(type,Text);
			result.put("sreach",str);
			return result.toJSONString();
		}
	@RequestMapping("/Sreachrent")//搜索租借的商品
	public String Searchrent(String Text)
	{
		JSONObject result = new JSONObject();
		List<rentgoods> str=goodsm.Searchrent(Text);
		result.put("sreach",str);
		return result.toJSONString();
	}
	@RequestMapping("/Mysellgoods")//查看自己的发布
	public String Mysellgoods(String sellid)
	{
		JSONObject result = new JSONObject();
		List<sellgoods> str=goodsm.Mysellgoods(sellid);
		result.put("sellgoods",str);
		return result.toJSONString();
	}

}
