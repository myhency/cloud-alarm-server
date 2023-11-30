package cloud.stock.auth.ui.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserResponseDto {
    private Long id;
    private String userName;
    private String role;
    private String token;
    private LocalDate paymentEndDate;
    private LocalDate paymentStartDate;
    private String memo;
}
