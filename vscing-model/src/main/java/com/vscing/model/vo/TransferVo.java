package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * TransferVo
 *
 * @author vscing
 * @date 2025/3/15 17:56
 */
@Data
public class TransferVo {

  @Schema(description = "商户号")
  private String mchId;

  @Schema(description = "商户AppID")
  private String appId;

  @Schema(description = "package")
  private String packageInfo;

}
