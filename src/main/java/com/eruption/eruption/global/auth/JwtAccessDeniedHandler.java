package com.eruption.eruption.global.auth;

import com.eruption.eruption.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res
            , AccessDeniedException accessDeniedException) throws IOException {
        errorResponseWriter.writeError(res, ErrorCode.FORBIDDEN);
    }

}
