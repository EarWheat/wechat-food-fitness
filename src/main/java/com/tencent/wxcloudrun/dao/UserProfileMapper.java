package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.UserProfile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserProfileMapper {

  @Select("SELECT id, openid, nickname, gender, height, weight, age, goal FROM user_profile WHERE openid = #{openid} LIMIT 1")
  UserProfile getByOpenId(@Param("openid") String openid);

  @Insert("INSERT INTO user_profile(openid, nickname, gender, height, weight, age, goal) " +
          "VALUES(#{openid}, #{nickname}, #{gender}, #{height}, #{weight}, #{age}, #{goal}) " +
          "ON DUPLICATE KEY UPDATE nickname = #{nickname}, gender = #{gender}, height = #{height}, weight = #{weight}, age = #{age}, goal = #{goal}")
  void upsert(UserProfile profile);

  @Update("UPDATE user_profile SET weight = #{weight} WHERE openid = #{openid}")
  void updateWeight(@Param("openid") String openid, @Param("weight") Double weight);
}
