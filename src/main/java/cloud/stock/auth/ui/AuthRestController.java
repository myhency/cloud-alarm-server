package cloud.stock.auth.ui;

import cloud.stock.auth.config.JwtTokenProvider;
import cloud.stock.auth.domain.User;
import cloud.stock.auth.infra.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUserName(user.get("userName"))
                .orElseThrow(() -> new IllegalArgumentException("해당유저는 없습니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("wrong password");
        }

        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
}
