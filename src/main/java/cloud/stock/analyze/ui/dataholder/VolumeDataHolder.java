package cloud.stock.analyze.ui.dataholder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolumeDataHolder {
    private Long id;
    private String itemName;
    private String itemCode;
    private Integer closingPrice;
    private Double fluctuationRate;
    private Integer volume;
    private Integer numberOfOutstandingShares;
    private Integer marketCap;
    private String marketType;
    private LocalDateTime createdDate;
}
