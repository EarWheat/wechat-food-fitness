package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FoodRecord implements Serializable {

  private Long id;

  private String openid;

  private String image;

  private String name;

  private Double calories;

  private Double protein;

  private Double carbs;

  private Double fat;

  private String serving;

  private String mealType;

  private Long recordTime;

  private String source;

  private Long createdAt;
}
