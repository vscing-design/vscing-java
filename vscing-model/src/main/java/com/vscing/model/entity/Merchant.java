package com.vscing.model.entity;

import com.vscing.model.domain.BaseEntity;
import com.vscing.model.vo.MerchantDetailVo;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Merchant
 *
 * @author vscing
 * @date 2025/4/18 23:13
 */
@Getter
@Setter
@AutoMappers({
    @AutoMapper(target = MerchantDetailVo.class),
})
public class Merchant extends BaseEntity {

  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "商户名称")
  private String merchantName;

  @Schema(description = "密码")
  private String password;

  @Schema(description = "状态：1 合作中 2 已暂停")
  private Integer status;

  @Schema(description = "账户余额金额")
  private BigDecimal balance;

  @Schema(description = "累计充值金额")
  private BigDecimal totalRecharge;

  @Schema(description = "手机号")
  private String phone;

  @Schema(description = "联系人")
  private String contacts;

  @Schema(description = "营业执照编号")
  private String businessLicense;

  @Schema(description = "统一社会信用代码")
  private String uscc;

  @Schema(description = "法人姓名")
  private String legalName;

  @Schema(description = "法人身份证号")
  private String idCard;

  @Schema(description = "法人身份证正面")
  private String idCardFront;

  @Schema(description = "法人身份证反面")
  private String idCardBack;

  @Schema(description = "最后登陆IP")
  private String lastIp;

  @Schema(description = "最后登陆时间")
  private LocalDateTime loginAt;

  @Schema(description = "单点登陆token")
  private String token;

}
