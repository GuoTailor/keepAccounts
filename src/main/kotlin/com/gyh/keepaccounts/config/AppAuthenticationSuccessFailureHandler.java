package com.gyh.keepaccounts.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyh.keepaccounts.common.JwtUtil;
import com.gyh.keepaccounts.model.ResponseInfo;
import com.gyh.keepaccounts.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * APP环境下认证成功处理器
 *
 * @author gyh
 */
public class AppAuthenticationSuccessFailureHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private final ObjectMapper json = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) authentication;
        String token = JwtUtil.generateToken((User) user.getPrincipal());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json.writeValueAsString(ResponseInfo.ok("成功", token)));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json.writeValueAsString(ResponseInfo.failed("用户名或密码错误")));
    }
}
