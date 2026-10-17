package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.FoodRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FoodRecordMapper {

  @Insert("INSERT INTO food_records(openid, image, name, calories, protein, carbs, fat, serving, meal_type, record_time, source, created_at) " +
          "VALUES(#{openid}, #{image}, #{name}, #{calories}, #{protein}, #{carbs}, #{fat}, #{serving}, #{mealType}, #{recordTime}, #{source}, #{createdAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(FoodRecord record);

  @Select("SELECT id, openid, image, name, calories, protein, carbs, fat, serving, meal_type, record_time, source, created_at " +
          "FROM food_records WHERE openid = #{openid} ORDER BY record_time DESC LIMIT #{limit}")
  List<FoodRecord> listByOpenId(@Param("openid") String openid, @Param("limit") int limit);

  @Select("SELECT id, openid, image, name, calories, protein, carbs, fat, serving, meal_type, record_time, source, created_at " +
          "FROM food_records WHERE id = #{id}")
  FoodRecord getById(@Param("id") Long id);

  @Update("UPDATE food_records SET name = #{name}, calories = #{calories}, protein = #{protein}, carbs = #{carbs}, fat = #{fat}, " +
          "serving = #{serving}, meal_type = #{mealType} WHERE id = #{id}")
  void update(FoodRecord record);

  @Delete("DELETE FROM food_records WHERE id = #{id}")
  void delete(@Param("id") Long id);
}
