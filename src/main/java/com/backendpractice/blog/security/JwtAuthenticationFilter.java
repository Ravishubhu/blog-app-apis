package com.backendpractice.blog.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private JwtTokenHelper jwtTokenHelper;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // 1. get Token ()key - Authorization
    String requestToken = request.getHeader("Authorization");

    // Bearer 23egh43k20
    System.out.println(requestToken);

    String username = null;

    String token = null;

    if (requestToken != null && requestToken.startsWith("Bearer")) {

      token = requestToken.substring(7);
      try {
        username = jwtTokenHelper.getUsernameFromToken(token);
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get Jwt Token");
      } catch (ExpiredJwtException e) {
        System.out.println("Expired JWT Token");
      } catch (MalformedJwtException e) {
        System.out.println("Invalid JWT Token");
      }


    } else {
      System.out.println("Jwt Token Does not start with Bearer");
    }

    //Once we get the token we will validate
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

      if (this.jwtTokenHelper.validateToken(token, userDetails)) {
        //working fine
        //authentication
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      } else {
        System.out.println("Invalid JWT Token");
      }
    } else {
      System.out.println("username is null or context is null");
    }

    filterChain.doFilter(request, response);

  }
}
