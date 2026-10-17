package com.tencent.wxcloudrun.config;

/**
 * 从请求头解析 openid。通过 wx.cloud.callContainer 调用时，微信会自动注入 X-WX-OPENID 头；
 * 本地调试无该头时回退为 "dev"。
 */
public final class OpenIdUtil {

  private OpenIdUtil() {
  }

  public static String resolve(String header) {
    return (header == null || header.trim().isEmpty()) ? "dev" : header;
  }
}
