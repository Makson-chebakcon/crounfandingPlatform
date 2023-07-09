package ru.cft.shift.crowdfundingplatformapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.cft.shift.crowdfundingplatformapi.dto.api.ApiError;
import ru.cft.shift.crowdfundingplatformapi.service.TokenService;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String CHAR_ENCODING = "UTF-8";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final EndpointsPermitAll endpointsPermitAll;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationValue = request.getHeader(AUTHORIZATION_HEADER);

        try {
            String token = authorizationValue.substring(7);
            TokenData tokenData = tokenService.decodeAccessToken(token);
            Authentication authentication = new JwtAuthentication(tokenData);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exception) {
            sendError(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        List<AntPathRequestMatcher> requestMatchers = endpointsPermitAll.getRequestMatchers();

        for (AntPathRequestMatcher requestMatcher : requestMatchers) {
            if (!request.getRequestURI().startsWith("/api") ||
                    requestMatcher.matcher(request).isMatch()) {
                return true;
            }
        }

        return false;
    }

    private void sendError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error("Токен отсутствовал или не прошел верификацию");
        ApiError apiError = new ApiError(401, request.getMethod(), request.getRequestURI(), "Не авторизован");
        String responseBody = objectMapper.writeValueAsString(apiError);

        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHAR_ENCODING);
        response.getWriter().write(responseBody);
    }

}
