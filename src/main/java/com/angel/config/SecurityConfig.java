package com.angel.config;

import com.angel.handler.MyAuthenticationFailureHandler;
import com.angel.handler.MyAuthenticationSucessHandler;
import com.angel.validate.code.ValidateCodeFilter;
import com.angel.validate.smscode.SmsAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author 陈明
 * @date 2019/9/5 11:11
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private MyAuthenticationSucessHandler authenticationSucessHandler;
	
	@Autowired
	private MyAuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private ValidateCodeFilter validateCodeFilter;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SmsAuthenticationConfig smsAuthenticationConfig;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		jdbcTokenRepository.setCreateTableOnStartup(false);
		return jdbcTokenRepository;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin()
				.loginPage("/login.html")
				.loginProcessingUrl("/login")
				.successHandler(authenticationSucessHandler)
				.failureHandler(authenticationFailureHandler)
				.and()
				.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(10)//过期时间
				.and()
				.authorizeRequests() // 授权配置
				.antMatchers("/login.html","/code/image","/code/sms").permitAll() // 登录跳转 URL 无需认证
				.anyRequest()  // 所有请求
				.authenticated()
				.and()
				.csrf().disable()
		.apply(smsAuthenticationConfig);
				
	}
}
