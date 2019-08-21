package com.ats.cataskapi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	/*<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-test</artifactId>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>*/

	// Authentication : User --> Roles
			protected void configure(AuthenticationManagerBuilder auth)
					throws Exception {
				auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance()).withUser("user1").password("secret1")
						.roles("USER").and().withUser("aaryatech").password("Aaryatech@1cr")
						.roles("USER", "ADMIN");
			}

			// Authorization : Role -> Access
			protected void configure(HttpSecurity http) throws Exception {
				http.httpBasic().and().authorizeRequests().antMatchers("/**")
						.hasRole("USER").antMatchers("/**").hasRole("ADMIN").and()
						.csrf().disable().headers().frameOptions().disable();
			}

}
