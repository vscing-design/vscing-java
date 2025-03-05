package com.vscing.common.service.storage.impl.aliyun;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.vscing.common.service.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * StorageServiceImpl
 *
 * @author vscing
 * @date 2025/1/7 19:25
 */
@Slf4j
@Service("aliyunStorageService")
public class StorageServiceImpl implements StorageService {

  @Autowired
  private StorageProperties storageProperties;

  /**
   * 配置文件
   */
  private OSS getOssClient() {
    ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
    clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

    return OSSClientBuilder.create()
        .endpoint(storageProperties.getEndpoint())
        .credentialsProvider(new DefaultCredentialProvider(
            storageProperties.getAccessKeyId(),
            storageProperties.getAccessKeySecret()))
        .clientConfiguration(clientBuilderConfiguration)
        .region(storageProperties.getRegion())  // 如果需要指定区域
        .build();
  }

  @Override
  public String put(String key, InputStream inputStream) throws IOException {
    OSS ossClient = getOssClient();
    try {
      // 创建PutObjectRequest对象。
      PutObjectRequest putObjectRequest = new PutObjectRequest(storageProperties.getBucketName(), key, inputStream);
      // 创建PutObject请求。
      ossClient.putObject(putObjectRequest);
      return storageProperties.getDomain() + "/" + key;
    } catch (OSSException oe) {
      throw new IOException(oe.getMessage());
    } catch (ClientException ce) {
      throw new IOException(ce.getMessage());
    } finally {
      if (ossClient != null) {
        ossClient.shutdown();
      }
    }
  }

  @Override
  public void delete(String key) throws IOException {
    OSS ossClient = getOssClient();
    try {
      // 删除文件或目录。如果要删除目录，目录必须为空。
      ossClient.deleteObject(storageProperties.getBucketName(), key);
    } catch (OSSException oe) {
      throw new IOException(oe.getMessage());
    } catch (ClientException ce) {
      throw new IOException(ce.getMessage());
    } finally {
      if (ossClient != null) {
        ossClient.shutdown();
      }
    }
  }

}
