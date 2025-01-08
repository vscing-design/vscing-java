package com.vscing.common.service.applet.impl.alipay;

import cn.hutool.http.HttpException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConfig;
import com.alipay.api.internal.util.AlipayEncrypt;
import com.alipay.api.internal.util.AlipaySignature;
import com.vscing.common.service.OkHttpService;
import com.vscing.common.service.applet.AppletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * AppletServiceImpl
 *
 * @author vscing
 * @date 2025/1/7 19:25
 */
@Slf4j
@Service("alipayAppletService")
public class AppletServiceImpl implements AppletService {

  private static final String ALIPAY_BASH_URL = "https://openapi.alipay.com/gateway.do";

  @Autowired
  private OkHttpService okHttpService;

  @Autowired
  private AppletProperties appletProperties;

  public AlipayConfig getAlipayConfig() {
    AlipayConfig alipayConfig = new AlipayConfig();
    //设置连接池中的最大可缓存的空闲连接数
    alipayConfig.setMaxIdleConnections(5);
    //连接超时，单位：毫秒，默认3000
    alipayConfig.setConnectTimeout(3000);
    //读取超时，单位：毫秒，默认15000
    alipayConfig.setReadTimeout(15000);
    //空闲连接存活时间，单位：毫秒，默认10000L
    alipayConfig.setKeepAliveDuration(10000L);
    //设置网关地址
    alipayConfig.setServerUrl(ALIPAY_BASH_URL);
    //设置请求格式，固定值json
    alipayConfig.setFormat("JSON");
    //设置字符集
    alipayConfig.setCharset("UTF-8");
    //设置签名类型
    alipayConfig.setSignType("RSA2");
    //设置应用ID
    alipayConfig.setAppId(appletProperties.getAppId());
    //设置应用私钥
    alipayConfig.setPrivateKey(appletProperties.getPrivateKey());
    //设置应用公钥证书路径
    alipayConfig.setAppCertPath(appletProperties.getAppCertPath());
    //设置支付宝公钥证书路径
    alipayConfig.setAlipayPublicCertPath(appletProperties.getAlipayPublicCertPath());
    //设置支付宝根证书路径
    alipayConfig.setRootCertPath(appletProperties.getRootCertPath());
    // 返回配置
    return alipayConfig;
  }

