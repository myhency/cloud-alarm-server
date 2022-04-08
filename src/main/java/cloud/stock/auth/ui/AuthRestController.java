package cloud.stock.auth.ui;

import cloud.stock.auth.config.JwtTokenProvider;
import cloud.stock.auth.domain.User;
import cloud.stock.auth.domain.exceptions.LoginFailException;
import cloud.stock.auth.domain.exceptions.UserNotExistsException;
import cloud.stock.auth.infra.UserRepository;
import cloud.stock.auth.ui.dto.GetUserResponseDto;
import cloud.stock.common.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return userRepository.save(User.builder()
        .userName(user.get("userName"))
        .password(passwordEncoder.encode(user.get("password")))
        .roles(Collections.singletonList("ROLE_USER"))
        .createdAt(new Date())
        .paymentStartDate(LocalDate.parse(user.get("paymentStartDate"), formatter))
        .paymentEndDate(LocalDate.parse(user.get("paymentEndDate"), formatter))
        .isPaid(!user.get("paymentStartDate").toString().isBlank())
        .build()).getId();
    }

    @PostMapping("/auth/login")
    @Operation(summary = "로그인", description = "로그인 API 입니다.")
    public ResponseEntity login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUserName(user.get("userName"))
                .orElseThrow(LoginFailException::new);
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new LoginFailException();
        }

        return ResponseEntity
                .ok(new ResponseDto<>(GetUserResponseDto.builder()
                        .userName(member.getUsername())
                        .role(member.getRoles().get(0))
                        .token(jwtTokenProvider.createToken(
                                member.getUsername(),
                                member.getRoles()))
                        .build())

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

    @GetMapping("/auth/user/my-info")
    @Operation(summary = "유저 정보 조회", description = "유저 정보 조회 API 입니다.")
    public ResponseEntity getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails)principal).getUsername();

        User member = userRepository.findByUserName(userName)
                .orElseThrow(UserNotExistsException::new);

        return ResponseEntity.ok(new ResponseDto<>(GetUserResponseDto.builder()
                .userName(member.getUsername())
                .role(member.getRoles().get(0))
                .build()
        ));
    }
}
