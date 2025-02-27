package com.vscing.common.service.storage;

import java.io.File;
import java.io.InputStream;

/**
 * StorageService
 * 文件存储封装
 * @author vscing
 * @date 2025/1/12 19:58
 */
public interface StorageService {

    /**
     * 上传文件
     * @param key 文件oss的路径、不包含bucketName
     * @param file 文件资源
     * @return 公共url
     */
    String put(String key, File file);

    /**
     * 上传文件
     * @param key 文件oss的路径、不包含bucketName
     * @param inputStream 流资源
     * @return 公共url
     */
    String put(String key, InputStream inputStream);

    /**
     * 删除文件
     * @param key 文件oss的路径、不包含bucketName
     */
    void delete(String key);

}
