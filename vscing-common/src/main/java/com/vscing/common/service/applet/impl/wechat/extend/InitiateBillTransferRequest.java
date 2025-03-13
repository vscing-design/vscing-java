package com.vscing.common.service.applet.impl.wechat.extend;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * InitiateBillTransferRequest
 *
 * @author vscing
 * @date 2025/3/9 23:21
 */
@Getter
@Setter
public class InitiateBillTransferRequest {

  @SerializedName("appid")
  private String appid;
  @SerializedName("out_bill_no")
  private String outBillNo;
  @SerializedName("transfer_scene_id")
  private String transferSceneId;
  @SerializedName("openid")
  private String openid;
  @SerializedName("user_name")
  private String userName;
  @SerializedName("transfer_amount")
  private Integer transferAmount;
  @SerializedName("transfer_remark")
  private String transferRemark;
  @SerializedName("notify_url")
  private String notifyUrl;
  @SerializedName("user_recv_perception")
  private String userRecvPerception;
  @SerializedName("transfer_scene_report_infos")
  private List<TransferSceneReportInfo> transferSceneReportInfos = new ArrayList<>();

  public InitiateBillTransferRequest() {
  }

  public InitiateBillTransferRequest cloneWithCipher(UnaryOperator<String> s) {
    InitiateBillTransferRequest copy = new InitiateBillTransferRequest();
    copy.appid = this.appid;
    copy.outBillNo = this.outBillNo;
    copy.transferSceneId = this.transferSceneId;
    copy.openid = this.openid;
    copy.userName = this.userName;
    copy.transferAmount = this.transferAmount;
    copy.transferRemark = this.transferRemark;
    copy.notifyUrl = this.notifyUrl;
    copy.userRecvPerception = this.userRecvPerception;

    if (this.transferSceneReportInfos != null && !this.transferSceneReportInfos.isEmpty()) {
      copy.transferSceneReportInfos = new ArrayList<>();

      for(TransferSceneReportInfo val : this.transferSceneReportInfos) {
        if (val != null) {
          copy.transferSceneReportInfos.add(val.cloneWithCipher(s));
        }
      }
    }

    return copy;
  }

}
