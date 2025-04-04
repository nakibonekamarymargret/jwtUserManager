package com.AUTH.jwtUserManager.security;

import com.AUTH.jwtUserManager.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtutil;
    private final ApplicationContext context;

    public JwtFilter(JwtUtil jwtutil, ApplicationContext context) {
        this.jwtutil = jwtutil;
        this.context = context;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        Bearer
        String authHeader =request.getHeader("Authorization");
        String token = null;
        String username =null;
        if(authHeader!=null && authHeader.startsWith("Bearer")) {
            token =authHeader.substring(7);
            username= jwtutil.extractUsername(token);
        }

        if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(username);
            if(jwtutil.validateToken(token,userDetails)){

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
