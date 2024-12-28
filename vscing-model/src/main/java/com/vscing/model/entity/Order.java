package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:50:54
*/
@Getter
@Setter
public class Order extends BaseEntity {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "订单状态 0->待付款 1->待发货 2->已发货 3->已完成 4->已取消")
    private Integer status;

//    private String orderSn;
}
