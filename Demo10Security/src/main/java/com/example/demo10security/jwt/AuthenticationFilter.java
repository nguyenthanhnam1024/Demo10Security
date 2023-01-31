package com.example.demo10security.jwt;

import com.example.demo10security.configure.UserDetailsImpl;
import com.example.demo10security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private String getTokenFromRequest(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")){
            return token.substring(7, token.length());
        }
        return null;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
       try {
           String jwt = getTokenFromRequest(httpServletRequest);
           if (jwt != null && jwtUtils.validateJwtToken(jwt)){
               String userName = jwtUtils.getUserNameFromJwtToken(jwt);
               UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(userName);
               UsernamePasswordAuthenticationToken authenticationToken =
                       new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
           }
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
       filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
