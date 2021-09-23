package cloud.stock.sevenbread.domain.sevenbread;

import cloud.stock.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SevenBreadDeletedItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private String itemCode;
    private Integer capturedPrice;
    private String majorHandler;
    private String capturedDate;
    private String reason;

    public SevenBreadDeletedItem(String itemName, String itemCode, Integer capturedPrice, String capturedDate, String majorHandler, String reason) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.capturedPrice = capturedPrice;
        this.capturedDate = capturedDate;
        this.majorHandler = majorHandler;
        this.reason = reason;
    }

    public static SevenBreadDeletedItem createSevenBreadDeletedCreationRequest(String itemName,
                                                                               String itemCode,
                                                                               Integer capturedPrice,
                                                                               String capturedDate,
                                                                               String majorHandler,
                                                                               String reason
    ) {
        return new SevenBreadDeletedItem(itemName, itemCode, capturedPrice, capturedDate, majorHandler, reason);
    }
}
