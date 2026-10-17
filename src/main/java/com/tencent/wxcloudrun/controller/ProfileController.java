package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.OpenIdUtil;
import com.tencent.wxcloudrun.model.UserProfile;
import com.tencent.wxcloudrun.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  private final UserProfileService userProfileService;

  public ProfileController(@Autowired UserProfileService userProfileService) {
    this.userProfileService = userProfileService;
  }

  @GetMapping("/api/profile")
  ApiResponse get(@RequestHeader(value = "X-WX-OPENID", required = false) String openid) {
    return ApiResponse.ok(userProfileService.get(OpenIdUtil.resolve(openid)));
  }

  @PostMapping("/api/profile")
  ApiResponse upsert(@RequestHeader(value = "X-WX-OPENID", required = false) String openid,
                     @RequestBody UserProfile profile) {
    profile.setOpenid(OpenIdUtil.resolve(openid));
    userProfileService.upsert(profile);
    return ApiResponse.ok();
  }
}
