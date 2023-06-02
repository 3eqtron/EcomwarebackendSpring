package com.eqtron.Management.System.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

public class JwtFilter extends OncePerRequestFilter  {
@Autowired
public   JwtUtil jwtUtil;
@Autowired
CustomerUserDetailsService service;

Claims claimes =null;
private String username =null;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
if(httpServletRequest.getServletPath().matches("/user/login|/user/forgotpassword|/user/signup")){
    filterChain.doFilter(httpServletRequest,httpServletResponse);
}
else {
String authorizationheader=httpServletRequest.getHeader("Authorization");
String token =null;
if (authorizationheader != null && authorizationheader.startsWith("Bearer")){
    token=authorizationheader.substring(7);
    username=jwtUtil.extractUserName(token);
    claimes=jwtUtil.extractAllClaims(token);
}
if (username != null && SecurityContextHolder.getContext().getAuthentication()==null){
    UserDetails userDetails=service.loadUserByUsername(username);
    if (jwtUtil.validatetoken(token,userDetails)){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
        );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
    filterChain.doFilter(httpServletRequest,httpServletResponse);
}


        }


    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String)claimes.get("role"));
    }
    public boolean isSubscriptionvalid(){
        return "valide".equalsIgnoreCase((String) claimes.get("validation"));
    }

    public boolean isAUser(){
        return "user".equalsIgnoreCase((String)claimes.get("role"));
    }
public String getCurrentUser(){
        return username;
}

}
