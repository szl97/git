package com.loserclub.pushdata.nodeserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "push-data.zookeeper")
public class ZookeeperConfig {
    private String servers;

    private String namespace;

    private String listenNamespace;

    private int sessionTimeout;

    private int connectionTimeout;

    private int maxRetries;

    private int retriesSleepTime;
}