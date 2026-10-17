package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserProfile implements Serializable {

  private Long id;

  private String openid;

  private String nickname;

  private String gender;

  private Double height;

  private Double weight;

  private Integer age;

  private String goal;
}
