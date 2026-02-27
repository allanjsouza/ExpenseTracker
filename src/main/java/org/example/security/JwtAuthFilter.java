package org.example.security;

import java.io.IOException;

import org.example.service.CustomUserDetailsService;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

    final String authorizationHeader = request.getHeader("Authorization");

    String jwtToken = extracToken(authorizationHeader);
    String username = extractUsername(jwtToken);

    if (shouldAuthenticateUser(username)) {
      var userDetails = userDetailsService.loadUserByUsername(username);

      if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }

  private String extracToken(String authHeader) {
    if (authHeader != null && authHeader.startsWith("Bearer "))
      return authHeader.substring(7);

    return null;
  }

  private String extractUsername(String jwtToken) {
    try {
      if (jwtToken != null)
        return jwtUtil.extractUsername(jwtToken);
    } catch (JwtException e) {
      System.err.println(e.getMessage());
    }

    return null;
  }

  private boolean shouldAuthenticateUser(String username) {
    return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
  }
}
