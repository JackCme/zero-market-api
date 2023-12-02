package com.example.marketapi.global.config;

import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 필요한 권한이 존재하지 않는 겨경우에 403 Forbidden 에러를 리턴하는 class
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Setter
    private  String errorURL;
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //필요한 권한이 없이 접근하려 할때 403
        response.sendRedirect(errorURL);
//        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
