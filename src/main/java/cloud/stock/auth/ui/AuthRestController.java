package cloud.stock.auth.ui;

import cloud.stock.alarm.domain.exceptions.InvalidAlarmModificationDataException;
import cloud.stock.auth.config.JwtTokenProvider;
import cloud.stock.auth.domain.User;
import cloud.stock.auth.domain.exceptions.LoginFailException;
import cloud.stock.auth.domain.exceptions.UserDuplicatedException;
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
import org.springframework.dao.DuplicateKeyException;
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
        userRepository.findByUserName(user.get("userName")).ifPresent(_user -> {
            throw new UserDuplicatedException();
        });


        return userRepository.save(User.builder()
        .userName(user.get("userName"))
        .password(passwordEncoder.encode(user.get("password")))
        .roles(Collections.singletonList("ROLE_USER"))
        .createdAt(new Date())
        .updatedAt(new Date())
        .paymentStartDate(user.get("paymentStartDate").isEmpty() ? null : LocalDate.parse(user.get("paymentStartDate"), formatter))
        .paymentEndDate(user.get("paymentEndDate").isEmpty() ? null : LocalDate.parse(user.get("paymentEndDate"), formatter))
        .isPaid(!user.get("paymentStartDate").toString().isBlank())
        .memo(user.get("memo"))
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
                        .paymentStartDate(member.getPaymentStartDate())
                        .paymentEndDate(member.getPaymentEndDate())
                        .token(jwtTokenProvider.createToken(
                                member.getUsername(),
                                member.getRoles()))
                        .id(member.getId())
                        .build())

                );
    }

    @PutMapping("/auth/changePassword")
    @Operation(summary = "비밀번호변경", description = "비밀번호변경 API 입니다.")
    public Long changePassword(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUserName(user.get("userName"))
                .orElseThrow(UserNotExistsException::new);

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
                .paymentStartDate(member.getPaymentStartDate())
                .paymentEndDate(member.getPaymentEndDate())
                .memo(member.getMemo())
                .id(member.getId())
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
                .updatedAt(user.getUpdatedAt().toString())
                .isPaid(user.getIsPaid())
                .paymentEndDate(user.getPaymentEndDate())
                .paymentStartDate(user.getPaymentStartDate())
                .memo(user.getMemo())
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
        if (editUserRequestDto.getPaymentEndDate() != null && !editUserRequestDto.getPaymentEndDate().equals(""))
            toBeUpdatedUser.setPaymentEndDate(LocalDate.parse(editUserRequestDto.getPaymentEndDate().substring(0, 10), formatter));
        else
            toBeUpdatedUser.setPaymentEndDate(null);
        if (editUserRequestDto.getPaymentStartDate() != null && !editUserRequestDto.getPaymentStartDate().equals(""))
            toBeUpdatedUser.setPaymentStartDate(LocalDate.parse(editUserRequestDto.getPaymentStartDate().substring(0, 10), formatter));
        else
            toBeUpdatedUser.setPaymentStartDate(null);

        toBeUpdatedUser.setRoles(Collections.singletonList(editUserRequestDto.getRole()));
        toBeUpdatedUser.setMemo(editUserRequestDto.getMemo());
        toBeUpdatedUser.setUpdatedAt(new Date());

        return ResponseEntity.ok(userRepository.save(toBeUpdatedUser).getId());
    }
}
