package org.blogg.Config;

import org.blogg.security.CustomerUserDetailService;
import org.blogg.security.JwtAuthenticationEntryPoint;
import org.blogg.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity>{
	
	
	@Autowired 
	private CustomerUserDetailService customerUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception
//	{
//		http.csrf()
//		.disable()
//		.authorizeHttpRequests()
//		.requestMatchers("/api/v1/auth/login")
//		.permitAll()
//		.anyRequest()
//		.authenticated()
//		.and()
//		.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
//		.and()
//		.sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		
//		http.addFilterBefore(this.jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
//		
//		return http.build();
//	}
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeRequests().
                requestMatchers("/test").authenticated().requestMatchers("/api/v1/auth/login").permitAll()
                .anyRequest()
                .authenticated()
                .and().exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	
	
	
//	AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception
//	{
//		return auth.userDetailsService(this.customerUserDetailService).passwordEncoder(passwordEncoder()).and().build();
//	}
	
	 
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
//	@Override
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception
	{
		return authConfig.getAuthenticationManager();	
	}
}
