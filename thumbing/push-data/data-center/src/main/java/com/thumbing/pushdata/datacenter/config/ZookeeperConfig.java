package com.thumbing.pushdata.datacenter.config;

import com.thumbing.pushdata.common.config.BaseZookeeperConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Stan Sai
 * @date 2020-06-20
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "push-data.zookeeper")
public class ZookeeperConfig extends BaseZookeeperConfig {

}
