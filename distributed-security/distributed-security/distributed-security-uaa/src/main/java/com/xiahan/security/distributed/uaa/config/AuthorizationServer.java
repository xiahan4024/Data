package com.xiahan.security.distributed.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @Auther: xiahan
 * @Date: 2020/2/8 21:34
 * @Description:
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService)
                clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }


    // 用来配置客户端详情服务 内存 --> 数据库
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
        throws Exception {
        // 客户端信息存储到数据库
        clients.withClientDetails(clientDetailsService);
    }
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients)
//            throws Exception {
//        clients.inMemory()// 使用in‐memory存储
//                .withClient("c1")// client_id
//                .secret(new BCryptPasswordEncoder().encode("secret")) // 客户密钥
//                .resourceIds("res1")  // 资源列表
//                .authorizedGrantTypes("authorization_code",
//                        "password","client_credentials","implicit","refresh_token")// 该client允许的授权类型 authorization_code,password,refresh_token,implicit,client_credentials
//                .scopes("all")// 允许的授权范围
//                .autoApprove(true) // fasle跳转到授权页面 授权码授权页面
//                //加上验证回调地址
//                .redirectUris("http://www.baidu.com")
////                // 多个服务配置
////                .and()
////                .withClient("c1")// client_id
////                .secret(new BCryptPasswordEncoder().encode("secret")) // 客户密钥
////                .resourceIds("res1")  // 资源列表
////                .authorizedGrantTypes("authorization_code",
////                        "password","client_credentials","implicit","refresh_token")// 该client允许的授权类型 authorization_code,password,refresh_token,implicit,client_credentials
////                .scopes("all")// 允许的授权范围
////                .autoApprove(false) // fasle跳转到授权页面 授权码授权页面
////                //加上验证回调地址
////                .redirectUris("http://www.baidu.com")
//                ;
//    }

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    // 令牌管理服务
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service=new DefaultTokenServices();
        // 客户端信息服务
        service.setClientDetailsService(clientDetailsService);
        // 是否产生刷新令牌
        service.setSupportRefreshToken(true);
        // 令牌存储策略
        service.setTokenStore(tokenStore);

        // 令牌增强 （内存--> JWT增加）
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }

//    @Bean
//    public AuthorizationCodeServices authorizationCodeServices() {
//        // 设置授权码模式的授权码如何存取，暂时采用内存方式
//        return new InMemoryAuthorizationCodeServices();
//    }

    // 设置授权码模式的授权码数据库存取
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }


    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 令牌访问端点
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 密码模式需要
                .authenticationManager(authenticationManager)
                // 授权码模式需要
                .authorizationCodeServices(authorizationCodeServices)
                // 令牌管理服务
                .tokenServices(tokenService())
                // 允许post提交
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }


    // 令牌访问端点策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security
                // /oauth/token_key 公开
                .tokenKeyAccess("permitAll()")
                // /oauth/check_token 公开
                .checkTokenAccess("permitAll()")
                // 允许表单认证
                .allowFormAuthenticationForClients()
        ;
    }


}
