package com.vscing.model.mapper;

import com.vscing.model.entity.OrderScore;
import org.apache.ibatis.annotations.Mapper;

/**
 * OrderScoreMapper
 *
 * @author vscing
 * @date 2025/1/23 22:06
 */
@Mapper
public interface OrderScoreMapper {

  /**
   * 根据订单Id查询评分
  */
  OrderScore selectByOrderId(long userId, long orderId);

  /**
   * 新增
  */
  int insert(OrderScore record);

  /**
   * 修改
  */
  int updateByOrderIdAndUserId(OrderScore record);

}
