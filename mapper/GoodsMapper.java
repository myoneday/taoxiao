package com.example.taoxiao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select; 
import org.apache.ibatis.annotations.Delete; 
import org.apache.ibatis.annotations.Update;

import com.example.taoxiao.entity.*; 


@Mapper
public interface GoodsMapper {  
	@Select("select * from sellgoods order by uptime")
    List<sellgoods> select1();//查找所有的商品
    
    @Select("select * from sellgoods where sellid = #{sellid}")  
    List<sellgoods> select4(String sellid);//根据用户userid查看自己销售物品
    
    @Select("select * from sellgoods where type = #{type}")  
    List<sellgoods> select3(String type);//根据商品的类型查找商品
    
    @Select("select * from sellgoods where goodid = #{goodid}")  
    List<sellgoods> select2(int goodid);//根据ID查找商品信息
    
    @Delete("delete from sellgoods where goodid = #{goodid}")
    boolean delete(@Param("goodid") int goodid);//根据ID删除商品信息
    
    @Update("update sellgoods set goodname=#{goodname},sellid=#{sellid},type=#{type},price=#{price},description=#{description},uptime=#{uptime} where goodid=#{goodid}")
    boolean update(@Param("goodid") String goodid, @Param("goodname") String goodname,@Param("sellid") String sellid,@Param("type") String type,
    		@Param("price") double price,@Param("description") String description,@Param("uptime") Date uptime);
    
    @Insert("INSERT INTO sellgoods VALUES(null,#{goodname},#{sellid},#{type},#{price},#{description},#{uptime)")//添加用户到数据库
    boolean insert(@Param("goodname") String goodname, @Param("sellid") String sellid,@Param("type") String type,
    		@Param("price") double price,@Param("description") String description, @Param("uptime") Date uptime);

    @Select("select * from sellgoods where goodname like #{Text} OR description like #{Text} OR type like #{Text}")
    List<sellgoods> Searchsell(@Param("Text") String Text);//根据关键字搜索商品

    @Select("select * from rentgoods where goodname like #{Text} OR description like #{Text} OR type like #{Text}")
    List<rentgoods> Searchrent(@Param("Text") String Text);//根据关键字搜索商品
    
    @Select("select * from sellgoods where sellid=#{sellid}")
    List<sellgoods> Mysellgoods(@Param("sellid") String sellid);//查看我的发布
}  

