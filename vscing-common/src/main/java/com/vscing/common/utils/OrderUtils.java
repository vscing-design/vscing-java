package com.vscing.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * OrderUtils
 *
 * @author vscing
 * @date 2025/3/13 20:45
 */
public class OrderUtils {

  /**
   * 生成订单号
   *
   * @param prefix 前缀（如 "HY"）
   * @param randomLength 随机数长度
   * @return 生成的订单号
   */
  public static String generateOrderSn(String prefix, Integer randomLength) {
    // 获取当前时间戳，格式为yyyyMMddHHmmssSSS（17位）
    String timestamp = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS");
    if (randomLength == null) {
      randomLength = 5;
    }
    // 生成随机数字
    String randomNum = RandomUtil.randomNumbers(randomLength);
    // 组合生成订单号
    return prefix + timestamp + randomNum;
  }

}
