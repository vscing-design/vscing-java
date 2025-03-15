package com.vscing.common.service.applet.impl.wechat.extend;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * TransferCallbackResource
 *
 * @author vscing
 * @date 2025/3/15 23:40
 */
@Getter
@Setter
public class TransferCallbackResource {

  @SerializedName("original_type")
  private String originalType;

  @SerializedName("algorithm")
  private String algorithm;

  @SerializedName("ciphertext")
  private String ciphertext;

  @SerializedName("associated_data")
  private String associatedData;

  @SerializedName("nonce")
  private String nonce;

}
