/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.umedia.CloudGate;

import java.util.Locale;

import javax.inject.Inject;
import javax.sql.DataSource;









import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

//import com.umedia.CloudGate.dao.UsersDao;

@Configuration
@EnableAutoConfiguration
@ComponentScan
//public class SampleWebJspApplication extends SpringBootServletInitializer {

//for jar
public class SampleWebJspApplication {

	@Inject
	private Environment env;
	
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SampleWebJspApplication.class);
	}*/

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleWebJspApplication.class, args);
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(env.getProperty("database.driver"));
		ds.setUrl(env.getProperty("database.url"));
		ds.setUsername(env.getProperty("database.username"));
		ds.setPassword(env.getProperty("database.password"));	
		
		return ds;
	}
	

	

}
