package cloud.stock.sevenbread.ui.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadStatisticsHistoryResponseDto {
    private String capturedDate;
    private String itemCode;
    private String itemName;
    private Integer capturedPrice;
    private String highestDate;
    private Integer highestPrice;
    private String lowestDate;
    private Integer lowestPrice;
    private String highestCapturedDate;
}
