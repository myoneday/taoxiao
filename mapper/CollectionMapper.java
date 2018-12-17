package com.example.taoxiao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CollectionMapper {
    @Select("select * from collection where UserId = #{UserId}")
    List<Collection> getcollection(String UserId);//根据Id找收藏夹
    @Insert("INSERT INTO collection VALUES(#{UserId}, #{GoodsId},#{Uptime})")
    boolean setcollection(String UserId, String GoodsId, Date Uptime);
    @Delete("delete from collection where UserId = #{UserId} AND GoodsId = #{GoodsId}")
    boolean delcollection(String UserId,String GoodsId);
}
