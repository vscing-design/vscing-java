package com.vscing.api.service;

import com.vscing.model.request.ShowSeatRequest;

/**
 * TestService
 *
 * @author vscing
 * @date 2025/1/20 21:18
 */
public interface TestService {

  /**
   * 三方座位列表
   */
  String getSeat(ShowSeatRequest showSeat);

}
