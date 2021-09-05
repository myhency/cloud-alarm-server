package cloud.stock.sevenbread.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadItemHistoryRequestDto {
    private String itemName;
    private String itemCode;
    private Integer startingPrice;
    private Integer highestPrice;
    private Integer lowestPrice;
    private Integer closingPrice;
    private String capturedDate;
}
