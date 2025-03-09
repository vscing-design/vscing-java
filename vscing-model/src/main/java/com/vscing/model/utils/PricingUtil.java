package com.vscing.model.utils;

import com.vscing.model.entity.PricingRule;
import com.vscing.model.vo.UserConfigPricingRuleVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * PricingUtil
 *
 * @author vscing
 * @date 2025/1/1 23:11
 */
public class PricingUtil {

  /**
   * checkDifference 判断差值是否满足条件
  */
  public static boolean checkDifference(BigDecimal diff, BigDecimal boundary, Integer operator) {
    return !switch (operator) {
      case 1 ->
        // >=
          diff.compareTo(boundary) >= 0;
      case 2 ->
        // >
          diff.compareTo(boundary) > 0;
      case 3 ->
        // <=
          diff.compareTo(boundary) <= 0;
      case 4 ->
        // <
          diff.compareTo(boundary) < 0;
      default -> throw new IllegalArgumentException("未知的运算符类型");
    };
  }

  /**
   * @author vscing (vscing@foxmail.com)
   * @date 2025-03-09 15:34:28
  */
  public static BigDecimal calculateEarnAmount(BigDecimal officialPrice, BigDecimal salePrice, List<UserConfigPricingRuleVo> rules) {
    BigDecimal difference = officialPrice.subtract(salePrice);

    // 查找匹配的规则
    Optional<UserConfigPricingRuleVo> matchingRule = rules.stream()
        .filter(rule -> {
          BigDecimal minDiff = rule.getMinDiff();
          BigDecimal maxDiff = rule.getMaxDiff();

          if (rule.getMinOperator() > 0 && minDiff != null && checkDifference(difference, minDiff, rule.getMinOperator())) {
            return false;
          }
          return rule.getMaxOperator() <= 0 || maxDiff == null || !checkDifference(difference, maxDiff, rule.getMaxOperator());
        })
        .findFirst();

    // 如果没有匹配的规则，默认返回售价
    return matchingRule.map(UserConfigPricingRuleVo::getEarnAmount).orElse(BigDecimal.ZERO);
  }

  /**
   * 根据官方价、售价和规则列表计算实际售价。
   *
   * @param officialPrice 官方价
   * @param salePrice     售价
   * @param rules         定价规则列表
   * @return 实际售价
   */
  public static BigDecimal calculateActualPrice(BigDecimal officialPrice, BigDecimal salePrice, List<PricingRule> rules) {
    BigDecimal difference = officialPrice.subtract(salePrice);

    // 查找匹配的规则
    Optional<PricingRule> matchingRule = rules.stream()
        .filter(rule -> {
          BigDecimal minDiff = rule.getMinDiff();
          BigDecimal maxDiff = rule.getMaxDiff();

          if (rule.getMinOperator() > 0 && minDiff != null && checkDifference(difference, minDiff, rule.getMinOperator())) {
            return false;
          }
          return rule.getMaxOperator() <= 0 || maxDiff == null || !checkDifference(difference, maxDiff, rule.getMaxOperator());
        })
        .findFirst();

    // 如果没有匹配的规则，默认返回售价
    return matchingRule.map(pricingRule -> salePrice.add(pricingRule.getMarkupAmount())).orElse(salePrice);
  }

  /**
   * 将价格列表转换为指定格式的字符串表示。
   *
   * @param salesPrice 价格列表
   * @return 格式化后的字符串
   */
  public static String formatPrices(List<BigDecimal> salesPrice) {
    if (salesPrice == null || salesPrice.isEmpty()) {
      return "";
    }

    // 使用 TreeMap 确保输出是按价格排序的
    Map<BigDecimal, Integer> priceCounts = new TreeMap<>();

    // 统计每个价格出现的次数
    for (BigDecimal price : salesPrice) {
      priceCounts.put(price, priceCounts.getOrDefault(price, 0) + 1);
    }

    // 构建结果字符串
    StringBuilder result = new StringBuilder();
    for (Map.Entry<BigDecimal, Integer> entry : priceCounts.entrySet()) {
      if (!result.isEmpty()) {
        result.append(", ");
      }
      result.append(entry.getKey()).append("*").append(entry.getValue());
    }

    return result.toString();
  }

}