  @Override
  public String getPhoneNumber(String code) {
    try {
      //1. 获取验签和解密所需要的参数
      Map<String, String> openapiResult = JSON.parseObject(code, new TypeReference<Map<String, String>>() {}, Feature.OrderedField);
      String signType = "RSA2";
      String charset = "UTF-8";
      String encryptType = "AES";
      String sign = openapiResult.get("sign");
      String content = openapiResult.get("response");
      //判断是否为加密内容
      boolean isDataEncrypted = !content.startsWith("{");
      boolean signCheckPass = false;
      //2. 验签
      String signContent = content;
      String signVeriKey = "-----BEGIN CERTIFICATE-----\n" +
          "MIIDsjCCApqgAwIBAgIQICUBB5qebmqcCr2QJWW9GjANBgkqhkiG9w0BAQsFADCBgjELMAkGA1UE\n" +
          "BhMCQ04xFjAUBgNVBAoMDUFudCBGaW5hbmNpYWwxIDAeBgNVBAsMF0NlcnRpZmljYXRpb24gQXV0\n" +
          "aG9yaXR5MTkwNwYDVQQDDDBBbnQgRmluYW5jaWFsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5IENs\n" +
          "YXNzIDIgUjEwHhcNMjUwMTA3MTIxNDM0WhcNMzAwMTA2MTIxNDM0WjCBkjELMAkGA1UEBhMCQ04x\n" +
          "LTArBgNVBAoMJOatpuaxieWXqOS4q+aWh+WMluS8oOWqkuaciemZkOWFrOWPuDEPMA0GA1UECwwG\n" +
          "QWxpcGF5MUMwQQYDVQQDDDrmlK/ku5jlrp0o5Lit5Zu9Kee9kee7nOaKgOacr+aciemZkOWFrOWP\n" +
          "uC0yMDg4OTQxNjk3NjY0MjQ0MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmMkXD9T5\n" +
          "gU/IUjZrCj2EcE4FdWzmsD/TqBrlut+Xcv5YCGRUxOFkkBzztB+LweOiER3y/p35B3ww4oev2fK3\n" +
          "JKlUz1G+h18d56EXphP7JU0KbfzDv4VTC9pizdIHajgjbKzFS53gqHRel9ObgAkLA1g0K6dlZ5Ps\n" +
          "zFXYLRn31mdu4Njm2w0fZflYpVk2z0yfVjtFVXvu69qjBBxno2n07QrmvCODMcU9eQkyK2vcx2s1\n" +
          "0YLz5iVEe/pSellfei9d3xVIUUIhVt3eigOkB/a58HHjnUurs4t/ANA2a2BcVO+82loaovD7rYoT\n" +
          "e9Gb07QTpaSg6gg4BJHz91cvy8aw8QIDAQABoxIwEDAOBgNVHQ8BAf8EBAMCA/gwDQYJKoZIhvcN\n" +
          "AQELBQADggEBAAY1fxBc6zJQHXThOZ8T/s9bPeu+wo+qoP59rTurSD/bIoKLEUJqjpR5VqRZaFMI\n" +
          "F6qmlwIcLjvK9rJRawVeEDXM7VKTzWoS9oRP4kbZSIq0CMhcDwIjTgvnMes6oiYvmqcURFoo0yee\n" +
          "u15RG8o9FCDA/aqT9sSsJCSyJYxyitOhYJfC2rxmKd5xMTRzmhN1dmeN3ewqSLe+gCy9lVQeunYx\n" +
          "2LWiI4ebFU0wOQ6a/TytRM4U1Dtir8Vg8jrZtwtYi2Id0eIYpeyzxoNHHSkHK2x7zuFd8ad9oP+4\n" +
          "Qwqt0AFyuJZrDa/CyoisEitz0Un5DAHg7lXnIzeqJv/dB7Zm7h0=\n" +
          "-----END CERTIFICATE-----\n" +
          "-----BEGIN CERTIFICATE-----\n" +
          "MIIE4jCCAsqgAwIBAgIIYsSr5bKAMl8wDQYJKoZIhvcNAQELBQAwejELMAkGA1UEBhMCQ04xFjAU\n" +
          "BgNVBAoMDUFudCBGaW5hbmNpYWwxIDAeBgNVBAsMF0NlcnRpZmljYXRpb24gQXV0aG9yaXR5MTEw\n" +
          "LwYDVQQDDChBbnQgRmluYW5jaWFsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5IFIxMB4XDTE4MDMy\n" +
          "MjE0MzQxNVoXDTM3MTEyNjE0MzQxNVowgYIxCzAJBgNVBAYTAkNOMRYwFAYDVQQKDA1BbnQgRmlu\n" +
          "YW5jaWFsMSAwHgYDVQQLDBdDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTE5MDcGA1UEAwwwQW50IEZp\n" +
          "bmFuY2lhbCBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eSBDbGFzcyAyIFIxMIIBIjANBgkqhkiG9w0B\n" +
          "AQEFAAOCAQ8AMIIBCgKCAQEAsLMfYaoRoPRbmDcAfXPCmKf43pWRN5yTXa/KJWO0l+mrgQvs89bA\n" +
          "NEvbDUxlkGwycwtwi5DgBuBgVhLliXu+R9CYgr2dXs8D8Hx/gsggDcyGPLmVrDOnL+dyeauheARZ\n" +
          "fA3du60fwEwwbGcVIpIxPa/4n3IS/ElxQa6DNgqxh8J9Xwh7qMGl0JK9+bALuxf7B541Gr4p0WEN\n" +
          "G8fhgjBV4w4ut9eQLOoa1eddOUSZcy46Z7allwowwgt7b5VFfx/P1iKJ3LzBMgkCK7GZ2kiLrL7R\n" +
          "iqV+h482J7hkJD+ardoc6LnrHO/hIZymDxok+VH9fVeUdQa29IZKrIDVj65THQIDAQABo2MwYTAf\n" +
          "BgNVHSMEGDAWgBRfdLQEwE8HWurlsdsio4dBspzhATAdBgNVHQ4EFgQUSqHkYINtUSAtDPnS8Xoy\n" +
          "oP9p7qEwDwYDVR0TAQH/BAUwAwEB/zAOBgNVHQ8BAf8EBAMCAQYwDQYJKoZIhvcNAQELBQADggIB\n" +
          "AIQ8TzFy4bVIVb8+WhHKCkKNPcJe2EZuIcqvRoi727lZTJOfYy/JzLtckyZYfEI8J0lasZ29wkTt\n" +
          "a1IjSo+a6XdhudU4ONVBrL70U8Kzntplw/6TBNbLFpp7taRALjUgbCOk4EoBMbeCL0GiYYsTS0mw\n" +
          "7xdySzmGQku4GTyqutIGPQwKxSj9iSFw1FCZqr4VP4tyXzMUgc52SzagA6i7AyLedd3tbS6lnR5B\n" +
          "L+W9Kx9hwT8L7WANAxQzv/jGldeuSLN8bsTxlOYlsdjmIGu/C9OWblPYGpjQQIRyvs4Cc/mNhrh+\n" +
          "14EQgwuemIIFDLOgcD+iISoN8CqegelNcJndFw1PDN6LkVoiHz9p7jzsge8RKay/QW6C03KNDpWZ\n" +
          "EUCgCUdfHfo8xKeR+LL1cfn24HKJmZt8L/aeRZwZ1jwePXFRVtiXELvgJuM/tJDIFj2KD337iV64\n" +
          "fWcKQ/ydDVGqfDZAdcU4hQdsrPWENwPTQPfVPq2NNLMyIH9+WKx9Ed6/WzeZmIy5ZWpX1TtTolo6\n" +
          "OJXQFeItMAjHxW/ZSZTok5IS3FuRhExturaInnzjYpx50a6kS34c5+c8hYq7sAtZ/CNLZmBnBCFD\n" +
          "aMQqT8xFZJ5uolUaSeXxg7JFY1QsYp5RKvj4SjFwCGKJ2+hPPe9UyyltxOidNtxjaknOCeBHytOr\n" +
          "-----END CERTIFICATE-----";
      String decryptKey = appletProperties.getAes();
      if (isDataEncrypted) {
        signContent = "\"" + signContent + "\"";
      } try {
        signCheckPass = AlipaySignature.rsaCheck(signContent, sign, signVeriKey, charset, signType);
      } catch (AlipayApiException e) {
        // 验签异常, 日志
      } if (!signCheckPass) {
        //验签不通过（异常或者报文被篡改），终止流程（不需要做解密）
        throw new Exception("验签失败");
      }
      //3. 解密
      String plainData = null;
      if (isDataEncrypted) {
        try {
          plainData = AlipayEncrypt.decryptContent(content, encryptType, decryptKey, charset);
        } catch (AlipayApiException e) {
          //解密异常, 记录日志
          throw new Exception("解密异常");
        }
      } else {
        plainData = content;
      }
      return plainData;
    } catch (Exception e) {
      log.error("Unexpected error while refreshing stable access token.", e);
      throw new HttpException("Unexpected error refreshing stable access token: " + e.getMessage(), e);
    }
  }

}
