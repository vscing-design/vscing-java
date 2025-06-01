package com.vscing.admin.service;

/**
 * VipSyncService
 *
 * @author vscing
 * @date 2025/6/1 16:44
 */
public interface VipSyncService {

  // 获取分组数据
  void queryGroup();

  // 获取商品列表
  void queryGoods(int page);

}
