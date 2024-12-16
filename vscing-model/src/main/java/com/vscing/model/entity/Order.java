package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;

import java.math.BigDecimal;

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
