package cloud.stock.auth.ui;

import cloud.stock.auth.config.JwtTokenProvider;
import cloud.stock.auth.domain.User;
import cloud.stock.auth.domain.exceptions.LoginFailException;
import cloud.stock.auth.domain.exceptions.UserNotExistsException;
import cloud.stock.auth.infra.UserRepository;
import cloud.stock.common.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "auth", description = "인증 API")
@RestController
@RequestMapping(value = "/api/v1/platform", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
public class AuthRestController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/auth/join")
    public Long join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
        .userName(user.get("userName"))
        .password(passwordEncoder.encode(user.get("password")))
        .roles(Collections.singletonList("ROLE_USER"))
        .build()).getId();
    }

    @PostMapping("/auth/login")
    @Operation(summary = "로그인", description = "로그인 API 입니다.")
    public ResponseEntity login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUserName(user.get("userName"))
                .orElseThrow(() -> new LoginFailException());
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new LoginFailException();
        }

        return ResponseEntity
                .ok(new ResponseDto<>(jwtTokenProvider.createToken(
                        member.getUsername(),
                        member.getRoles()))
                );
    }

    @PutMapping("/auth/changePassword")
    @Operation(summary = "비밀번호변경", description = "비밀번호변경 API 입니다.")
    public Long changePassword(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUserName(user.get("userName"))
                .orElseThrow(() -> new UserNotExistsException());

        member.setPassword(passwordEncoder.encode(user.get("password")));
        return userRepository.save(member).getId();
    }
}
