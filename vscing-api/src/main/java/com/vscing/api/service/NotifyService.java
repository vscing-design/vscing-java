package com.vscing.api.service;

import java.util.Map;

/**
 * NotifyService
 *
 * @author vscing
 * @date 2025/1/21 23:01
 */
public interface NotifyService {

  /**
   * 查询阿里订单是否成功
  */
  boolean queryAlipayOrder(Map<String, String> params);

}
