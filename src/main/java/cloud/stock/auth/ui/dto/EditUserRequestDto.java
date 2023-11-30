package cloud.stock.auth.ui.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditUserRequestDto {
    @NotNull
    @NotBlank
    private String userName;

    @NotNull
    private Long id;

    private String password;

    private String role;

    private String paymentStartDate;

    private String paymentEndDate;

    private String memo;
}
