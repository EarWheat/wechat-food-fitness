package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeightRecord implements Serializable {

  private Long id;

  private String openid;

  private Double weight;

  private Long recordTime;

  private Long createdAt;
}
