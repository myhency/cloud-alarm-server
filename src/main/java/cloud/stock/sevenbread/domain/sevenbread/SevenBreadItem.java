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
public class SevenBreadItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String capturedDate;

    public SevenBreadItem(String itemName,
                          String itemCode,
                          String capturedDate,
                          String majorHandler,
                          String theme
    ) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.capturedDate = capturedDate;
        this.majorHandler = majorHandler;
        this.theme = theme;
    }

    public static SevenBreadItem createSevenBreadCreationRequest(String itemName,
                                                                 String itemCode,
                                                                 String capturedDate,
                                                                 String majorHandler,
                                                                 String theme
    ) {
        return new SevenBreadItem(itemName, itemCode, capturedDate, majorHandler, theme);
    }
}
