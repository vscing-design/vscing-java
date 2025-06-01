package com.vscing.admin.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * UploadService
 *
 * @author vscing
 * @date 2025/3/1 18:24
 */
public interface UploadService {

  /**
   * 上传文件
  */
  String put(MultipartFile file, String module) throws IOException;

  /**
   * 上传文件
   */
  String put(String url, String module) throws IOException;

}
