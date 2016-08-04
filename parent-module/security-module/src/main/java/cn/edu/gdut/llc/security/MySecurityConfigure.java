package cn.edu.gdut.llc.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import cn.edu.gdut.llc.security.hanlder.AccessDeniedJsonHanlder;
import cn.edu.gdut.llc.security.hanlder.AuthenticationEntryPointHandler;
import cn.edu.gdut.llc.security.hanlder.AuthenticationFailureJsonHandler;
import cn.edu.gdut.llc.security.hanlder.AuthenticationSuccessJsonHandler;
import cn.edu.gdut.llc.security.hanlder.LogoutSuccessJsonHandler;
import cn.edu.gdut.llc.security.service.CustomUserDetailsService;
import cn.edu.gdut.llc.security.util.SecurityUtil;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class MySecurityConfigure extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	@Autowired
	private DataSource dataSource;
	@Autowired 
	private CustomUserDetailsService customUserDetailsService;
	@Autowired 
	private AccessDeniedJsonHanlder accessDeniedJsonHanlder;
	@Autowired 
	private AuthenticationFailureJsonHandler authenticationFailureJsonHandler;
	@Autowired 
	private AuthenticationSuccessJsonHandler authenticationSuccessJsonHandler;
	@Autowired 
	private LogoutSuccessJsonHandler logoutSuccessJsonHandler;
	@Autowired 
	private AuthenticationEntryPointHandler authenticationEntryPointHandler;
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
		.maximumSessions(1)
		.expiredUrl(env.getProperty("session.expires.url"))
		.sessionRegistry(sessionRegistry());
		
		http.authorizeRequests()
		.and().formLogin().permitAll()
		.successHandler(authenticationSuccessJsonHandler)
		.failureHandler(authenticationFailureJsonHandler)
		.and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessJsonHandler).permitAll()
		.invalidateHttpSession(true)
		.and().rememberMe().tokenValiditySeconds(SecurityUtil.getTimeout(env.getProperty("login.timeout").trim()))
		.tokenRepository(tokenRepository())
		.and().csrf().disable();
		
		http.exceptionHandling().accessDeniedHandler(accessDeniedJsonHanlder)
		.authenticationEntryPoint(authenticationEntryPointHandler);
	}

	@Autowired 
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//指定密码加密所使用的加密器为passwordEncoder() 
		//需要将密码加密后写入数据库 
		auth.userDetailsService(customUserDetailsService);
		//不删除凭据，以便记住用户  
		auth.eraseCredentials(false);
	}

	private PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl=new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}

}
