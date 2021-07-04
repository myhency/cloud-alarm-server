package cloud.stock.analyze.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolumeBySharesCreationRequestDto {
    private String itemName;
    private String itemCode;
    private Integer closingPrice;
    private Double fluctuationRate;
    private Integer volume;
    private Integer numberOfOutstandingShares;
    private Integer marketCap;
    private String marketType;
}
