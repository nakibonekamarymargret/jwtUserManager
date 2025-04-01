package com.AUTH.jwtUserManager.configs;

import com.AUTH.jwtUserManager.security.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
         return http
                  .csrf(AbstractHttpConfigurer::disable)//.csrf(csrf->csrf.disable())
                  .authorizeHttpRequests(auth -> auth
                          .requestMatchers("/auth/register", "/auth/login").permitAll()  // Allow public access
                          .anyRequest().authenticated()
                  )
                  .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                  .httpBasic(Customizer.withDefaults())
                 .build();
    }
}
