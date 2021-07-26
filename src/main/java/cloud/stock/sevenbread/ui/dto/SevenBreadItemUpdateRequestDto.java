package cloud.stock.sevenbread.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadItemUpdateRequestDto {
    private Integer closingPrice;
    private Integer capturedPrice;
    private Double fluctuationRate;
    private Integer priceByYesterday;
    private Integer volume;
}
