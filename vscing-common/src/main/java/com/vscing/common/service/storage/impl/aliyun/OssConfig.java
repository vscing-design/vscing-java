package com.vscing.common.service.storage.impl.aliyun;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    public OSS ossClient() {
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

}
