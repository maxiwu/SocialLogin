package com.umedia.CloudGate.jsp;

import javax.sql.DataSource;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import com.umedia.CloudGate.service.MixUserDetailsService;


@Configuration
// @EnableWebSecurity
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		// Spring Security ignores request to static resources such as CSS or JS
		// files.
		.ignoring().antMatchers("/css/**", "/js/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.formLogin().loginPage("/login").defaultSuccessUrl("/home", true).failureUrl("/fail?error=true").permitAll()
		.and().logout().invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/nothing")
		.and().authorizeRequests()
		.antMatchers("/signup/**", "/favicon.ico", "/resources/**", "/resources/css/**", "/css/**", "/user/**").permitAll()
		.antMatchers("/**").hasRole("USER")
		.and().rememberMe()
		.and().apply(new SpringSocialConfigurer().signupUrl("/user/register").postLoginUrl("/home")	
				.alwaysUsePostLoginUrl(true));
	
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {

		// auth.jdbcAuthentication().dataSource(dataSource);

		auth.jdbcAuthentication().dataSource(dataSource)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	// for local account
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);

		return jdbcUserDetailsManager;
	}
	

	// for social account
	@Bean
	public SocialUserDetailsService socialUsersDetailService() {
		return new MixUserDetailsService(userDetailsService());
	}
	
}
