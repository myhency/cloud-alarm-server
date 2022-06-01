package cloud.stock.auth.ui.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUsersResponseDto {
    private Long id;
    private String userName;
    private String role;
    private String createdAt;
    private String updatedAt;
    private Boolean isPaid;
    private String memo;
    private LocalDate paymentStartDate;
    private LocalDate paymentEndDate;
}
