package com.vscing.common.service.applet.impl.wechat.extend;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * InitiateBillTransferResponse
 *
 * @author vscing
 * @date 2025/3/9 23:38
 */
@Data
public class InitiateBillTransferResponse {

  @SerializedName("mch_id")
  private String mchId;
  @SerializedName("app_id")
  private String appId;
  @SerializedName("out_bill_no")
  private String outBillNo;
  @SerializedName("transfer_bill_no")
  private String transferBillNo;
  @SerializedName("create_time")
  private String createTime;
  @SerializedName("state")
  private String state;
  @SerializedName("fail_reason")
  private String failReason;
  @SerializedName("package_info")
  private String packageInfo;

  public InitiateBillTransferResponse() {
  }

}
