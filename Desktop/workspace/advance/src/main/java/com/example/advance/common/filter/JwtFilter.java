package com.example.advance.common.filter;

import com.example.advance.common.enums.UserRoleEnum;
import com.example.advance.common.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@Order(3)
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // 토큰을 발급받는 로그인의 경우에는 토큰 검사를 하지 않아도 통과
        String requestURL = request.getRequestURI();

        if (requestURL.equals("/api/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 너 토큰 있어? 없어?

        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || authorizationHeader.isBlank()) {
            log.info("JWT 토큰이 필요합니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 필요합니다.");
            return;
        }

        // 토큰이 있어? 그럼 그 토큰 유효해?

        String jwt = authorizationHeader.substring(7);

        if(!jwtUtil.validateToken(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
        }

        // 만약에 유효하다면 어떤 정보를 가지고 있어?

        String username = jwtUtil.extractUsername(jwt);

        String auth = jwtUtil.extractRole(jwt);

        UserRoleEnum userRoleEnum = UserRoleEnum.valueOf(auth);

        // JWT 토큰에서 복호화 한 데이터 저장

//        request.setAttribute("username", username); -> Spring security 방식에 맞는 방법으로 만들어줄 것임.

        // Spring Security에서 사용하는 User 객체 생성
        User user = new User(username, "", List.of(userRoleEnum::getRole));

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

//        log.info("1번 : JwtFilter 인증/인가 성공 -> 다음 단계로 넘어감.");

        filterChain.doFilter(request, response);

//        log.info("8번째 : JwtFilter 통과 완료 후 postman에 전달 끝");
    }
}
