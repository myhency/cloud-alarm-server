package cloud.stock.auth.ui.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserResponseDto {
    private String userName;
    private String role;
    private String token;
    private LocalDate paymentEndDate;
}
