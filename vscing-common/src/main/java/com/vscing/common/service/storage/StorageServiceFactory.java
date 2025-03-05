package com.vscing.common.service.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * StorageServiceFactory
 * 文件存储封装工厂类
 * @author vscing
 * @date 2025/1/12 19:58
 */
@Component
public class StorageServiceFactory {

    public static final String ALIYUN = "aliyun";

    public static final String QINIU = "qiniu";

    private final Map<String, StorageService> storageServices;

    @Autowired
    public StorageServiceFactory(Map<String, StorageService> storageServices) {
        this.storageServices = storageServices;
    }

    public StorageService getStorageService(String type) {
        if (ALIYUN.equalsIgnoreCase(type)) {
            return this.storageServices.get("aliyunStorageService");
        } else if (QINIU.equalsIgnoreCase(type)) {
            return this.storageServices.get("qiniuStorageService");
        } else {
            throw new IllegalArgumentException("Unknown payment type: " + type);
        }
    }

}
