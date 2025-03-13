package com.vscing.common.service.applet.impl.wechat.extend;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Transfer
 *
 * @author vscing
 * @date 2025/3/13 21:16
 */
@Getter
@Setter
public class Transfer {

  @SerializedName("out_bill_no")
  private String outBillNo;

  @SerializedName("transfer_bill_no")
  private String transferBillNo;

  @SerializedName("state")
  private String state;

  @SerializedName("mch_id")
  private String mchId;

  @SerializedName("transfer_amount")
  private Integer transferAmount;

  @SerializedName("openid")
  private String openid;

  @SerializedName("fail_reason")
  private String failReason;

  @SerializedName("create_time")
  private String createTime;

  @SerializedName("update_time")
  private String updateTime;

}
