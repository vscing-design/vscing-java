package com.vscing.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.vscing.admin.service.UploadService;
import com.vscing.common.exception.ServiceException;
import com.vscing.common.service.storage.StorageService;
import com.vscing.common.service.storage.StorageServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * UploadServiceImpl
 *
 * @author vscing
 * @date 2025/3/1 18:25
 */
@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

  @Autowired
  StorageServiceFactory storageServiceFactory;

  public String put(MultipartFile file, String module) {
    try {
      StorageService storageService = storageServiceFactory.getStorageService("aliyun");
      // 文件后缀名
      String suffix = FileUtil.extName(file.getOriginalFilename());
      InputStream inputStream = file.getInputStream();
      // 生成新的文件名（使用UUID确保唯一性）
      String newFileName = IdUtil.getSnowflakeNextId() + "." + suffix;
      String key = module + "/" + DateUtil.format(LocalDateTime.now(), "yyyyMMdd") + "/" + newFileName;
      return storageService.put(key, inputStream);
    } catch (Exception e) {
      throw new ServiceException(e.getMessage());
    }
  }

}
