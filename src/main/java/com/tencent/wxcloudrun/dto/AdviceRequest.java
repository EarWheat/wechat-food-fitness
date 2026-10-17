package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AdviceRequest {

  private String summary;

  private Map<String, Object> profile;

  private Map<String, Object> targets;
}
