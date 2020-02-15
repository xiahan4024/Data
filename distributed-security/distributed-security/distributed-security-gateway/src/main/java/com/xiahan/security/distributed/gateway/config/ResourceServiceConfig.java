package com.xiahan.security.distributed.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Auther: xiahan
 * @Date: 2020/2/9 16:14
 * @Description:
 */
@Configuration
public class ResourceServiceConfig {

    // 与认证服务器的id 要一致
    public static final String RESOURCE_ID = "res1";

    /**
     * 统一认证服务(UAA) 资源拦截
     */
    @Configuration
    @EnableResourceServer
    public class UAAServerConfig extends ResourceServerConfigurerAdapter {

        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources){
            resources.tokenStore(tokenStore)
                    .resourceId(RESOURCE_ID)
                    .stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // 认证服务需要暴露  即不需要了权限
            http.authorizeRequests()
                .antMatchers("/uaa/**").permitAll();
        }
    }

    /**
     * 订单服务
     */
    @Configuration
    @EnableResourceServer
    public class OrderServerConfig extends ResourceServerConfigurerAdapter {

        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.tokenStore(tokenStore)
                    .resourceId(RESOURCE_ID)
                    .stateless(true);
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            // 这个服务 不是所有的客户端都能访问，即需要放的客户端拥有权限才行
            http
                .authorizeRequests()
                .antMatchers("/order/**").access("#oauth2.hasScope('ROLE_API')");
        }
    }


}
