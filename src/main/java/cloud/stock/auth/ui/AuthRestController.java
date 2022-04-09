package cloud.stock.auth.ui;

import cloud.stock.alarm.domain.exceptions.InvalidAlarmModificationDataException;
import cloud.stock.auth.config.JwtTokenProvider;
import cloud.stock.auth.domain.User;
import cloud.stock.auth.domain.exceptions.LoginFailException;
import cloud.stock.auth.domain.exceptions.UserNotExistsException;
import cloud.stock.auth.infra.UserRepository;
import cloud.stock.auth.ui.dto.EditUserRequestDto;
import cloud.stock.auth.ui.dto.GetUserResponseDto;
import cloud.stock.auth.ui.dto.GetUsersResponseDto;
import cloud.stock.common.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "auth", description = "인증 API")
@RestController
@RequestMapping(value = "/api/v1/platform", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
public class AuthRestController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @PostMapping("/admin/auth/join")
    public Long join(@RequestBody Map<String, String> user) {

        return userRepository.save(User.builder()
        .userName(user.get("userName"))
        .password(passwordEncoder.encode(user.get("password")))
        .roles(Collections.singletonList("ROLE_USER"))
        .createdAt(new Date())
        .paymentStartDate(user.get("paymentStartDate").isEmpty() ? null : LocalDate.parse(user.get("paymentStartDate"), formatter))
        .paymentEndDate(user.get("paymentEndDate").isEmpty() ? null : LocalDate.parse(user.get("paymentEndDate"), formatter))
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
                .paymentEndDate(member.getPaymentEndDate())
                .build()
        ));
    }

    @GetMapping("/admin/users")
    public ResponseEntity getUsers() {
        List<GetUsersResponseDto> result = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            result.add(GetUsersResponseDto.builder()
                .id(user.getId())
                .createdAt(user.getCreatedAt().toString())
                .isPaid(user.getIsPaid())
                .paymentEndDate(user.getPaymentEndDate())
                .paymentStartDate(user.getPaymentStartDate())
                .role(user.getRoles().get(0))
                .userName(user.getUsername())
                .build());
        });
        return ResponseEntity.ok(result);
    }

    @PutMapping("/admin/users/edit")
    public ResponseEntity editUser(
            @RequestBody EditUserRequestDto editUserRequestDto
    ) {
        User toBeUpdatedUser = userRepository.findById(editUserRequestDto.getId())
                .orElseThrow(UserNotExistsException::new);
        if (editUserRequestDto.getPassword() != null)
            toBeUpdatedUser.setPassword(passwordEncoder.encode(editUserRequestDto.getPassword()));
        if (editUserRequestDto.getPaymentEndDate() != null)
            toBeUpdatedUser.setPaymentEndDate(LocalDate.parse(editUserRequestDto.getPaymentEndDate().substring(0, 10), formatter));
        else
            toBeUpdatedUser.setPaymentEndDate(null);
        if (editUserRequestDto.getPaymentStartDate() != null)
            toBeUpdatedUser.setPaymentStartDate(LocalDate.parse(editUserRequestDto.getPaymentStartDate().substring(0, 10), formatter));
        else
            toBeUpdatedUser.setPaymentStartDate(null);

        toBeUpdatedUser.setRoles(Collections.singletonList(editUserRequestDto.getRole()));

        return ResponseEntity.ok(userRepository.save(toBeUpdatedUser).getId());
    }
}
