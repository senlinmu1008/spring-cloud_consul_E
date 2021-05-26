/**
 * Copyright (C), 2015-2019
 */
package net.zhaoxiaobin.server.domain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhaoxb
 * @create 2019-12-18 09:59
 */
@ConfigurationProperties(prefix = "config")
@Component
@Data
public class ServerProperties {
    private String date;
}