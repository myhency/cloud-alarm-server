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
public class SevenBreadItemHistoryDataHolder {
    private Long id;
    private String itemName;
    private String itemCode;
    private Integer startingPrice;
    private Integer highestPrice;
    private Integer lowestPrice;
    private Integer closingPrice;
    private String capturedDate;
    private Integer capturedPrice;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
