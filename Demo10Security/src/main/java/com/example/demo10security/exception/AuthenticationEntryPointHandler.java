package com.example.demo10security.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointHandler.class);
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", e.getMessage());
            httpServletResponse.sendError(httpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}
