package cloud.stock.sevenbread.ui.dataholder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SevenBreadItemDataHolder {
    private Long id;
    private String itemName;
    private String itemCode;
    private Integer closingPrice;
    private Integer capturedPrice;
    private Double fluctuationRate;
    private Integer priceByYesterday;
    private Integer volume;
    private String majorHandler;
    private String theme;
    private LocalDateTime capturedDate;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
