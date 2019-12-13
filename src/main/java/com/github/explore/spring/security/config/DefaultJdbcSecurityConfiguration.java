package com.github.explore.spring.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class DefaultJdbcSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJdbcSecurityConfiguration.class);
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        LOGGER.info("Configuring JDBC based authentication");
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(User.withUsername("home").password("home").roles("home"))
                .withUser(User.withUsername("user").password("user").roles("user"))
                .withUser(User.withUsername("admin").password("admin").roles("admin"));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.info("Configuring authorization");
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("admin")
                .antMatchers("/user").hasAnyRole("admin,user")
                .antMatchers("/").permitAll()
                .and().formLogin();
    }
}