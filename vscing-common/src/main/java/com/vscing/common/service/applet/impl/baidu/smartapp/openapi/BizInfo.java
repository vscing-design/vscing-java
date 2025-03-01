package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import com.google.gson.annotations.SerializedName;

/**
 * BizInfo
 *
 * @author vscing
 * @date 2025/2/26 01:08
 */
public class BizInfo {
  @SerializedName("swanFrom")
  private String swanFrom;

  @SerializedName("cuid")
  private String cuid;

  @SerializedName("appId")
  private String appId; // 字符串类型的 appId

  @SerializedName("session_id")
  private String sessionId;

  @SerializedName("launch_id")
  private String launchId;

  @SerializedName("nativeAppId")
  private String nativeAppId;

  @SerializedName("platformId")
  private String platformId;

  @SerializedName("isAccountDataFlow")
  private int isAccountDataFlow;

  @SerializedName("isFoldChannel")
  private int isFoldChannel;

  // Getters and setters
  public String getSwanFrom() {
    return swanFrom;
  }

  public void setSwanFrom(String swanFrom) {
    this.swanFrom = swanFrom;
  }

  public String getCuid() {
    return cuid;
  }

  public void setCuid(String cuid) {
    this.cuid = cuid;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getLaunchId() {
    return launchId;
  }

  public void setLaunchId(String launchId) {
    this.launchId = launchId;
  }

  public String getNativeAppId() {
    return nativeAppId;
  }

  public void setNativeAppId(String nativeAppId) {
    this.nativeAppId = nativeAppId;
  }

  public String getPlatformId() {
    return platformId;
  }

  public void setPlatformId(String platformId) {
    this.platformId = platformId;
  }

  public int getIsAccountDataFlow() {
    return isAccountDataFlow;
  }

  public void setIsAccountDataFlow(int isAccountDataFlow) {
    this.isAccountDataFlow = isAccountDataFlow;
  }

  public int getIsFoldChannel() {
    return isFoldChannel;
  }

  public void setIsFoldChannel(int isFoldChannel) {
    this.isFoldChannel = isFoldChannel;
  }
}
