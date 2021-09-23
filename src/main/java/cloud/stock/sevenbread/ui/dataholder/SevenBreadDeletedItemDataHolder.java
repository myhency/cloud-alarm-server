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
public class SevenBreadDeletedItemDataHolder {
    private Long id;
    private String itemName;
    private String itemCode;
    private Integer capturedPrice;
    private String majorHandler;
    private String capturedDate;
    private String reason;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
