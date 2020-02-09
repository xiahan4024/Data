package com.xiahan.security.distributed.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @Auther: xiahan
 * @Date: 2020/2/8 21:48
 * @Description:
 */
@Configuration
public class TokenConfig {

    @Bean
    public TokenStore tokenStore() {
        // 使用内存的方式存储令牌（普通令牌）
        return new InMemoryTokenStore();
    }
}
