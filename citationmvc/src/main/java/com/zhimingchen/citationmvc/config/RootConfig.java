/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citationmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhiming
 *
 */
@Configuration
@Import(MongoConfig.class)
public class RootConfig {
    
}
