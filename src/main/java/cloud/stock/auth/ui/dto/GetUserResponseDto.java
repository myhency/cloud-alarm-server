package cloud.stock.auth.ui.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserResponseDto {
    private String userName;
    private String role;
    private String token;
}
