package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.WeightRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WeightRecordMapper {

  @Insert("INSERT INTO weight_records(openid, weight, record_time, created_at) " +
          "VALUES(#{openid}, #{weight}, #{recordTime}, #{createdAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(WeightRecord record);

  @Select("SELECT id, openid, weight, record_time, created_at FROM weight_records WHERE openid = #{openid} ORDER BY record_time DESC LIMIT #{limit}")
  List<WeightRecord> listByOpenId(@Param("openid") String openid, @Param("limit") int limit);
}
