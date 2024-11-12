package com.example.temp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {  
    
    @Autowired 
    AuthUserProvider authUserProvider;
    
    @Autowired 
    AuthAdminProvider authAdminProvider;
 
    @Autowired 
    AuthSuccessHandler authSuccessHandler;
    
    @Autowired 
    AuthFailureHandler authFailureHandler;
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authUserProvider);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.authorizeRequests()
        // 페이지 권한 설정
    	.antMatchers("/prv/**").permitAll()
		.antMatchers("/comn/**").permitAll()
		.antMatchers("/mngr/**").hasRole("ADMIN")
		.antMatchers("/cstmr/myPage/**").hasAnyRole("USER","RETURN")
		.antMatchers("/cstmr/**").hasAnyRole("USER","RETURN");
        //.antMatchers("/**").permitAll();
    	
		/* http.csrf().disable(); */

    	http// 로그인 설정
        .formLogin()
        .loginPage("/prv/login")
        .loginPage("/comn/login")
        .loginPage("/")
        .loginProcessingUrl("/loginProcess")
        .failureHandler(authFailureHandler) //로그인 실패시 수행하는 클래스
        .successHandler(authSuccessHandler); // 로그인 성공시 수행하는 클래스
    	
        
        http // 로그아웃 설정
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/comn/logout"))
        .logoutSuccessUrl("/comn/login")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID") /*쿠키 제거*/
        .clearAuthentication(true);
        
        http.sessionManagement() 
        .maximumSessions(10) /* session 허용 갯수 */
        .expiredUrl("/sessionOut") /* session 만료시 이동 페이지*/
        //.maxSessionsPreventsLogin(false) /* 동일한 사용자 로그인시 x, false 일 경우 기존 사용자*/
        .sessionRegistry(sessionRegistry())
        .and()
        .sessionAuthenticationStrategy(new CustomSessionAuthenticationStrategy(sessionRegistry()));
        
        //http.sessionManagement().invalidSessionUrl("/sessionOut").maximumSessions(1).maxSessionsPreventsLogin(false);
        
        
		http.exceptionHandling().accessDeniedPage("/comn/login").accessDeniedHandler(
			  new AccessDeniedHandler() {
				  @Override public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException,ServletException { 
					  // TODO Auto-generated method stub 
					  if(accessDeniedException instanceof MissingCsrfTokenException) { 
						  //Some Exception Handling 
						  response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
				  	}else if (accessDeniedException instanceof InvalidCsrfTokenException) { 
				  		//Some Exception Handling 
				  		response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
			  		} 
		  		}
			  } 
		  );
			 
        
				/* http.headers().frameOptions().disable(); */
        http
        .headers()
           .frameOptions()
              .sameOrigin();
        				

        
    }
    
    //JSP의 리소스 파일이나 자바스크립트 파일이 저장된 경로는 무시를 한다
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        .antMatchers("/api/**","/webhook/**","/resources/**","/assets/**");
    }

	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

    
}
