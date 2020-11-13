package com.hausen.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.config.Ini;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.security.krb5.Realm;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    /*@Bean
    public IniRealm getIniRealm(){

        return new IniRealm("classpath:shiro.ini");
    }*/

    @Bean
    public JdbcRealm getJdbcRealm(DataSource dataSource){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        return jdbcRealm;
    }

    @Bean
    public DefaultWebSecurityManager getSecurityManager(JdbcRealm jdbcRealm){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(jdbcRealm);

        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){

        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();

        filter.setSecurityManager(securityManager);

        Map<String,String> filterMap = new HashMap<String, String>();
        filterMap.put("/","anon");
        filterMap.put("/login.html","anon");
        filterMap.put("/index.html","anon");
        filterMap.put("register.html","anon");
        filterMap.put("/user/login","anon");
        filterMap.put("/user/register.html","anon");
        filterMap.put("/static/**","anon");
        filterMap.put("/**","authc");

        filter.setFilterChainDefinitionMap(filterMap);

        filter.setLoginUrl("/");
        // 设置未授权提示url
        filter.setUnauthorizedUrl("/login.html");
        return filter;

    }
}
