package com.vscing.common.service.applet.impl.wechat.extend;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.function.UnaryOperator;

/**
 * TransferSceneReportInfo
 *
 * @author vscing
 * @date 2025/3/9 23:28
 */
@Data
public class TransferSceneReportInfo {

  @SerializedName("info_type")
  private String infoType;

  @SerializedName("info_content")
  private String infoContent;

  public TransferSceneReportInfo cloneWithCipher(UnaryOperator<String> s) {
    TransferSceneReportInfo copy = new TransferSceneReportInfo();
    copy.infoType = this.infoType;
    copy.infoContent = this.infoContent;

    return copy;
  }

}
