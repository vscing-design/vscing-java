package com.vscing.common.service.applet.impl.baidu.smartapp.openapi;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

/**
 * RSASign
 *
 * @author vscing
 * @date 2025/2/21 22:58
 */
public class RSASign {

  private static final String CHARSET = "UTF-8";
  private static final String SIGN_TYPE_RSA = "RSA";
  private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
  private static final String SIGN_KEY = "rsaSign";

  /**
   * 使用私钥生成签名字符串
   *
   * @param params     待签名参数集合
   * @param privateKey 私钥原始字符串
   *
   * @return 签名结果字符串
   *
   * @throws Exception
   */
  public static String sign(Map<String, Object> params, String privateKey) throws Exception {
    isTrue(!CollectionUtils.isEmpty(params), "params is required");
    notNull(privateKey, "privateKey is required");

    String signContent = signContent(params);

    Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
    signature.initSign(getPrivateKeyPKCS8(privateKey));
    signature.update(signContent.getBytes(CHARSET));
    byte[] signed = signature.sign();

    return new String(Base64.getEncoder().encode(signed));
  }

  /**
   * 使用公钥校验签名
   *
   * @param params    入参数据，签名属性名固定为rsaSign
   * @param publicKey 公钥原始字符串
   *
   * @return true 验签通过 | false 验签不通过
   *
   * @throws Exception
   */
  public static boolean checkSign(Map<String, Object> params, String publicKey) throws Exception {
    isTrue(!CollectionUtils.isEmpty(params), "params is required");
    notNull(publicKey, "publicKey is required");

    // sign & content
    String content = signContent(params);
    String rsaSign = params.get(SIGN_KEY).toString();

    // verify
    Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
    signature.initVerify(getPublicKeyX509(publicKey));
    signature.update(content.getBytes(CHARSET));

    return signature.verify(Base64.getDecoder().decode(rsaSign.getBytes(CHARSET)));
  }

  /**
   * 对输入参数进行key过滤排序和字符串拼接
   *
   * @param params 待签名参数集合
   *
   * @return 待签名内容
   *
   * @throws UnsupportedEncodingException
   */
  private static String signContent(Map<String, Object> params) throws UnsupportedEncodingException {
    Map<String, String> sortedParams = new TreeMap<>(Comparator.naturalOrder());
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      String key = entry.getKey();
      if (legalKey(key)) {
        sortedParams.put(key, entry.getValue().toString());
      }
    }

    StringBuilder builder = new StringBuilder();
    if (!CollectionUtils.isEmpty(sortedParams)) {
      for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
        builder.append(entry.getKey());
        builder.append("=");
        builder.append(entry.getValue());
        builder.append("&");
      }
      builder.deleteCharAt(builder.length() - 1);
    }
    return builder.toString();
  }

  /**
   * 将公钥字符串进行Base64 decode之后，生成X509标准公钥
   *
   * @param publicKey 公钥原始字符串
   *
   * @return X509标准公钥
   *
   * @throws InvalidKeySpecException
   * @throws NoSuchAlgorithmException
   */
  private static PublicKey getPublicKeyX509(String publicKey) throws InvalidKeySpecException,
      NoSuchAlgorithmException, UnsupportedEncodingException {
    if (StringUtils.isEmpty(publicKey)) {
      return null;
    }
    KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
    byte[] decodedKey = Base64.getDecoder().decode(publicKey.getBytes(CHARSET));
    return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
  }

  /**
   * 将私钥字符串进行Base64 decode之后，生成PKCS #8标准的私钥
   *
   * @param privateKey 私钥原始字符串
   *
   * @return PKCS #8标准的私钥
   *
   * @throws Exception
   */
  private static PrivateKey getPrivateKeyPKCS8(String privateKey) throws Exception {
    if (StringUtils.isEmpty(privateKey)) {
      return null;
    }
    KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
    byte[] decodedKey = Base64.getDecoder().decode(privateKey.getBytes(CHARSET));
    return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
  }

  /**
   * 有效的待签名参数key值
   * 非空、且非签名字段
   *
   * @param key 待签名参数key值
   *
   * @return true | false
   */
  private static boolean legalKey(String key) {
    return StringUtils.hasText(key) && !SIGN_KEY.equalsIgnoreCase(key);
  }

}
