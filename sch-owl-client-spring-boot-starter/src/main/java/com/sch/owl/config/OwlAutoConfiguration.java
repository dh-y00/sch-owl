package com.sch.owl.config;

import cn.hutool.core.map.MapUtil;
import com.rdrk.rsf.framework.EnableRsfFramework;
import com.sch.owl.ILoginAuthenticationTokenVerity;
import com.sch.owl.ITokenConfig;
import com.sch.owl.config.security.PermitAllUrlProperties;
import com.sch.owl.config.security.SecurityConfig;
import com.sch.owl.config.security.filter.JwtAuthenticationTokenFilter;
import com.sch.owl.config.security.handle.AuthenticationEntryPointImpl;
import com.sch.owl.config.security.handle.SimpleLogoutHandlerImpl;
import com.sch.owl.properties.OwlBaseProperties;
import com.sch.owl.properties.OwlServerProperties;
import com.sch.owl.properties.OwlSseProperties;
import com.sch.owl.token.TokenDeal;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Map;

@Configuration
@EnableConfigurationProperties({OwlServerProperties.class, OwlBaseProperties.class, OwlSseProperties.class})
@EnableScheduling
@EnableRsfFramework
//@AutoConfigureOrder(99)
@Import({SecurityConfig.class, OwlRemoteConfiguration.class, OwlPermissionConfiguration.class, OwlSseConfiguration.class})
public class OwlAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(ILoginAuthenticationTokenVerity loginAuthenticationTokenVerity,
                                                                     TokenDeal tokenDeal) {
        return new JwtAuthenticationTokenFilter(loginAuthenticationTokenVerity, tokenDeal);
    }

    @Bean
    public PermitAllUrlProperties permitAllUrlProperties() {
        return new PermitAllUrlProperties();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint()
    {
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 退出处理器
     * @return 返回退出处理器
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        try {
            Map<String, LogoutSuccessHandler> beansOfType = applicationContext.getBeansOfType(LogoutSuccessHandler.class);
            if(MapUtil.isNotEmpty(beansOfType) && beansOfType.size() == 1) {
                for (LogoutSuccessHandler value : beansOfType.values()) {
                    return value;
                }
            }
        }catch (Exception e) {}
        return new SimpleLogoutHandlerImpl();
    }

    @Bean
    public TokenDeal tokenDeal(ITokenConfig tokenConfig) {
        return new TokenDeal(tokenConfig);
    }

    /**
     * 跨域配置
     */
    @Bean("upmsCorsFilter")
    public CorsFilter corsFilter()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }
}
