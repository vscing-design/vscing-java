package com.vscing.common.service.storage.impl.qiniu;

import com.vscing.common.service.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * AppletServiceImpl
 *
 * @author vscing
 * @date 2025/1/7 19:25
 */
@Slf4j
@Service("qiniuStorageService")
public class StorageServiceImpl implements StorageService {

  @Autowired
  private StorageProperties storageProperties;

  @Override
  public String put(String key, File file) {
    return "";
  }

  @Override
  public String put(String key, InputStream inputStream) {
    return "";
  }

  @Override
  public void delete(String key) {

  }
}
