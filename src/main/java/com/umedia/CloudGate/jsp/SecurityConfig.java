package com.umedia.CloudGate.jsp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// default
		http.authorizeRequests().antMatchers("/home").authenticated()
				.anyRequest().permitAll().and().formLogin().loginPage("/login")
				.and().httpBasic();
		/*http.authorizeRequests()
		.anyRequest().permitAll().and().formLogin().loginPage("/login")
		.and().httpBasic();*/

		// no security needed
		// http.authorizeRequests().anyRequest().permitAll();
		/*
		 * .inMemoryAuthentication()
		 * .withUser("user").password("password").roles("USER");
		 */
		/*
		 * http.authorizeRequests() .anyRequest().hasRole("USER");
		 */
		/*
		 * .and() .formLogin().loginProcessingUrl("/login")
		 * .failureUrl("/loginfail") .loginPage("login");
		 */
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
	
		//auth.jdbcAuthentication().dataSource(dataSource);
		
		auth.jdbcAuthentication().dataSource(dataSource)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);
	
		return jdbcUserDetailsManager;
	}
}
