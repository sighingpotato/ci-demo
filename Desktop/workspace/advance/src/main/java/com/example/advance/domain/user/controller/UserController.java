package com.example.advance.domain.user.controller;

import com.example.advance.common.utils.JwtUtil;
import com.example.advance.domain.user.model.dto.UserDto;
import com.example.advance.domain.user.model.request.UpdateUserEmailRequest;
import com.example.advance.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get")
    public String getUserInfo(@AuthenticationPrincipal User user) {

        log.info(user.getUsername());
        return user.getUsername();
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
//
//        String token = jwtUtil.generateToken(request.getUserName());
//
//        return ResponseEntity.ok(new LoginResponse(token));
//    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> checkValidateToken(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        String jwt = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분만 추출

        Boolean validate = jwtUtil.validateToken(jwt);

        return ResponseEntity.ok(validate);
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsernameFromToken(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        String jwt = authorizationHeader.substring(7);

        String username = jwtUtil.extractUsername(jwt);

        return ResponseEntity.ok(username);
    }

    @PutMapping("/{username}/email")
    public ResponseEntity<String> updateEmail(@PathVariable String username, @RequestBody UpdateUserEmailRequest request) {
        log.info("3번째 : interceptor를 통과 후 controller 로직 수행.");

        userService.updateUserEmail(username, request.getEmail());

        log.info("7번째 : controller 수행 완료.");

        return ResponseEntity.ok("수정완료");
    }

    // 이메일을 수정하는 것은 본인만 가능

    // JPQL을 통한 CRUD 실습

    // 1. 단계 조회하기
    // 2. 수정하기
    // 3. 삭제하기

    @GetMapping("/get/user/{username}/jpql")
    public ResponseEntity<UserDto> getUserByUsernameWithJpql(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping("/{username}/email/jpql")
    public ResponseEntity<UserDto> updateEmailByJpql(@PathVariable String username, @RequestBody UpdateUserEmailRequest request) {

        userService.updateUserEmailByJpql(username, request.getEmail());

        return ResponseEntity.ok(userService.updateUserEmailByJpql(username, request.getEmail()));
    }

    @DeleteMapping("/{username}/jpql")
    public ResponseEntity<String> deleteUserByJpql(@PathVariable String username) {
        userService.deleteUserByJpql(username);
        return ResponseEntity.ok("삭제 완료");
    }
}
