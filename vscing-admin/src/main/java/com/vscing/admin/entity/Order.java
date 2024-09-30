package com.vscing.admin.entity;

import java.math.BigDecimal;
import com.vscing.common.domain.BaseEntity;

public class Order extends BaseEntity {

    private Long id;

    // 用户id
    private Long user_id;

    private BigDecimal amount;

    private byte status;

    private String order_sn;
}
