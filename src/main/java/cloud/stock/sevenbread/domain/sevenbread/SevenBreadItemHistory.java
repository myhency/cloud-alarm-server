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
public class SevenBreadItemHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private String itemCode;
    private Integer startingPrice;
    private Integer highestPrice;
    private Integer lowestPrice;
    private Integer closingPrice;
    private String capturedDate;

    public SevenBreadItemHistory(String itemName,
                                 String itemCode,
                                 Integer startingPrice,
                                 Integer highestPrice,
                                 Integer lowestPrice,
                                 Integer closingPrice,
                                 String capturedDate) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.startingPrice = startingPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.closingPrice = closingPrice;
        this.capturedDate = capturedDate;
    }

    public static SevenBreadItemHistory createSevenBreadItemHistory(String itemName,
                                                                    String itemCode,
                                                                    Integer startingPrice,
                                                                    Integer highestPrice,
                                                                    Integer lowestPrice,
                                                                    Integer closingPrice,
                                                                    String capturedDate
    ) {
        return new SevenBreadItemHistory(itemName, itemCode, startingPrice, highestPrice, lowestPrice, closingPrice, capturedDate);
    }
}
