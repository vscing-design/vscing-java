package com.vscing.model.mapper;

import com.vscing.model.dto.SeatListDto;
import com.vscing.model.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OrderDetailMapper
 *
 * @author vscing
 * @date 2025/1/2 16:25
 */
@Mapper
public interface OrderDetailMapper {

  int batchInsert(@Param("list") List<OrderDetail> list);

  List<SeatListDto> selectByOrderId(@Param("orderId") Long orderId);

  List<SeatListDto> selectByShowId(Long showId);

  int softDeleteByOrderId(@Param("orderId") long orderId, @Param("deleterId") long deleterId);

  int batchUpdateOrderDetail(List<OrderDetail> updateOrderDetailList);

}
