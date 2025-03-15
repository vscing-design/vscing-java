package com.vscing.common.service.applet.impl.wechat.extend;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * TransferCallback
 *
 * @author vscing
 * @date 2025/3/15 23:37
 */
@Getter
@Setter
public class TransferCallback {

  @SerializedName("id")
  private String id;

  @SerializedName("create_time")
  private String createTime;

  @SerializedName("resource_type")
  private String resourceType;

  @SerializedName("event_type")
  private String eventType;

  @SerializedName("summary")
  private String summary;

  @SerializedName("resource")
  private TransferCallbackResource resource;

}
