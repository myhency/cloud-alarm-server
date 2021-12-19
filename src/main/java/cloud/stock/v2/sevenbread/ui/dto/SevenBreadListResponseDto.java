package cloud.stock.v2.sevenbread.ui.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadListResponseDto {
    private LocalDateTime createdDate;
    private String itemName;
    private String itemCode;
    private String capturedDate;
    private Integer capturedPrice;
    private Integer lowestPrice;
    private String majorHandler;
}
