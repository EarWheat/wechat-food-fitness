package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.UserProfileMapper;
import com.tencent.wxcloudrun.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

  private final UserProfileMapper userProfileMapper;

  public UserProfileService(@Autowired UserProfileMapper userProfileMapper) {
    this.userProfileMapper = userProfileMapper;
  }

  public UserProfile get(String openid) {
    return userProfileMapper.getByOpenId(openid);
  }

  public void upsert(UserProfile profile) {
    userProfileMapper.upsert(profile);
  }

  public void updateWeight(String openid, Double weight) {
    userProfileMapper.updateWeight(openid, weight);
  }
}
