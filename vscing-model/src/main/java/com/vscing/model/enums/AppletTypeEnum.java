package com.vscing.model.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * AppletTypeEnum 小程序类型
 *
 * @author vscing
 * @date 2025/2/9 00:58
 */
@Getter
public enum AppletTypeEnum {

  WECHAT(1, "wechat"),
  ALIPAY(2, "alipay"),
  BAIDU(3, "baidu");

  /**
   * 标识
   */
  private int code;

  /**
   * 平台
   */
  private String applet;

  /**
   * 手动定义全参构造函数
   */
  AppletTypeEnum(int code, String applet) {
    this.code = code;
    this.applet = applet;
  }

  /**
   * 标识查平台
   *
   * @param code 标识
   */
  public static String findByCode(int code) {
    // 直接进行枚举查找，无需对整数进行空检查
    return Arrays.stream(AppletTypeEnum.values())
        .filter(record -> record.getCode() == code)
        .findFirst()
        .map(AppletTypeEnum::getApplet)
        .orElse(StrUtil.EMPTY);
  }

  /**
   * 平台查标识
   *
   * @param applet 标识
   */
  public static int findByApplet(String applet) {
    return Arrays.stream(AppletTypeEnum.values())
        .filter(record -> record.getApplet().equals(applet))
        .findFirst()
        .map(AppletTypeEnum::getCode)
        .orElse(0);
  }


}
