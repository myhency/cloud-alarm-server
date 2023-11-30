package cloud.stock.v2.sevenbread.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenBreadItemUpdateRequestDto {
    private Integer lowestPrice;
    private Integer capturedPrice;
}
