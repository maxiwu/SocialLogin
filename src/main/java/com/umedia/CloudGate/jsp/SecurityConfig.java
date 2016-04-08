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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import com.umedia.CloudGate.social.SimpleSocialUsersDetailService;



@Configuration
//@EnableWebSecurity
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.
            jdbcAuthentication()
            .dataSource(dataSource)
            .withDefaultSchema();
    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
                //Spring Security ignores request to static resources such as CSS or JS files.
                .ignoring()
                    .antMatchers("/css/**", "/js/**");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// default
		/*http.authorizeRequests().antMatchers("/home").authenticated()
				.anyRequest().permitAll().and().formLogin().loginPage("/login")
				.and().httpBasic();
		http.formLogin().and().apply(new SpringSocialConfigurer().postLoginUrl("/").alwaysUsePostLoginUrl(true));*/
	
		//social login only
		/*http
        .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login/authenticate")
            .failureUrl("/login?param.error=bad_credentials")
            .permitAll()
    .and()
        .logout()
            .logoutUrl("/logout")
            .deleteCookies("JSESSIONID")
    .and()
        .authorizeRequests()
            .antMatchers("/favicon.ico", "/resources/**", "/resources/css/**", "/css/**").permitAll()
            .antMatchers("/**").authenticated()
    .and()
        .rememberMe()
    .and()
    //add a SocialAuthenticationFilter
        .apply(new SpringSocialConfigurer()
            .postLoginUrl("/")
            .alwaysUsePostLoginUrl(true));*/

		//support both local and social
		http
        //Configures form login
        .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login/authenticate").defaultSuccessUrl("/home")            
            .failureUrl("/login?error=bad_credentials")
        //Configures the logout function
        .and()
            .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
        //Configures url based authorization
        .and()
            .authorizeRequests()
                //Anyone can access the urls
                .antMatchers(
                        "/auth/**",
                        "/login",
                        "/signup/**",
                        "/user/register/**"
                ).permitAll()
                //The rest of the our application is protected.
                .antMatchers("/**").hasRole("USER")
        //Adds the SocialAuthenticationFilter to Spring Security's filter chain.
        .and()
            .apply(new SpringSocialConfigurer());
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

	//for local account
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);

		return jdbcUserDetailsManager;
	}
	
	//for social account
	 @Bean
	    public SocialUserDetailsService socialUsersDetailService() {
	        return new SimpleSocialUsersDetailService(userDetailsService());
	    }
}
