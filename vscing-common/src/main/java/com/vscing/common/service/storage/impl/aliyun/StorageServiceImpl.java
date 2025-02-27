package com.vscing.common.service.storage.impl.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.vscing.common.service.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
  private OSS ossClient;

  @Autowired
  private StorageProperties storageProperties;

  @Override
  public String put(String key, File file) {
    try {
      // 创建PutObjectRequest对象。
      PutObjectRequest putObjectRequest = new PutObjectRequest(storageProperties.getBucketName(), key, file);
      // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
      // ObjectMetadata metadata = new ObjectMetadata();
      // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
      // metadata.setObjectAcl(CannedAccessControlList.Private);
      // putObjectRequest.setMetadata(metadata);

      // 上传文件。
      PutObjectResult result = ossClient.putObject(putObjectRequest);
    } catch (OSSException oe) {
      System.out.println("Caught an OSSException, which means your request made it to OSS, "
          + "but was rejected with an error response for some reason.");
      System.out.println("Error Message:" + oe.getErrorMessage());
      System.out.println("Error Code:" + oe.getErrorCode());
      System.out.println("Request ID:" + oe.getRequestId());
      System.out.println("Host ID:" + oe.getHostId());
    } catch (ClientException ce) {
      System.out.println("Caught an ClientException, which means the client encountered "
          + "a serious internal problem while trying to communicate with OSS, "
          + "such as not being able to access the network.");
      System.out.println("Error Message:" + ce.getMessage());
    } finally {
      if (ossClient != null) {
        ossClient.shutdown();
      }
    }
    return "";
  }

  @Override
  public String put(String key, InputStream inputStream) {
    try {
      // 创建PutObjectRequest对象。
      PutObjectRequest putObjectRequest = new PutObjectRequest(storageProperties.getBucketName(), key, inputStream);
      // 创建PutObject请求。
      PutObjectResult result = ossClient.putObject(putObjectRequest);
    } catch (OSSException oe) {
      System.out.println("Caught an OSSException, which means your request made it to OSS, "
          + "but was rejected with an error response for some reason.");
      System.out.println("Error Message:" + oe.getErrorMessage());
      System.out.println("Error Code:" + oe.getErrorCode());
      System.out.println("Request ID:" + oe.getRequestId());
      System.out.println("Host ID:" + oe.getHostId());
    } catch (ClientException ce) {
      System.out.println("Caught an ClientException, which means the client encountered "
          + "a serious internal problem while trying to communicate with OSS, "
          + "such as not being able to access the network.");
      System.out.println("Error Message:" + ce.getMessage());
    } finally {
      if (ossClient != null) {
        ossClient.shutdown();
      }
    }

    return "";
  }

  @Override
  public void delete(String key) {
    try {
      // 删除文件或目录。如果要删除目录，目录必须为空。
      ossClient.deleteObject(storageProperties.getBucketName(), key);
    } catch (OSSException oe) {
      System.out.println("Caught an OSSException, which means your request made it to OSS, "
          + "but was rejected with an error response for some reason.");
      System.out.println("Error Message:" + oe.getErrorMessage());
      System.out.println("Error Code:" + oe.getErrorCode());
      System.out.println("Request ID:" + oe.getRequestId());
      System.out.println("Host ID:" + oe.getHostId());
    } catch (ClientException ce) {
      System.out.println("Caught an ClientException, which means the client encountered "
          + "a serious internal problem while trying to communicate with OSS, "
          + "such as not being able to access the network.");
      System.out.println("Error Message:" + ce.getMessage());
    } finally {
      if (ossClient != null) {
        ossClient.shutdown();
      }
    }
  }

}
