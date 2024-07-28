package com.masjid.crm.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Configuration
	@Order(1)
	public static class SwaggerSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.antMatcher("/swagger-ui.html")
					.authorizeRequests()
					.antMatchers("/user/register").permitAll()
					.antMatchers("/user/all/**").permitAll()
					.antMatchers("/user/login").permitAll()
					.antMatchers("/user/sendOtp").permitAll()
//				.antMatchers(HttpMethod.POST, "/mykare/enquiry/filtered").permitAll()
					.antMatchers("/user/passwordEncryption/**").permitAll()
					.antMatchers("/internal/data/email/subscribers/all/**").permitAll()
					.antMatchers("/internal/mykare/partnership/enquiries/all").permitAll()
					.antMatchers("/mykare/enquiry/downloadReport").permitAll()
					.anyRequest().authenticated()
					.and()
					.httpBasic();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication()
					.withUser("admin@mykare.com").password("{noop}mykare#38@71").roles("ADMIN");
		}
	}

	@Configuration
	@Order(2)
	public static class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private AppAuthenticationEntryPoint appAuthenticationEntryPoint;
		@Autowired
		private UserDetailsService userDetailsService;
		@Autowired
		private AppRequestFilter appRequestFilter;

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			httpSecurity
					.csrf().disable()
					.authorizeRequests().antMatchers("/").permitAll()
					.antMatchers("/swagger-ui.html").permitAll()
					.antMatchers("/webjars/**").permitAll()
					.antMatchers("/v2/**").permitAll()
					.antMatchers("/swagger-resources/**").permitAll()
					.antMatchers("/user/register").permitAll()
					.antMatchers("/user/all/**").permitAll()
					.antMatchers("/user/login").permitAll()
					.antMatchers("/user/sendOtp").permitAll()
//				.antMatchers(HttpMethod.POST, "/mykare/enquiry/filtered").permitAll()
					.antMatchers("/user/passwordEncryption/**").permitAll()
					.antMatchers("/internal/data/email/subscribers/all/**").permitAll()
					.antMatchers("/internal/mykare/partnership/enquiries/all").permitAll()
					.antMatchers("/mykare/enquiry/downloadReport").permitAll()// Permit access to Swagger UI without JWT
					.anyRequest().authenticated()
					.and()
					.exceptionHandling().authenticationEntryPoint(appAuthenticationEntryPoint)
					.and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			httpSecurity.addFilterBefore(appRequestFilter, UsernamePasswordAuthenticationFilter.class);
		}
	}
}
