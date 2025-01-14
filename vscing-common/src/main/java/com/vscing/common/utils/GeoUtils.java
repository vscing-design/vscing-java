package com.vscing.common.utils;

/**
 * GeoUtils
 *
 * @author vscing
 * @date 2025/1/14 14:15
 */
public class GeoUtils {

  private static final double EARTH_RADIUS = 6371; // 地球半径，单位为千米

  /**
   * 使用 Haversine 公式计算两点之间的距离
   */
  public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    double phi1 = Math.toRadians(lat1);
    double phi2 = Math.toRadians(lat2);
    double deltaPhi = Math.toRadians(lat2 - lat1);
    double deltaLambda = Math.toRadians(lon2 - lon1);

    double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
        Math.cos(phi1) * Math.cos(phi2) *
            Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS * c; // 距离，单位为千米
  }

}
