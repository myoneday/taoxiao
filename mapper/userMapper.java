package com.example.taoxiao.mapper;

import java.sql.Blob;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.taoxiao.entity.user; 

@Mapper  
public interface userMapper {  
	@Select("select * from user")  
    List<user> select1();
    
    @Select("select * from user where userid = #{userid}")  
    user select2(String userid);
    
    @Select("select * from user where userid = #{userid} and userpsw = #{userpsw}")  
    user select3(@Param("userid") String userid, @Param("userpsw") String userpsw);
    
    @Insert("INSERT INTO user VALUES(#{userid}, #{userpsw}, #{nickname}, null,null,null,#{tel},null)")
    boolean insert(@Param("userid") String userid, @Param("userpsw") String userpsw,@Param("nickname") String nickname, @Param("tel") String tel);
    
    @Update("UPDATE user set nickname=#{nickname}, tel=#{tel}, ali=#{ali} where userid=#{userid}")
    boolean update(@Param("userid") String userid, @Param("nickname") String nickname, @Param("tel") String tel, @Param("ali") String ali);
    
    @Update("UPDATE user set nickname=#{nickname}, tel=#{tel}, ali=#{ali} where userid=#{userid}")
    boolean update2(@Param("userid") String userid, @Param("userpsw") String userpsw);
    
    @Update("UPDATE user set headpicture=#{headpicture}")
    boolean uppicture(@Param("headpicture")Blob headpicture);
}  
