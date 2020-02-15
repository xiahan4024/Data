package com.xiahan.security.distributed.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Auther: xiahan
 * @Date: 2020/2/8 21:09
 * @Description:
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 每个服务的权限要求不一样，作为一个路由，这里不设置权限，在每个资源服务里会设置权限
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and().csrf().disable();
    }

}
