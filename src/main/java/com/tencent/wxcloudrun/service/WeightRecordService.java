package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.WeightRecordMapper;
import com.tencent.wxcloudrun.model.WeightRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightRecordService {

  private final WeightRecordMapper weightRecordMapper;

  public WeightRecordService(@Autowired WeightRecordMapper weightRecordMapper) {
    this.weightRecordMapper = weightRecordMapper;
  }

  public WeightRecord add(String openid, Double weight) {
    WeightRecord record = new WeightRecord();
    long now = System.currentTimeMillis();
    record.setOpenid(openid);
    record.setWeight(weight);
    record.setRecordTime(now);
    record.setCreatedAt(now);
    weightRecordMapper.insert(record);
    return record;
  }

  public List<WeightRecord> list(String openid, int limit) {
    return weightRecordMapper.listByOpenId(openid, limit);
  }
}
