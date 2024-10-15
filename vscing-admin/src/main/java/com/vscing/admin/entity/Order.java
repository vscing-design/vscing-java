package com.vscing.admin.entity;

import java.math.BigDecimal;
import com.vscing.common.domain.BaseEntity;

/** 
 * @author vscing (vscing@foxmail.com)
 * @date 2024-10-15 23:50:54
*/
public class Order extends BaseEntity {

    private Long id;

    private Long userId;

    private BigDecimal amount;

    private byte status;

    private String orderSn;
}
