package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.FoodRecordMapper;
import com.tencent.wxcloudrun.model.FoodRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodRecordService {

  private final FoodRecordMapper foodRecordMapper;

  public FoodRecordService(@Autowired FoodRecordMapper foodRecordMapper) {
    this.foodRecordMapper = foodRecordMapper;
  }

  public FoodRecord create(FoodRecord record) {
    foodRecordMapper.insert(record);
    return record;
  }

  public List<FoodRecord> list(String openid, int limit) {
    return foodRecordMapper.listByOpenId(openid, limit);
  }

  public FoodRecord get(Long id) {
    return foodRecordMapper.getById(id);
  }

  public void update(FoodRecord record) {
    foodRecordMapper.update(record);
  }

  public void delete(Long id) {
    foodRecordMapper.delete(id);
  }
}
