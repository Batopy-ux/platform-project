package com.platform.user_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService){
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try{
           String header = request.getHeader(HttpHeaders.AUTHORIZATION);
           if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
               String token = header.substring(7);
               if (jwtProvider.validateToken(token)) {
                   String username = jwtProvider.getUsernameFromToken(token);
                   UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                   UsernamePasswordAuthenticationToken auth =
                           new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                   auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(auth);
               } else {
                   // token invalid or expired: leave security context unauthenticated
               }
           }
       } catch (Exception ex) {
           // log debug only (don’t expose sensitive info)
           logger.debug("Could not set user authentication in security context", ex);
       }

        filterChain.doFilter(request, response);
    }
}
