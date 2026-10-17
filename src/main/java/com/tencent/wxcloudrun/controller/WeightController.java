package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.OpenIdUtil;
import com.tencent.wxcloudrun.dto.WeightRequest;
import com.tencent.wxcloudrun.model.WeightRecord;
import com.tencent.wxcloudrun.service.UserProfileService;
import com.tencent.wxcloudrun.service.WeightRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeightController {

  private final WeightRecordService weightRecordService;
  private final UserProfileService userProfileService;

  public WeightController(@Autowired WeightRecordService weightRecordService,
                          @Autowired UserProfileService userProfileService) {
    this.weightRecordService = weightRecordService;
    this.userProfileService = userProfileService;
  }

  @PostMapping("/api/weights")
  ApiResponse add(@RequestHeader(value = "X-WX-OPENID", required = false) String openid,
                  @RequestBody WeightRequest request) {
    String oid = OpenIdUtil.resolve(openid);
    WeightRecord record = weightRecordService.add(oid, request.getWeight());
    userProfileService.updateWeight(oid, request.getWeight());
    return ApiResponse.ok(record);
  }

  @GetMapping("/api/weights")
  ApiResponse list(@RequestHeader(value = "X-WX-OPENID", required = false) String openid,
                   @RequestParam(defaultValue = "200") int limit) {
    List<WeightRecord> records = weightRecordService.list(OpenIdUtil.resolve(openid), limit);
    return ApiResponse.ok(records);
  }
}
