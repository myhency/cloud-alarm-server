package cloud.stock.analyze.domain.volume;

import cloud.stock.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Volume extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private String itemCode;
    private Integer closingPrice;
    private Double fluctuationRate;
    private Integer volume;
    private Integer numberOfOutstandingShares;
    private Integer marketCap;
    private String marketType;

    private Volume(String itemName,
                   String itemCode,
                   Integer closingPrice,
                   Double fluctuationRate,
                   Integer volume,
                   Integer numberOfOutstandingShares,
                   Integer marketCap,
                   String marketType) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.closingPrice = closingPrice;
        this.fluctuationRate = fluctuationRate;
        this.volume = volume;
        this.numberOfOutstandingShares = numberOfOutstandingShares;
        this.marketCap = marketCap;
        this.marketType = marketType;
    }

    public static Volume createVolumeCreationRequest(final String itemName,
                                                     final String itemCode,
                                                     final Integer closingPrice,
                                                     final Double fluctuationRate,
                                                     final Integer volume,
                                                     final Integer numberOfOutstandingShares,
                                                     final Integer marketCap,
                                                     final String marketType) {
        return new Volume(itemName, itemCode, closingPrice, fluctuationRate, volume, numberOfOutstandingShares, marketCap, marketType);
    }
}
