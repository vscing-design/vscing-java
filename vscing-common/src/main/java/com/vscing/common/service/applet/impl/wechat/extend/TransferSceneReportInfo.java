package com.vscing.common.service.applet.impl.wechat.extend;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.function.UnaryOperator;

/**
 * TransferSceneReportInfo
 *
 * @author vscing
 * @date 2025/3/9 23:28
 */
@Getter
@Setter
public class TransferSceneReportInfo {

  @SerializedName("info_type")
  private String infoType;

  @SerializedName("info_content")
  private String infoContent;

  // 静态内部类 Builder
  public static class Builder {
    private final TransferSceneReportInfo reportInfo;

    public Builder() {
      this.reportInfo = new TransferSceneReportInfo();
    }

    public Builder setInfoType(String infoType) {
      reportInfo.infoType = infoType;
      return this;
    }

    public Builder setInfoContent(String infoContent) {
      reportInfo.infoContent = infoContent;
      return this;
    }

    public TransferSceneReportInfo build() {
      return reportInfo;
    }
  }

  public TransferSceneReportInfo cloneWithCipher(UnaryOperator<String> s) {
    TransferSceneReportInfo copy = new TransferSceneReportInfo();
    copy.infoType = this.infoType;
    copy.infoContent = this.infoContent;

    return copy;
  }

}
