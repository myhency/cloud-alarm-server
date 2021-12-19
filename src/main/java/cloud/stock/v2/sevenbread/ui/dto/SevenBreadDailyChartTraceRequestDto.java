package cloud.stock.v2.sevenbread.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadDailyChartTraceRequestDto {
    private String itemCode;
    private String closingDate;
    private Integer closingPrice;
    private Integer highestPrice;
    private Integer lowestPrice;
    private String modifyPriceType;
    private String modifyRate;
    private String modifyEvent;
}
