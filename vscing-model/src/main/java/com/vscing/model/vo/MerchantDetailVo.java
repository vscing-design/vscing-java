package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * MerchantDetailVo
 *
 * @author vscing
 * @date 2025/4/18 23:01
 */
@Data
public class MerchantDetailVo {

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

  @Schema(description = "营业执照")
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

  @Schema(description = "乐观锁版本号")
  private Integer version;

}
