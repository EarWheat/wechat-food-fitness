package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.OpenIdUtil;
import com.tencent.wxcloudrun.model.FoodRecord;
import com.tencent.wxcloudrun.service.FoodRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoodRecordController {

  private final FoodRecordService foodRecordService;

  public FoodRecordController(@Autowired FoodRecordService foodRecordService) {
    this.foodRecordService = foodRecordService;
  }

  @PostMapping("/api/records")
  ApiResponse create(@RequestHeader(value = "X-WX-OPENID", required = false) String openid,
                     @RequestBody FoodRecord record) {
    record.setOpenid(OpenIdUtil.resolve(openid));
    if (record.getRecordTime() == null) {
      record.setRecordTime(System.currentTimeMillis());
    }
    record.setCreatedAt(System.currentTimeMillis());
    return ApiResponse.ok(foodRecordService.create(record));
  }

  @GetMapping("/api/records")
  ApiResponse list(@RequestHeader(value = "X-WX-OPENID", required = false) String openid,
                   @RequestParam(defaultValue = "200") int limit) {
    List<FoodRecord> records = foodRecordService.list(OpenIdUtil.resolve(openid), limit);
    return ApiResponse.ok(records);
  }

  @GetMapping("/api/records/{id}")
  ApiResponse get(@PathVariable Long id) {
    FoodRecord record = foodRecordService.get(id);
    return record == null ? ApiResponse.error("记录不存在") : ApiResponse.ok(record);
  }

  @PutMapping("/api/records/{id}")
  ApiResponse update(@PathVariable Long id, @RequestBody FoodRecord record) {
    record.setId(id);
    foodRecordService.update(record);
    return ApiResponse.ok();
  }

  @DeleteMapping("/api/records/{id}")
  ApiResponse delete(@PathVariable Long id) {
    foodRecordService.delete(id);
    return ApiResponse.ok();
  }
}
