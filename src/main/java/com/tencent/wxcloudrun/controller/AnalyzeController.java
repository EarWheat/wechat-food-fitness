package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.AdviceRequest;
import com.tencent.wxcloudrun.dto.AnalyzeRequest;
import com.tencent.wxcloudrun.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AnalyzeController {

  private final AiService aiService;

  public AnalyzeController(@Autowired AiService aiService) {
    this.aiService = aiService;
  }

  @PostMapping("/api/analyze")
  ApiResponse analyze(@RequestBody AnalyzeRequest request) {
    try {
      Map<String, Object> data = aiService.analyzeFood(request.getImageBase64());
      return ApiResponse.ok(data);
    } catch (Exception e) {
      return ApiResponse.error(e.getMessage());
    }
  }

  @PostMapping("/api/advice")
  ApiResponse advice(@RequestBody AdviceRequest request) {
    try {
      String advice = aiService.generateAdvice(request.getSummary(), request.getProfile(), request.getTargets());
      return ApiResponse.ok(advice);
    } catch (Exception e) {
      return ApiResponse.error(e.getMessage());
    }
  }
}
